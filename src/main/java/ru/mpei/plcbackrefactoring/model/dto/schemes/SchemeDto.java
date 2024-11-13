package ru.mpei.plcbackrefactoring.model.dto.schemes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchemeDto {
    private String id;
    private String name;
    private String nameForBack;
    private String userId;
    private boolean validated;
    private Date date;
    private Date validationDate;
    private String note;
    private boolean locked;
    private double offsetX;
    private double offsetY;
    private int version;
    private double zoom;
    private String sampling;
    private Map<String, NodeDto> nodes;
    private Map<String, LinkDto> links;
    private String projectId;
    private String projectName;
    private int vPLCNumber;

    public SchemeDto(String id, String name, boolean validated, String projectId, String projectName) {
        this.id = id;
        this.name = name;
        this.validated = validated;
        this.projectId = projectId;
        this.projectName = projectName;
    }
}
