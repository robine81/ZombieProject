package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.Transition;
import com.lexicon.ZombieProject.entity.dto.SceneInterfaceDTO;
import com.lexicon.ZombieProject.repository.SceneRepository;
import com.lexicon.ZombieProject.service.component.Inventory;
import com.lexicon.ZombieProject.service.component.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    SceneRepository repository;

    @Mock
    Player player;

    @InjectMocks
    GameService service;

    Item item;

    @BeforeEach
    public void setup(){
        Scene originScene = new Scene();
        originScene.setSceneName("A1L1S1-orchard");
        originScene.setDescription("You find yourself in a serene orchard. The citrus trees are in bloom. A sweet aroma washes over you.");

        Scene otherScene = new Scene();
        otherScene.setSceneName("A1L2S1-gazebo");
        otherScene.setDescription("The gazebo has eaten you. You are dead.");

        Transition returnTransition = new Transition.Builder()
                .originScene(originScene)
                .targetScene(originScene)
                .sceneDescription("A branch hangs low, heavy in bloom.")
                .choiceDescription("Smell the flowers.")
                .isEnabled(true)
                .build();

        Transition exitTransition = new Transition.Builder()
                .originScene(originScene)
                .targetScene(otherScene)
                .sceneDescription("Among the trees you spot a small gazebo offering shade from the sweltering midday sun.")
                .choiceDescription("Approach the gazebo.")
                .isEnabled(true)
                .build();

        Transition itemTransition = new Transition.Builder()
                .sceneDescription("Nestled in the thick grass is a perfectly ripe and unsullied peach.")
                .choiceDescription("Take the peach.")
                .isEnabled(true)
                .build();
        itemTransition.addDisabledTransition(itemTransition);

        item = new Item();
        item.setName("Peach");
        item.setDescription("Perfectly ripe, cool to the touch, gives off a sweet smell.");
        item.setTransition(itemTransition);


        List<Transition> originOutgoingTransitions = new ArrayList<>();
        originOutgoingTransitions.add(returnTransition);
        originOutgoingTransitions.add(exitTransition);

        originScene.setOutgoingTransitions(originOutgoingTransitions);
        originScene.addItem(item);

        when(repository.findById(1L)).thenReturn(Optional.of(originScene));
    }

    @Test
    @DisplayName("getCurrentScene returns a properly formatted dto")
    void sceneDescriptionTest(){

        SceneInterfaceDTO sceneInterfaceDTO = service.getCurrentScene();

        assertEquals("""
                        You find yourself in a serene orchard. The citrus trees are in bloom. A sweet aroma washes over you.
                        
                        A branch hangs low, heavy in bloom.
                        
                        Among the trees you spot a small gazebo offering shade from the sweltering midday sun.
                        
                        Nestled in the thick grass is a perfectly ripe and unsullied peach.
                        
                        """,
                sceneInterfaceDTO.getDescription());
        assertEquals("Smell the flowers.", sceneInterfaceDTO.getOptions().get(1));
        assertEquals("Approach the gazebo.", sceneInterfaceDTO.getOptions().get(2));
        assertEquals("Take the peach.", sceneInterfaceDTO.getOptions().get(3));
    }

    @Test
    @DisplayName("Transition back to same scene works")
    void transitionToSameScene(){
        SceneInterfaceDTO originScene = service.getCurrentScene();
        SceneInterfaceDTO transitionScene = service.executeTransition(1);
        SceneInterfaceDTO newCurrentScene = service.getCurrentScene();

        assertEquals(originScene.getName(), transitionScene.getName());
        assertEquals(originScene.getName(), newCurrentScene.getName());
    }

    @Test
    @DisplayName("Transition to new scene works")
    void transitionToNewScene(){
        SceneInterfaceDTO originScene = service.getCurrentScene();
        SceneInterfaceDTO transitionScene = service.executeTransition(2);
        SceneInterfaceDTO newCurrentScene = service.getCurrentScene();

        assertNotEquals(originScene.getName(), transitionScene.getName());
        assertNotEquals(originScene.getName(), newCurrentScene.getName());

        assertEquals("The gazebo has eaten you. You are dead.\n\n", transitionScene.getDescription());
        assertEquals("The gazebo has eaten you. You are dead.\n\n", newCurrentScene.getDescription());
    }

    @Test
    @DisplayName("Transition added by item returns to scene and disables self")
    void itemTransition(){
        SceneInterfaceDTO originScene = service.getCurrentScene();
        SceneInterfaceDTO transitionScene = service.executeTransition(3);

        assertEquals(originScene.getName(), transitionScene.getName());
        assertEquals(2, transitionScene.getOptions().size());
    }
}
