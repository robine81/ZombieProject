package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.Transition;
import com.lexicon.ZombieProject.entity.dto.SceneInterfaceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameServiceTest {

    GameService service;

    public GameServiceTest(){
        service = new GameService();
    }

    @BeforeEach
    public void setup(){
        Scene scene = new Scene();
        scene.setDescription("You find yourself in a serene orchard. The citrus trees are in bloom. A sweet aroma washes over you.");
        Transition transition = new Transition();
        transition.setOriginScene(scene);
        transition.setTargetScene(scene);
        transition.setSceneDescription("A branch hangs low, heavy in bloom.");
        transition.setChoiceDescription("Smell the flowers.");
        transition.setEnabled(true);
        List<Transition> transitions = new ArrayList<>();
        transitions.add(transition);
        scene.setOutgoingTransitions(transitions);

        service.setCurrentScene(scene);
    }

    @Test
    @DisplayName("getCurrentScene returns a properly formatted dto")
    void sceneDescriptionTest(){

        SceneInterfaceDTO sceneInterfaceDTO = service.getCurrentScene();

        assertEquals("You find yourself in a serene orchard. The citrus trees are in bloom. A sweet aroma washes over you.\n\nA branch hangs low, heavy in bloom.\n\n",
                sceneInterfaceDTO.getDescription());
        assertEquals("Smell the flowers.", sceneInterfaceDTO.getOptions().get(1));
    }
}
