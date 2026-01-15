package com.lexicon.ZombieProject.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "scenes")
public class Scene {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "originScene", cascade = CascadeType.ALL)
    private List<Transition> outgoingTransitions;

    @OneToMany(mappedBy = "targetScene", cascade = CascadeType.ALL)
    private List<Transition> incomingTransitions;

    private String sceneName;

    private String description;

    @OneToMany(mappedBy = "scene")
    private List<Item> items;

    public Scene() {}

    public Scene(Long id, List<Transition> outgoingTransitions, List<Transition> incomingTransitions, String sceneName, String description, List<Item> items) {
        this.id = id;
        this.outgoingTransitions = outgoingTransitions;
        this.incomingTransitions = incomingTransitions;
        this.sceneName = sceneName;
        this.description = description;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Transition> getOutgoingTransitions() {
        return outgoingTransitions;
    }

    public void setOutgoingTransitions(List<Transition> outgoingTransitions) {
        this.outgoingTransitions = outgoingTransitions;
    }

    public List<Transition> getIncomingTransitions() {
        return incomingTransitions;
    }

    public void setIncomingTransitions(List<Transition> incomingTransitions) {
        this.incomingTransitions = incomingTransitions;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void getTransitionFromItems(){

    }

    @Override
    public String toString() {
        return "Scene{" +
                "id=" + id +
                ", outgoingTransitions=" + outgoingTransitions +
                ", incomingTransitions=" + incomingTransitions +
                ", sceneName='" + sceneName + '\'' +
                ", description='" + description + '\'' +
                ", items=" + items +
                '}';
    }
}
