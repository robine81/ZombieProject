package com.lexicon.ZombieProject.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private String name;

    public Transition() {
    }

    public Transition(Builder builder) {
        this.originScene = builder.originScene;
        this.targetScene = builder.targetScene;
        this.sceneDescription = builder.sceneDescription;
        this.choiceDescription = builder.choiceDescription;
        this.requiredItems = builder.requiredItems;
        this.consumesRequiredItems = builder.consumesRequiredItems;
        this.owner = builder.owner;
        this.enabledTransitions = builder.enabledTransitions;
        this.enabledBy = builder.enabledBy;
        this.disabledTransitions = builder.disabledTransitions;
        this.disabledBy = builder.disabledBy;
        this.isEnabled = builder.isEnabled;
        this.name = builder.name;
    }

    public static class Builder {
        private Scene originScene;
        private Scene targetScene;
        private String sceneDescription;
        private String choiceDescription;
        private List<Item> requiredItems = new ArrayList<>();
        private Boolean consumesRequiredItems;
        private Item owner;
        private List<Transition> enabledTransitions = new ArrayList<>();
        private List<Transition> enabledBy = new ArrayList<>();
        private List<Transition> disabledTransitions = new ArrayList<>();
        private List<Transition> disabledBy = new ArrayList<>();
        private Boolean isEnabled;
        private String name;

        public Builder originScene(Scene originScene) {
            this.originScene = originScene;
            return this;
        }

        public Builder targetScene(Scene targetScene) {
            this.targetScene = targetScene;
            return this;
        }

        public Builder sceneDescription(String sceneDescription) {
            this.sceneDescription = sceneDescription;
            return this;
        }

        public Builder choiceDescription(String choiceDescription) {
            this.choiceDescription = choiceDescription;
            return this;
        }

        public Builder requiredItems(List<Item> requiredItems) {
            this.requiredItems = requiredItems;
            return this;
        }

        public Builder consumesRequiredItems(Boolean consumesRequiredItems) {
            this.consumesRequiredItems = consumesRequiredItems != null ? consumesRequiredItems : false;
            return this;
        }

        public Builder owner(Item owner) {
            this.owner = owner;
            return this;
        }

        public Builder enabledTransitions(List<Transition> enabledTransitions) {
            if(this.enabledTransitions == null) {
                this.enabledTransitions = new ArrayList<>();
            }
            this.enabledTransitions.addAll(enabledTransitions);
            return this;
        }

        public Builder enabledBy(List<Transition> enabledBy) {
            if(this.enabledBy == null) {
                this.enabledBy = new ArrayList<>();
            }
            this.enabledBy.addAll(enabledBy);
            return this;
        }

        public Builder disabledTransitions(List<Transition> disabledTransitions) {
            if(this.disabledTransitions == null) {
                this.disabledTransitions = new ArrayList<>();
            }
            this.disabledTransitions.addAll(disabledTransitions);
            return this;
        }

        public Builder disabledBy(List<Transition> disabledBy) {
            if(this.disabledBy == null) {
                this.disabledBy = new ArrayList<>();
            }
            this.disabledBy.addAll(disabledBy);
            return this;
        }

        public Builder isEnabled(Boolean isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Transition build() {
            return new Transition(this);
        }
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

    public void addEnabledTransition(Transition transition){
        enabledTransitions.add(transition);
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

    public void addDisabledTransition(Transition transition){
        disabledTransitions.add(transition);
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

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Item execute(){
        if (enabledTransitions != null) {
            for (Transition transition : enabledTransitions){
                transition.setEnabled(true);
            }
        }
        if (disabledTransitions != null) {
            for (Transition transition : disabledTransitions){
                transition.setEnabled(false);
            }
        }
        return owner;
    }
}
