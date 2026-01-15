package com.lexicon.ZombieProject.controller;

import com.lexicon.ZombieProject.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService service;

    public GameController(GameService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getCurrentScene(){
        return ResponseEntity.ok(service.getCurrentScene());
    }

    @GetMapping("/{optionId}")
    public ResponseEntity<?> selectOption(@PathVariable int optionId){
        return ResponseEntity.ok(service.executeTransition(optionId));
    }
}
