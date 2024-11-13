package ru.mpei.plcbackrefactoring.model.dto.project;

public enum Scopes {
    PROJECT_CREATE("project:create"),
    PROJECT_VIEW("project:view"),
    PROJECT_EDIT("project:edit"),
    PROJECT_REMOVE("project:remove"),
    PROJECT_APP_OPERATE("project.app*:operate");

    private String value;
    Scopes(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
}
