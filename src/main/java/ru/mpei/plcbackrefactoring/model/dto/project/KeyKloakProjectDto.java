package ru.mpei.plcbackrefactoring.model.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyKloakProjectDto {
    private KeycloakProject project;
    private Scopes[] scopes;
}
