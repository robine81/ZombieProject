package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.InventoryEntry;
import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.Transition;
import com.lexicon.ZombieProject.entity.dto.SceneInterfaceDTO;
import com.lexicon.ZombieProject.exception.ResourceNotFoundException;
import com.lexicon.ZombieProject.repository.SceneRepository;
import com.lexicon.ZombieProject.repository.TransitionRepository;
import com.lexicon.ZombieProject.service.component.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GameService {

    private final Player player;

    private Scene currentScene;

    private final SceneRepository sceneRepository;

    private final TransitionRepository transitionRepository;

    private Long currentSceneId = 1L;

    private final Map<Integer, Integer> optionKeyMap = new HashMap<>();

    public GameService(SceneRepository sceneRepository, TransitionRepository transitionRepository, Player player){
        this.sceneRepository = sceneRepository;
        this.transitionRepository = transitionRepository;
        this.player = player;
    }

    public SceneInterfaceDTO getCurrentScene(){
        if (currentScene == null){
            currentScene = sceneRepository.findById(currentSceneId)
                    .orElseThrow(() -> new ResourceNotFoundException("Couldn't find start scene"));
        }
        return sceneToDto(currentScene);
    }

    @Transactional
    public SceneInterfaceDTO executeTransition(int optionIndex){
        if (!optionKeyMap.containsKey(optionIndex)){
            return sceneToDto(currentScene);
        }
        Transition chosenTransition = currentScene.getAllTransitions().get(optionKeyMap.get(optionIndex));
        if (transitionChoosable(chosenTransition)){
            Item rewardedItem = chosenTransition.execute();
            if (rewardedItem != null) {
                player.getInventory().addItem(rewardedItem);
            }
            for (Item item : chosenTransition.getRequiredItems()){
                player.getInventory().consumeItem(item);
            }
            transitionRepository.save(chosenTransition);
            currentSceneId = chosenTransition.getTargetScene().getId();
            sceneRepository.save(currentScene);
            currentScene = sceneRepository.findById(currentSceneId)
                    .orElseThrow(() -> new ResourceNotFoundException("Couldn't find scene with ID = " + currentSceneId));
        }
        return sceneToDto(currentScene);
    }

    public Map<String, Integer> getInventory(){
        Map<String, Integer> inventoryMap = new HashMap<>();
        for(InventoryEntry inventoryEntry : player.getInventory().getInventoryEntries()) {
            inventoryMap.put(inventoryEntry.getItem().getName(), inventoryEntry.getAmount());
        }
        return inventoryMap;
    }

    private SceneInterfaceDTO sceneToDto(Scene scene){
        SceneInterfaceDTO dto = new SceneInterfaceDTO();
        dto.setName(scene.getSceneName());
        dto.setDescription(buildSceneDescription(scene));
        dto.setOptions(getOptionsMap(scene));
        return dto;
    }

    private String buildSceneDescription(Scene scene){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(scene.getDescription()).append("\n\n");
        for(Transition transition : scene.getAllTransitions()){
            if (!transition.getEnabled()) continue;
            stringBuilder.append(transition.getSceneDescription()).append("\n\n");
        }
        return stringBuilder.toString();
    }

    private Map<Integer, String> getOptionsMap(Scene scene){
        Map<Integer, String> optionsMap = new HashMap<>();
        optionKeyMap.clear();
        for (int i = 0; i < scene.getAllTransitions().size(); i++){
            if (transitionChoosable(scene.getAllTransitions().get(i))){
                int key = optionsMap.size() + 1;
                optionKeyMap.put(key, i);
                optionsMap.put(key, scene.getAllTransitions().get(i).getChoiceDescription());
            }
        }
        return optionsMap;
    }

    private boolean transitionChoosable(Transition transition) {
        if (transition.getEnabled()) {
            if (transition.getRequiredItems() != null && !transition.getRequiredItems().isEmpty()) {
                List<Item> requiredItems = transition.getRequiredItems();
                for (Item item : requiredItems) {
                    if (!player.getInventory().hasItem(item)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

}
