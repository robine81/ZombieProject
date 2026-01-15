package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.Transition;
import com.lexicon.ZombieProject.entity.dto.SceneInterfaceDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {
    private final Long startSceneId = 1L;
    private Scene currentScene;

    /*
    private final SceneRepository sceneRepository;

    public GameService(SceneRepository sceneRepository){
        this.sceneRepository = sceneRepository;
        startingScene = sceneRepository.findById(startingSceneId)
            .orElseThrow(() -> new ResourceNotFoundException("Couldn't find start scene"));
    }
     */

    public SceneInterfaceDTO getCurrentScene(){
        return sceneToDto(currentScene);
    }

    public SceneInterfaceDTO executeTransition(int optionIndex){
        Transition chosenTransition = currentScene.getOutgoingTransitions().get(optionIndex);
        chosenTransition.execute();
        currentScene = chosenTransition.getTargetScene();
        return sceneToDto(currentScene);
    }

    private SceneInterfaceDTO sceneToDto(Scene scene){
        SceneInterfaceDTO dto = new SceneInterfaceDTO();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(scene.getDescription()).append("\n\n");
        for(Transition transition : scene.getOutgoingTransitions()){
            if (!transition.getEnabled()) continue;
            stringBuilder.append(transition.getSceneDescription()).append("\n\n");
        }
        dto.setDescription(stringBuilder.toString());

        Map<Integer, String> optionsMap = new HashMap<>();
        for (int i = 0; i < scene.getOutgoingTransitions().size(); i++){
            if(!scene.getOutgoingTransitions().get(i).getEnabled()) continue; //TODO: add item check once inventory is done
            optionsMap.put(i + 1, scene.getOutgoingTransitions().get(i).getChoiceDescription());
        }
        dto.setOptions(optionsMap);

        return dto;
    }

    protected void setCurrentScene(Scene scene){
        currentScene = scene;
    }
}
