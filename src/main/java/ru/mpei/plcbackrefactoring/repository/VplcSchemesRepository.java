package ru.mpei.plcbackrefactoring.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.mpei.plcbackrefactoring.model.entities.VplcSchemeEntity;

@Repository
@Slf4j
public class VplcSchemesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(VplcSchemeEntity scheme){
        this.entityManager.persist(scheme);
    }

}
