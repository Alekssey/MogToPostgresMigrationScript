package ru.mpei.plcbackrefactoring.model.dto.schemes.linkAttributes;

import lombok.Data;
import ru.mpei.plcbackrefactoring.model.dto.schemes.nodeAttributes.CoordinatesDto;

@Data
public class PointDto {
    private String id;
    private boolean locked;
    private boolean selected;
    private CoordinatesDto coords;

}
