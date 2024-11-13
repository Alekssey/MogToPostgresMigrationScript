package ru.mpei.plcbackrefactoring.model.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mpei.plcbackrefactoring.model.dto.user.KeycloakUserDto;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakProject {
    private UUID id;
    private String name;
    private String projectManager;
    private String[] userSettings;
    private String[] appsSettings;
    private String createdDate;
    private String lastModifiedDate;
    private KeycloakUserDto createdBy;
    private KeycloakUserDto lastModifiedBy;
}
