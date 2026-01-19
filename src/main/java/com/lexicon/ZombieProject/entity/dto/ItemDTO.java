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
    private Transition transition;
    private InventoryEntry inventoryEntry;
    private Scene scene;

    public ItemDTO() {}

    public ItemDTO(Long id, String name, String description, Transition transition, InventoryEntry inventoryEntry, Scene scene) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.transition = transition;
        this.inventoryEntry = inventoryEntry;
        this.scene = scene;
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

    public Transition getTransition() { return transition; }

    public void setTransition(Transition transition) { this.transition = transition; }

    public InventoryEntry getInventoryEntry() { return inventoryEntry; }

    public void setInventoryEntry(InventoryEntry inventoryEntry) {
        this.inventoryEntry = inventoryEntry;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
