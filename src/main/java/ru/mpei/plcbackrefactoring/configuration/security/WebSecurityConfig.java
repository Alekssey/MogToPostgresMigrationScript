package ru.mpei.plcbackrefactoring.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
public class WebSecurityConfig {

    @Value("${spring.security.enable:true}")
    private boolean enableSecurity;

    @Bean
    public SecurityFilterChain securityFilterChainDev(HttpSecurity httpSecurity) throws Exception {
        if (enableSecurity) {
            httpSecurity
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                            authorizationManagerRequestMatcherRegistry.requestMatchers("/api/notifications/events").permitAll().anyRequest().authenticated())
                    .sessionManagement(httpSecuritySessionManagementConfigurer ->
                            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        } else {
            httpSecurity
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                            authorizationManagerRequestMatcherRegistry.anyRequest().permitAll())
                    .headers().frameOptions().disable();

        }

        return httpSecurity.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }

    @Bean
    public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter delegate = new JwtGrantedAuthoritiesConverter();

        return new Converter<>() {
            @Override
            public Collection<GrantedAuthority> convert(Jwt jwt) {
                Collection<GrantedAuthority> grantedAuthorities = delegate.convert(jwt);

                if (jwt.getClaim("realm_access") == null) {
                    return grantedAuthorities;
                }
                Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
                if (realmAccess.get("roles") == null) {
                    return grantedAuthorities;
                }
                Collection<String> roles = realmAccess.get("roles");

                final List<SimpleGrantedAuthority> keycloakAuthorities = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();
                grantedAuthorities.addAll(keycloakAuthorities);

                if (jwt.getClaim("resource_access") == null) {
                    return grantedAuthorities;
                }
                Map<String, Collection<String>> resourceAccess = jwt.getClaim("resource_access");

                if (resourceAccess.get("roles") == null) {
                    return grantedAuthorities;
                }
                roles = resourceAccess.get("roles");

                final List<SimpleGrantedAuthority> keycloakAuthorities2 = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();
                grantedAuthorities.addAll(keycloakAuthorities2);


                return grantedAuthorities;
            }
        };
    }

    @Bean
    public AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService
    ) {

        AuthorizedClientServiceOAuth2AuthorizedClientManager manager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                authorizedClientService
        );
        manager.
                setAuthorizedClientProvider(
                        OAuth2AuthorizedClientProviderBuilder.builder()
                                .clientCredentials()
                                .build()
                );
        return manager;
    }

    @Bean
    public WebClient webClient(AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oAuthClient = new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oAuthClient.setDefaultOAuth2AuthorizedClient(true);
        oAuthClient.setDefaultClientRegistrationId("keycloak");

        return WebClient.builder()
                .filter(oAuthClient)
                .build();
    }

}
