package com.lexicon.ZombieProject.entity.dto;

import com.lexicon.ZombieProject.entity.InventoryEntry;
import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.Transition;
import com.lexicon.ZombieProject.service.component.Inventory;

import java.util.List;

public class ItemDTO {
    private Long id;
    private String name;
    private String description;
    private Long transitionId;
    private Long inventoryEntryId;
    private Long sceneId;
    private String sceneName;

    public ItemDTO() {}

    public ItemDTO(Long id, String name, String description, Long transitionId, Long inventoryEntryId, Long sceneId, String sceneName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.transitionId = transitionId;
        this.inventoryEntryId = inventoryEntryId;
        this.sceneId = sceneId;
        this.sceneName = sceneName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Long getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(Long transitionId) {
        this.transitionId = transitionId;
    }

    public Long getInventoryEntryId() {
        return inventoryEntryId;
    }

    public void setInventoryEntryId(Long inventoryEntryId) {
        this.inventoryEntryId = inventoryEntryId;
    }

    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }
}
