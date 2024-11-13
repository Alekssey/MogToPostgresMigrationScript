package ru.mpei.plcbackrefactoring.model.dto.schemes;

import lombok.Data;
import ru.mpei.plcbackrefactoring.model.dto.schemes.linkAttributes.PointDto;

import java.util.List;

@Data
public class LinkDto {
    private String id;
    private boolean locked;
    private boolean selected;
    private String source;
    private String sourcePort;
    private String target;
    private String targetPort;
    private List<PointDto> points;
}
