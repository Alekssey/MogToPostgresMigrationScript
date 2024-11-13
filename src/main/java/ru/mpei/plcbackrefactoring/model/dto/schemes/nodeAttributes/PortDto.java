package ru.mpei.plcbackrefactoring.model.dto.schemes.nodeAttributes;

import lombok.Data;

import java.util.List;

@Data
public class PortDto {
    private String id;
    private boolean locked;
    private boolean selected;
    private String alignment;
    private String parentNode;
    private List<String> links;
    private String libId;
    private CoordinatesDto coords;
}
