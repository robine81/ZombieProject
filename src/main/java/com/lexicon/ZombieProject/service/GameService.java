package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.Transition;
import com.lexicon.ZombieProject.entity.dto.SceneInterfaceDTO;
import com.lexicon.ZombieProject.exception.ResourceNotFoundException;
import com.lexicon.ZombieProject.repository.SceneRepository;
import com.lexicon.ZombieProject.service.component.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private Player player;

    private Scene currentScene;

    private final SceneRepository sceneRepository;

    public GameService(SceneRepository sceneRepository){
        this.sceneRepository = sceneRepository;
    }

    public SceneInterfaceDTO getCurrentScene(){
        if (currentScene == null){
            currentScene = sceneRepository.findById(1L)
                    .orElseThrow(() -> new ResourceNotFoundException("Couldn't find start scene"));
        }
        return sceneToDto(currentScene);
    }

    public SceneInterfaceDTO executeTransition(int optionIndex){
        Transition chosenTransition = currentScene.getAllTransitions().get(optionIndex - 1);
        if (transitionChoosable(chosenTransition)){
            Item rewardedItem = chosenTransition.execute();
            if (rewardedItem != null) {
                player.getInventory().addItem(rewardedItem);
            }
            for (Item item : chosenTransition.getRequiredItems()){
                player.getInventory().consumeItem(item);
            }
            currentScene = chosenTransition.getTargetScene();
        }
        return sceneToDto(currentScene);
    }

    private SceneInterfaceDTO sceneToDto(Scene scene){
        SceneInterfaceDTO dto = new SceneInterfaceDTO();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(scene.getDescription()).append("\n\n");
        for(Transition transition : scene.getAllTransitions()){
            if (!transition.getEnabled()) continue;
            stringBuilder.append(transition.getSceneDescription()).append("\n\n");
        }
        dto.setDescription(stringBuilder.toString());

        Map<Integer, String> optionsMap = new HashMap<>();
        for (int i = 0; i < scene.getAllTransitions().size(); i++){
            if (transitionChoosable(scene.getAllTransitions().get(i))){
                optionsMap.put(i + 1, scene.getAllTransitions().get(i).getChoiceDescription());
            }
        }
        dto.setOptions(optionsMap);

        return dto;
    }

    private boolean transitionChoosable(Transition transition) {
        if (transition.getEnabled()) {
            if (transition.getRequiredItems().isEmpty()) {
                return true;
            } else {
                List<Item> requiredItems = transition.getRequiredItems();
                for (Item item : requiredItems){
                    if (!player.getInventory().hasItem(item)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    protected void setCurrentScene(Scene scene){
        currentScene = scene;
    }
}
