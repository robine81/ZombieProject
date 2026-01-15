package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.dto.SceneDTO;
import com.lexicon.ZombieProject.exception.ResourceAlreadyExistsException;
import com.lexicon.ZombieProject.repository.SceneRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SceneService {
    private final SceneRepository repository;
    private final SceneMapper mapper;

    public SceneService(SceneRepository repository, SceneMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SceneDTO> getAllScenes(){
        List<Scene> scenes = repository.findAll();
        List<SceneDTO> sceneDTOS = new ArrayList<>();

        for(Scene s : scenes){
            sceneDTOS.add(mapper.toSceneDTO(s));
        }
        return sceneDTOS;
    }

    public SceneDTO getScene(Long id){
        Scene scene = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scene not found with id: " + id));
        return mapper.toSceneDTO(scene);
    }

    public SceneDTO createScene(SceneDTO sceneDTO){
        if(repository.existsById(sceneDTO.getId())){
            throw new ResourceAlreadyExistsException("Scene " +
                    sceneDTO.getSceneName() +
                    " already exists");
        }
        return mapper.toSceneDTO(repository.save(mapper.toSceneEntity(sceneDTO)));
    }

    public SceneDTO updateScene(Long id, SceneDTO sceneDTO){
        Scene existingScene = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scene not found with id: " + id));
        existingScene.setSceneName(sceneDTO.getSceneName());
        existingScene.setDescription(sceneDTO.getDescription());
        existingScene.setItems(sceneDTO.getItems());

        return  mapper.toSceneDTO(repository.save(existingScene));
    }

    public void deleteScene(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Scene not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
