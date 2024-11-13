package ru.mpei.plcbackrefactoring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mpei.plcbackrefactoring.model.entities.Model;
import ru.mpei.plcbackrefactoring.model.dto.schemes.SchemeDto;
import ru.mpei.plcbackrefactoring.model.mappers.SchemesMapper;
import ru.mpei.plcbackrefactoring.service.schemestorage.MongoSchemeStorageService;
import ru.mpei.plcbackrefactoring.service.schemestorage.PostgresSchemeStorageService;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Slf4j
class Migration {
    @Autowired
    private MongoSchemeStorageService mongoAccessService;
    @Autowired
    private PostgresSchemeStorageService postgresAccessService;
    @Autowired
    private SchemesMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void migrate() {
        List<Model> mongoModels = this.mongoAccessService.getAllSchemes();
        mongoModels.forEach(model -> {
            System.out.println(model);
            SchemeDto scheme = this.mapper.modelToSchemeDto(model);
            try {
                this.postgresAccessService.save(UUID.fromString(scheme.getProjectId()), scheme.getProjectName(), UUID.fromString(scheme.getId()), scheme.getName(), this.objectMapper.writeValueAsString(scheme));
            } catch (JsonProcessingException e) {
                log.error("can not save scheme with id {}", scheme.getId());
            }
        });
    }

}
