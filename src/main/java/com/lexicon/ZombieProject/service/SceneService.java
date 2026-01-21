package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.dto.SceneDTO;
import com.lexicon.ZombieProject.exception.ResourceAlreadyExistsException;
import com.lexicon.ZombieProject.exception.ResourceNotFoundException;
import com.lexicon.ZombieProject.repository.SceneRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public boolean existsByName(String sceneName){ return repository.existsBySceneName(sceneName); }

    public SceneDTO getSceneById(Long id){
        Scene scene = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scene not found with id: " + id));
        return mapper.toSceneDTO(scene);
    }

    public SceneDTO create(SceneDTO sceneDTO){
        if(repository.existsBySceneName(sceneDTO.getSceneName())){
            throw new ResourceAlreadyExistsException("Scene " +
                    sceneDTO.getSceneName() +
                    " already exists");
        }
        return mapper.toSceneDTO(repository.save(mapper.toSceneEntity(sceneDTO)));
    }

    public Optional<SceneDTO> update(Long id, SceneDTO sceneDTO){
        return repository.findById(id)
                .map(scene -> {
                    scene.setSceneName(sceneDTO.getSceneName());
                    scene.setDescription(sceneDTO.getDescription());
                    Scene updated = repository.save(scene);
                    return mapper.toSceneDTO(updated);
                });
    }

    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Scene not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
