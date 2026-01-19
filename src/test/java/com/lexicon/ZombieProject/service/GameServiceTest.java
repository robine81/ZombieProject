package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.Transition;
import com.lexicon.ZombieProject.entity.dto.SceneInterfaceDTO;
import com.lexicon.ZombieProject.repository.SceneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    SceneRepository repository;

    @InjectMocks
    GameService service;

    @BeforeEach
    public void setup(){
        Scene originScene = new Scene();
        originScene.setDescription("You find yourself in a serene orchard. The citrus trees are in bloom. A sweet aroma washes over you.");

        Scene otherScene = new Scene();
        otherScene.setDescription("The gazebo has eaten you. You are dead.");

        Transition returnTransition = new Transition();
        returnTransition.setOriginScene(originScene);
        returnTransition.setTargetScene(originScene);
        returnTransition.setSceneDescription("A branch hangs low, heavy in bloom.");
        returnTransition.setChoiceDescription("Smell the flowers.");
        returnTransition.setEnabled(true);
        returnTransition.setEnabledTransitions(new ArrayList<>());
        returnTransition.setDisabledTransitions(new ArrayList<>());
        returnTransition.setRequiredItems(new ArrayList<>());

        Transition transition = new Transition();
        transition.setOriginScene(originScene);
        transition.setTargetScene(otherScene);
        transition.setSceneDescription("Among the trees you spot a small gazebo offering shade from the sweltering midday sun.");
        transition.setChoiceDescription("Approach the gazebo.");
        transition.setEnabled(true);
        transition.setEnabledTransitions(new ArrayList<>());
        transition.setDisabledTransitions(new ArrayList<>());
        transition.setRequiredItems(new ArrayList<>());

        List<Transition> transitions = new ArrayList<>();
        transitions.add(returnTransition);
        transitions.add(transition);

        originScene.setOutgoingTransitions(transitions);
        otherScene.setOutgoingTransitions(new ArrayList<>());

        when(repository.findById(1L)).thenReturn(Optional.of(originScene));
        //service.setCurrentScene(originScene);
    }

    @Test
    @DisplayName("getCurrentScene returns a properly formatted dto")
    void sceneDescriptionTest(){

        SceneInterfaceDTO sceneInterfaceDTO = service.getCurrentScene();

        assertEquals("You find yourself in a serene orchard. The citrus trees are in bloom. A sweet aroma washes over you.\n\nA branch hangs low, heavy in bloom.\n\nAmong the trees you spot a small gazebo offering shade from the sweltering midday sun.\n\n",
                sceneInterfaceDTO.getDescription());
        assertEquals("Smell the flowers.", sceneInterfaceDTO.getOptions().get(1));
        assertEquals("Approach the gazebo.", sceneInterfaceDTO.getOptions().get(2));
    }

    @Test
    @DisplayName("Transition back to same scene works")
    void transitionToSameScene(){
        SceneInterfaceDTO originScene = service.getCurrentScene();
        SceneInterfaceDTO transitionScene = service.executeTransition(1);
        SceneInterfaceDTO newCurrentScene = service.getCurrentScene();

        assertEquals(originScene.getDescription(), transitionScene.getDescription());
        assertEquals(originScene.getDescription(), newCurrentScene.getDescription());
    }

    @Test
    @DisplayName("Transition to new scene works")
    void transitionToNewScene(){
        SceneInterfaceDTO originScene = service.getCurrentScene();
        SceneInterfaceDTO transitionScene = service.executeTransition(2);
        SceneInterfaceDTO newCurrentScene = service.getCurrentScene();

        assertNotEquals(originScene.getDescription(), transitionScene.getDescription());
        assertNotEquals(originScene.getDescription(), newCurrentScene.getDescription());

        assertEquals("The gazebo has eaten you. You are dead.\n\n", transitionScene.getDescription());
        assertEquals("The gazebo has eaten you. You are dead.\n\n", newCurrentScene.getDescription());
    }
}
