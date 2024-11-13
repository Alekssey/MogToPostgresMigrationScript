package ru.mpei.plcbackrefactoring.model.dto.schemes;

import lombok.Data;
import ru.mpei.plcbackrefactoring.model.dto.schemes.nodeAttributes.CBlockLocalVariableDto;
import ru.mpei.plcbackrefactoring.model.dto.schemes.nodeAttributes.CoordinatesDto;
import ru.mpei.plcbackrefactoring.model.dto.schemes.nodeAttributes.DimentionsDto;
import ru.mpei.plcbackrefactoring.model.dto.schemes.nodeAttributes.PortDto;

import java.util.List;
import java.util.Map;

@Data
public class NodeDto {
    private String id;
    private boolean locked;
    private boolean selected;
    private CoordinatesDto coords;
    private DimentionsDto dimensions;
    private List<PortDto> ports;
    private Integer hour;
    private String libEquipmentId;
    private Map<String, Object> fields;
    private Map<String, Object> fieldsForBack;
    private String name;
    private String nameForBack;
    private List<String> outputType;
    private List<String> inputType;
    private String inputOutputType;
    private boolean enableDynamicPorts;
    private String description;
    private String group;
    private CBlockLocalVariableDto[] localVariables;
}
