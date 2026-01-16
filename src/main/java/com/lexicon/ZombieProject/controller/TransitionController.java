package com.lexicon.ZombieProject.controller;

import com.lexicon.ZombieProject.entity.dto.TransitionDTO;
import com.lexicon.ZombieProject.exception.ResourceAlreadyExistsException;
import com.lexicon.ZombieProject.service.TransitionService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transitions")
public class TransitionController {
    @Autowired
    private TransitionService transitionService;

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
    public ResponseEntity<TransitionDTO> createScene(@RequestBody TransitionDTO transitionDTO) {
        if(transitionService.existsByTransitionName(transitionDTO.getTransitionName())) {
            throw new ResourceAlreadyExistsException("Transition already exists with name: " + transitionDTO.getTransitionName());
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
