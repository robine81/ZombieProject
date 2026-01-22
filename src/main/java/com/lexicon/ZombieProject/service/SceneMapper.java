package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.dto.SceneDTO;
import com.lexicon.ZombieProject.repository.SceneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SceneMapper {

    public SceneMapper() {}

    public SceneDTO toSceneDTO(Scene scene) {
        return new SceneDTO(scene.getId(), scene.getSceneName(), scene.getDescription());
    }

    public Scene toSceneEntity(SceneDTO sceneDTO){
        Scene scene = new Scene();
        scene.setId(sceneDTO.getId());
        scene.setSceneName(sceneDTO.getSceneName());
        scene.setDescription(sceneDTO.getDescription());

        return scene;
    }
}
