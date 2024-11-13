package ru.mpei.plcbackrefactoring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mpei.plcbackrefactoring.model.entities.Model;

public interface MongoModelRepository extends MongoRepository<Model, String> {

}
