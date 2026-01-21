package com.lexicon.ZombieProject.controller;

import com.lexicon.ZombieProject.entity.dto.SceneDTO;
import com.lexicon.ZombieProject.service.SceneService;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scene")
public class SceneController {

    private final SceneService service;

    public SceneController(SceneService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<SceneDTO> create(@RequestBody SceneDTO sceneDTO){
        if(service.existsByName(sceneDTO.getSceneName())){
            return ResponseEntity.status(409).build();
        }
        return  ResponseEntity.status(201).body(service.create(sceneDTO));
    }

    @GetMapping
    public ResponseEntity<List<SceneDTO>> getAllScenes(){
        List <SceneDTO> scenes = service.getAllScenes();
        return ResponseEntity.ok(scenes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SceneDTO> getSceneById(@PathVariable Long id) {
        SceneDTO sceneDTO = service.getSceneById(id);
        return ResponseEntity.ok(sceneDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SceneDTO> update(@PathVariable Long id, @RequestBody SceneDTO sceneDTO){
        return service.update(id, sceneDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Min(value = 1, message = "Enter a non-zero value for id.") @PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
