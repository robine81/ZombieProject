package com.lexicon.ZombieProject.controller;

import com.lexicon.ZombieProject.entity.dto.TransitionDTO;
import com.lexicon.ZombieProject.service.TransitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
