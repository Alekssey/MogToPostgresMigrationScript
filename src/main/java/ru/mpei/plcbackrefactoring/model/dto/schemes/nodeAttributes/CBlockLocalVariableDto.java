package ru.mpei.plcbackrefactoring.model.dto.schemes.nodeAttributes;

import lombok.Data;

@Data
public class CBlockLocalVariableDto {
    private String name;
    private String variableType;
    private String initValue;
    private int arraySize;
}
