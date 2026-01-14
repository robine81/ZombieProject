package com.lexicon.ZombieProject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToOne
    @JoinColumn(name = "transition_id")
    private Transition transition;

    @ManyToOne
    @JoinColumn(name = "scene_id")
    private Scene scene;

    public Item() {
    }

    public Item(String name, String description, Transition transition, Scene scene) {
        this.name = name;
        this.description = description;
        this.transition = transition;
        this.scene = scene;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Transition getTransition() {
        return transition;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
