package ru.mpei.plcbackrefactoring.service.schemestorage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mpei.plcbackrefactoring.model.entities.VplcSchemeEntity;
import ru.mpei.plcbackrefactoring.model.dto.schemes.SchemeDto;
import ru.mpei.plcbackrefactoring.repository.VplcSchemesRepository;

import java.util.UUID;


/**
 *  a) save scheme
 *  b) get scheme
 *  c) delete scheme
 * 	d) get all schemes for project (only main info)
 *  e) check if scheme exist -
 */
@Slf4j
@Service
@Transactional
public class PostgresSchemeStorageService {

    @Autowired
    private VplcSchemesRepository repository;


    public void save(UUID projectId, String projectName, UUID schemeId, String schemeName, String schemeJson){

        this.repository.save(new VplcSchemeEntity(
                schemeId,
                schemeName,
                projectId,
                projectName,
                schemeJson,
                false
        ));
    }
}
