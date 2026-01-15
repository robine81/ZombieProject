package com.lexicon.ZombieProject.entity.dto;

import java.util.Map;

public class SceneInterfaceDTO {
    private String description;
    private Map<Integer, String> options;

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
