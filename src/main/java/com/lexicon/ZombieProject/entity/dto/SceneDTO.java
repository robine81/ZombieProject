package com.lexicon.ZombieProject.entity.dto;

import com.lexicon.ZombieProject.entity.Item;

import java.util.List;

public class SceneDTO {
    private Long id;
    private String sceneName;
    private String description;

    public SceneDTO() {}

    public SceneDTO(Long id, String sceneName, String description) {
        this.id = id;
        this.sceneName = sceneName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
