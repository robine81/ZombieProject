package com.lexicon.ZombieProject.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "transitions")
public class Transition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origin_scene_id")
    private Scene originScene;

    @ManyToOne
    @JoinColumn(name = "target_scene_id")
    private Scene targetScene;

    private String sceneDescription;
    private String choiceDescription;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "transition_items",
        joinColumns = @JoinColumn(name = "transition_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> requiredItems;

    private Boolean consumesRequiredItems = false;

    @OneToOne(cascade = CascadeType.ALL)
    private Item owner;

    // Following many-to-many relationships map what transitions are enabled or disabled when executing a given transition
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "transition_enables_transitions",
        joinColumns = @JoinColumn(name = "owner_transition_id"),
        inverseJoinColumns = @JoinColumn(name = "affected_transition_id"))
    private List<Transition> enabledTransitions;

    @ManyToMany(mappedBy = "enabledTransitions")
    private List<Transition> enabledBy;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "transition_disables_transitions",
            joinColumns = @JoinColumn(name = "owner_transition_id"),
            inverseJoinColumns = @JoinColumn(name = "affected_transition_id"))
    private List<Transition> disabledTransitions;

    @ManyToMany(mappedBy = "disabledTransitions")
    private List<Transition> disabledBy;

    private Boolean isEnabled;

    public Transition() {
    }

    public Transition(Scene originScene, Scene targetScene, String sceneDescription, String choiceDescription, List<Item> requiredItems, Boolean consumesRequiredItems, Item owner, List<Transition> enabledTransitions, List<Transition> enabledBy, List<Transition> disabledTransitions, List<Transition> disabledBy, Boolean isEnabled) {
        this.originScene = originScene;
        this.targetScene = targetScene;
        this.sceneDescription = sceneDescription;
        this.choiceDescription = choiceDescription;
        this.requiredItems = requiredItems;
        this.consumesRequiredItems = consumesRequiredItems;
        this.owner = owner;
        this.enabledTransitions = enabledTransitions;
        this.enabledBy = enabledBy;
        this.disabledTransitions = disabledTransitions;
        this.disabledBy = disabledBy;
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Scene getOriginScene() {
        return originScene;
    }

    public void setOriginScene(Scene originScene) {
        this.originScene = originScene;
    }

    public Scene getTargetScene() {
        return targetScene;
    }

    public void setTargetScene(Scene targetScene) {
        this.targetScene = targetScene;
    }

    public String getSceneDescription() {
        return sceneDescription;
    }

    public void setSceneDescription(String sceneDescription) {
        this.sceneDescription = sceneDescription;
    }

    public String getChoiceDescription() {
        return choiceDescription;
    }

    public void setChoiceDescription(String choiceDescription) {
        this.choiceDescription = choiceDescription;
    }

    public List<Item> getRequiredItems() {
        return requiredItems;
    }

    public void setRequiredItems(List<Item> requiredItems) {
        this.requiredItems = requiredItems;
    }

    public Boolean getConsumesRequiredItems() {
        return consumesRequiredItems;
    }

    public void setConsumesRequiredItems(Boolean consumesRequiredItems) {
        this.consumesRequiredItems = consumesRequiredItems;
    }

    public Item getOwner() {
        return owner;
    }

    public void setOwner(Item owner) {
        this.owner = owner;
    }

    public List<Transition> getEnabledTransitions() {
        return enabledTransitions;
    }

    public void setEnabledTransitions(List<Transition> enabledTransitions) {
        this.enabledTransitions = enabledTransitions;
    }

    public List<Transition> getEnabledBy() {
        return enabledBy;
    }

    public void setEnabledBy(List<Transition> enabledBy) {
        this.enabledBy = enabledBy;
    }

    public List<Transition> getDisabledTransitions() {
        return disabledTransitions;
    }

    public void setDisabledTransitions(List<Transition> disabledTransitions) {
        this.disabledTransitions = disabledTransitions;
    }

    public List<Transition> getDisabledBy() {
        return disabledBy;
    }

    public void setDisabledBy(List<Transition> disabledBy) {
        this.disabledBy = disabledBy;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public void execute(){
        for (Transition transition : enabledTransitions){
            transition.setEnabled(true);
        }

        for (Transition transition : disabledTransitions){
            transition.setEnabled(false);
        }
    }
}
