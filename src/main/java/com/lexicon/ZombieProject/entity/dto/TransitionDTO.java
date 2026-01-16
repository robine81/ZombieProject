package com.lexicon.ZombieProject.entity.dto;

import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.Transition;

import java.util.ArrayList;
import java.util.List;

public class TransitionDTO {
    private Long id;
    private Scene originScene;
    private Scene targetScene;
    private String sceneDescription;
    private String choiceDescription;
    private List<Item> requiredItems;
    private Boolean consumesRequiredItems = false;
    private Item owner;
    private List<Transition> enabledTransitions;
    private List<Transition> enabledBy;
    private List<Transition> disabledTransitions;
    private List<Transition> disabledBy;
    private Boolean isEnabled;
    private String name;

    public TransitionDTO() {}

    public TransitionDTO(Builder builder) {
        this.id = builder.id;
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
        private Long id;
        private Scene originScene;
        private Scene targetScene;
        private String sceneDescription;
        private String choiceDescription;
        private List<Item> requiredItems;
        private Boolean consumesRequiredItems;
        private Item owner;
        private List<Transition> enabledTransitions;
        private List<Transition> enabledBy;
        private List<Transition> disabledTransitions;
        private List<Transition> disabledBy;
        private Boolean isEnabled;
        private String name;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

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

        public Builder transitionName(String transitionName) {
            this.name = transitionName;
            return this;
        }

        public TransitionDTO build() {
            return new TransitionDTO(this);
        }
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Scene getOriginScene () {
        return originScene;
    }

    public void setOriginScene (Scene originScene) {
        this.originScene = originScene;
    }

    public Scene getTargetScene () {
        return targetScene;
    }

    public void setTargetScene (Scene targetScene) {
        this.targetScene = targetScene;
    }

    public String getSceneDescription () {
        return sceneDescription;
    }

    public void setSceneDescription (String sceneDescription) {
        this.sceneDescription = sceneDescription;
    }

    public String getChoiceDescription () {
        return choiceDescription;
    }

    public void setChoiceDescription (String choiceDescription) {
        this.choiceDescription = choiceDescription;
    }

    public List<Item> getRequiredItems () {
        return requiredItems;
    }

    public void setRequiredItems (List<Item> requiredItems) {
        this.requiredItems = requiredItems;
    }

    public Boolean getConsumesRequiredItems () {
        return consumesRequiredItems;
    }

    public void setConsumesRequiredItems (Boolean consumesRequiredItems) {
        this.consumesRequiredItems = consumesRequiredItems;
    }

    public Item getOwner () {
        return owner;
    }

    public void setOwner (Item owner) {
        this.owner = owner;
    }

    public List<Transition> getEnabledTransitions () {
        return enabledTransitions;
    }

    public void setEnabledTransitions (List<Transition> enabledTransitions) {
        this.enabledTransitions = enabledTransitions;
    }

    public List<Transition> getEnabledBy () {
        return enabledBy;
    }

    public void setEnabledBy (List<Transition> enabledBy) {
        this.enabledBy = enabledBy;
    }

    public List<Transition> getDisabledTransitions () {
        return disabledTransitions;
    }

    public void setDisabledTransitions (List<Transition> disabledTransitions) {
        this.disabledTransitions = disabledTransitions;
    }

    public List<Transition> getDisabledBy () {
        return disabledBy;
    }

    public void setDisabledBy (List<Transition> disabledBy) {
        this.disabledBy = disabledBy;
    }

    public Boolean getEnabled () {
        return isEnabled;
    }

    public void setEnabled (Boolean enabled) {
        isEnabled = enabled;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String toString () {
        return "TransitionDTO{" +
                "id=" + id +
                ", originScene=" + originScene +
                ", targetScene=" + targetScene +
                ", sceneDescription='" + sceneDescription + '\'' +
                ", choiceDescription='" + choiceDescription + '\'' +
                ", requiredItems=" + requiredItems +
                ", consumeRequiredItems=" + consumesRequiredItems +
                ", owner=" + owner +
                ", enabledTransitions=" + enabledTransitions +
                ", enabledBy=" + enabledBy +
                ", disabledTransitions=" + disabledTransitions +
                ", disabledBy=" + disabledBy +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
