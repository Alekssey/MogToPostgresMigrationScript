package ru.mpei.plcbackrefactoring.service.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.mpei.plcbackrefactoring.model.dto.project.KeyKloakProjectDto;
import ru.mpei.plcbackrefactoring.model.dto.user.KeycloakUserDto;
//import ru.mpei.plcbackrefactoring.model.dto.slots.UserDto;

import java.util.UUID;

@Service
public class IntegrationPlatformClient {

    @Value("${services.address.integration-platform}")
    private String integrationPlatformUri;
    @Autowired
    private WebClient webClient;

    public KeycloakUserDto getUserInfoByUserId(UUID userId) {
        System.out.println("URL: " + this.integrationPlatformUri + "/api/integration/v1/internal/users/" + userId);
        WebClient.RequestHeadersSpec<?> response = webClient.get().uri(
                this.integrationPlatformUri + "/api/integration/v1/internal/users/" + userId
        ).accept(MediaType.APPLICATION_JSON);

        return response.exchangeToFlux(resp -> {
                System.out.println(resp.statusCode());
            if (resp.statusCode() == HttpStatus.OK) {
                return resp.bodyToFlux(KeycloakUserDto.class);
            } else {
                System.out.println("ERRORRRRRRRRRRRRRR");
                return Flux.error(new RuntimeException("Error while fetching entities from integration platform"));
            }
        }).blockFirst();
    }

    public KeyKloakProjectDto getProjectInfo(UUID projectId) {

        System.out.println("URL: " + this.integrationPlatformUri + "/api/integration/v1/projects/" + projectId);

        WebClient.RequestHeadersSpec<?> response = webClient.get().uri(
                this.integrationPlatformUri + "/v1/projects/" + projectId
        ).accept(MediaType.APPLICATION_JSON);
        return response.exchangeToFlux(resp -> {
            if (resp.statusCode() == HttpStatus.OK) {
                return resp.bodyToFlux(KeyKloakProjectDto.class);
            } else {
                return Flux.error(new RuntimeException("Error while fetching entities from integration platform"));
            }
        }).blockFirst();
    }

}
