package ru.mpei.plcbackrefactoring.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakUserDto {
    private UUID keycloakUserId;
    private String firstName;
    private String lastName;
    private UUID companyId;
}
