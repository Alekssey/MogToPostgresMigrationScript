package ru.mpei.plcbackrefactoring.model.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.mpei.plcbackrefactoring.model.dto.schemes.LinkDto;
import ru.mpei.plcbackrefactoring.model.dto.schemes.NodeDto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Document
public class Model {
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

    public void removeUnnecessaryFieldsForBack() {
        this.getNodes().values().forEach(node -> {
            Map<String, Object> fieldsForBackMap = new HashMap<>(node.getFields());
            if (!node.getLibEquipmentId().equals("C_BLOCK")) {
                fieldsForBackMap.remove("OUTPUT_TYPE");
                fieldsForBackMap.remove("INPUT_TYPE");
            } else {
                fieldsForBackMap.put("LOCAL_VARIABLES", node.getLocalVariables());
            }
            fieldsForBackMap.remove("INPUT_OUTPUT_TYPE");
            fieldsForBackMap.remove("PORTS_NUMBER");
            node.setFieldsForBack(fieldsForBackMap);
        });
    }
}
