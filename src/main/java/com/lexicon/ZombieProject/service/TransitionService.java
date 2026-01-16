package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Transition;
import com.lexicon.ZombieProject.entity.dto.TransitionDTO;
import com.lexicon.ZombieProject.repository.TransitionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransitionService {
    private final TransitionRepository repository;
    private final TransitionMapper mapper;

    public TransitionService(TransitionRepository repository, TransitionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<TransitionDTO> getAllTransitions() {
        return repository.findAll().stream().map(mapper::toTransitionDTO).collect(Collectors.toList());
    }

    public TransitionDTO getTransitionByName(String transitionName) {
        return mapper.toTransitionDTO(repository.findByTranistionName(transitionName));
    }

    public Boolean existsByTransitionName(String transitionName) {
        return repository.existsByTransitionName(transitionName);
    }

    public TransitionDTO getTransitionById(Long id) {
        Transition transition = repository.findById(id).orElseThrow(() -> new RuntimeException("Transation not found with id: " + id));
        return mapper.toTransitionDTO(transition);
    }

    public TransitionDTO createTransition(TransitionDTO transitionDTO) {
        Transition transition = mapper.toTransitionEntity(transitionDTO);
        Transition savedTransition = repository.save(transition);
        return mapper.toTransitionDTO(savedTransition);
    }

    public TransitionDTO updateTransition(Long id, TransitionDTO transitionDTO) {
        Transition transition = repository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
        transition.setOriginScene(transitionDTO.getOriginScene());
        transition.setTargetScene(transitionDTO.getTargetScene());
        transition.setSceneDescription(transitionDTO.getSceneDescription());
        transition.setChoiceDescription(transitionDTO.getChoiceDescription());
        transition.setRequiredItems(transitionDTO.getRequiredItems());
        transition.setConsumesRequiredItems(transitionDTO.getConsumesRequiredItems());
        transition.setOwner(transitionDTO.getOwner());
        transition.setEnabledTransitions(transitionDTO.getEnabledTransitions());
        transition.setEnabledBy(transitionDTO.getEnabledBy());
        transition.setDisabledTransitions(transitionDTO.getDisabledTransitions());
        transition.setDisabledBy(transitionDTO.getDisabledBy());
        transition.setEnabled(transitionDTO.getEnabled());

        return mapper.toTransitionDTO(repository.save(transition));
    }

    public void deleteTransition(Long id) {
        if(!repository.existsById(id)) {
            throw new RuntimeException("Transition not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
