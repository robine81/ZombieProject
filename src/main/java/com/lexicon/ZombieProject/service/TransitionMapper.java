package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Transition;
import com.lexicon.ZombieProject.entity.dto.TransitionDTO;
import com.lexicon.ZombieProject.repository.TransitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransitionMapper {
    private final TransitionRepository transitionRepository;

    @Autowired
    public TransitionMapper(TransitionRepository transitionRepository) {
        this.transitionRepository = transitionRepository;
    }

    public TransitionDTO toTransitionDTO(Transition transition) {
        return new TransitionDTO.Builder()
                .id(transition.getId())
                .originScene(transition.getOriginScene())
                .targetScene(transition.getTargetScene())
                .sceneDescription(transition.getSceneDescription())
                .choiceDescription(transition.getChoiceDescription())
                .requiredItems(transition.getRequiredItems())
                .consumesRequiredItems(transition.getConsumesRequiredItems())
                .owner(transition.getOwner())
                .enabledTransitions(transition.getEnabledTransitions())
                .enabledBy(transition.getEnabledBy())
                .disabledTransitions(transition.getDisabledTransitions())
                .disabledBy(transition.getDisabledBy())
                .isEnabled(transition.getEnabled())
                .build();
    }

    public Transition toTransitionEntity(TransitionDTO transitionDTO) {
        return new Transition.Builder()
                .originScene(transitionDTO.getOriginScene())
                .targetScene(transitionDTO.getTargetScene())
                .sceneDescription(transitionDTO.getSceneDescription())
                .choiceDescription(transitionDTO.getChoiceDescription())
                .requiredItems(transitionDTO.getRequiredItems())
                .consumesRequiredItems(transitionDTO.getConsumesRequiredItems())
                .owner(transitionDTO.getOwner())
                .enabledTransitions(transitionDTO.getEnabledTransitions())
                .enabledBy(transitionDTO.getEnabledBy())
                .disabledTransitions(transitionDTO.getDisabledTransitions())
                .disabledBy(transitionDTO.getDisabledBy())
                .isEnabled(transitionDTO.getEnabled())
                .build();
    }
}
