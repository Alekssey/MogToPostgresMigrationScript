package ru.mpei.plcbackrefactoring.model.mappers;

import org.mapstruct.Mapper;
import ru.mpei.plcbackrefactoring.model.entities.Model;
import ru.mpei.plcbackrefactoring.model.dto.schemes.SchemeDto;

@Mapper(componentModel = "spring")
public interface SchemesMapper {
    SchemeDto modelToSchemeDto(Model source);
}
