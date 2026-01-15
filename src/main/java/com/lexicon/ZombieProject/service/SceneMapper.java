package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.dto.SceneDTO;
import com.lexicon.ZombieProject.repository.SceneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SceneMapper {
    private final SceneRepository sceneRepository;

    @Autowired

    public SceneMapper(SceneRepository sceneRepository) {
        this.sceneRepository = sceneRepository;
    }

    public SceneDTO toSceneDTO(Scene scene) {
        return new SceneDTO(scene.getId(), scene.getSceneName(), scene.getDescription(), scene.getItems());
    }

    public Scene toSceneEntity(SceneDTO sceneDTO){
        Scene scene = new Scene();
        scene.setSceneName(scene.getSceneName());
        scene.setDescription(sceneDTO.getDescription());
        scene.setItems(sceneDTO.getItems());

        return scene;
    }

    /*public SceneDTO patchScene(Long id, SceneDTO sceneDTO){
        Scene scene = sceneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scene with id: " + id + " not found"));

        if (sceneDTO.getDescription() != null) scene.getDescription(sceneDTO.getDescription());
    }*/
}
