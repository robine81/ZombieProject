package com.lexicon.ZombieProject.entity.dto;

import java.util.Map;

public class SceneInterfaceDTO {
    private String name;
    private String description;
    private Map<Integer, String> options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Integer, String> getOptions() {
        return options;
    }

    public void setOptions(Map<Integer, String> options) {
        this.options = options;
    }
}
