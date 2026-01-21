package com.lexicon.ZombieProject.controller;

import com.lexicon.ZombieProject.entity.dto.TransitionDTO;
import com.lexicon.ZombieProject.exception.ResourceAlreadyExistsException;
import com.lexicon.ZombieProject.service.TransitionService;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transitions")
public class TransitionController {
    private final TransitionService transitionService;

    public TransitionController(TransitionService transitionService) {
        this.transitionService = transitionService;
    }

    @GetMapping
    public ResponseEntity<List<TransitionDTO>> getAllTransitions() {
        List<TransitionDTO> transitions = transitionService.getAllTransitions();
        return ResponseEntity.ok(transitions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransitionDTO> getTransitionById(@PathVariable Long id) {
        return ResponseEntity.ok(transitionService.getTransitionById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<TransitionDTO> createTransition(@RequestBody TransitionDTO transitionDTO) {
        if(transitionService.existsByName(transitionDTO.getName())) {
            throw new ResourceAlreadyExistsException("Transition already exists with getName: " + transitionDTO.getName());
        }
        return ResponseEntity.status(201).body(transitionService.createTransition(transitionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransitionDTO> updateTransition(@PathVariable Long id, @RequestBody TransitionDTO transitionDTO) {
        return ResponseEntity.ok(transitionService.updateTransition(id, transitionDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteTransition(@Min(value = 1, message = "Enter a non-zero value for ID") @PathVariable Long id) {
        transitionService.deleteTransition(id);
    }
}
