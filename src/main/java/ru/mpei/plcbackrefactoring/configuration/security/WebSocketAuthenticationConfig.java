package ru.mpei.plcbackrefactoring.configuration.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Slf4j
public class WebSocketAuthenticationConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private JwtAuthenticationConverter jwtAuthenticationConverter;
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> nativeHeader = accessor.getNativeHeader("X-Authorization");
                    log.info("Authorization: {}", nativeHeader.get(0));
                    Object accessToken = Arrays.stream(nativeHeader.get(0).split(" ")).dropWhile(String::isEmpty).toArray()[1];
                    Jwt jwt = jwtDecoder.decode((String) accessToken);
                    AbstractAuthenticationToken authenticationToken = jwtAuthenticationConverter.convert(jwt);
                    accessor.setUser(authenticationToken);
                }
                return message;
            }
        });
    }
}
