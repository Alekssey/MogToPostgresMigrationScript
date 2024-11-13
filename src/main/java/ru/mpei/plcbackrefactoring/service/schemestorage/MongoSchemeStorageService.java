package ru.mpei.plcbackrefactoring.service.schemestorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import ru.mpei.plcbackrefactoring.model.entities.Model;
import ru.mpei.plcbackrefactoring.repository.MongoModelRepository;

import java.util.List;

@Service
@Slf4j
public class MongoSchemeStorageService {

    @Autowired
    private MongoModelRepository modelRepository;

    public List<Model> getAllSchemes() {
        return this.modelRepository.findAll();
    }

}
