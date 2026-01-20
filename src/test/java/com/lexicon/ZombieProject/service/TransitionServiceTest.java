package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.Transition;
import com.lexicon.ZombieProject.entity.dto.TransitionDTO;
import com.lexicon.ZombieProject.repository.TransitionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransitionServiceTest {

    @Mock
    private TransitionRepository transitionRepository;

    @Mock
    private TransitionMapper transitionMapper;

    @InjectMocks
    private TransitionService transitionService;

    private Transition transition1;
    private TransitionDTO transition1DTO;

    private Item item1;
    private Item item2;

    private Scene scene1;
    private Scene scene2;

    @BeforeEach
    void setUp () {
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setName("Sword");
        item2.setName("Gun");
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        scene1 = new Scene();
        scene1.setId(1L);
        scene1.setSceneName("StartScene");
        scene1.setDescription("You find yourself at the start");
        scene1.setItems(items);

        scene2 = new Scene();
        scene2.setId(2L);
        scene2.setSceneName("EndScene");
        scene2.setDescription("You find yourself at the end");
        scene2.setItems(new ArrayList<>());

        transition1 = new Transition.Builder()
                .originScene(scene1)
                .targetScene(scene2)
                .sceneDescription("You are in a lush garden pregnant with the stench of decay")
                .choiceDescription("Dunno")
                .requiredItems(List.of(item1))
                .consumesRequiredItems(false)
                .owner(item2)
                .enabledTransitions(List.of(new Transition()))
                .enabledBy(List.of(new Transition()))
                .disabledTransitions(List.of(new Transition()))
                .disabledBy(List.of(new Transition()))
                .isEnabled(true)
                .name("A5S1L1-garden")
                .build();

        transition1DTO = new TransitionDTO.Builder()
                .id(1L)
                .originScene(scene1)
                .targetScene(scene2)
                .sceneDescription("You are in a lush garden pregnant with the stench of decay")
                .choiceDescription("Dunno")
                .requiredItems(List.of(item1))
                .consumesRequiredItems(false)
                .owner(item2)
                .enabledTransitions(List.of(new Transition()))
                .enabledBy(List.of(new Transition()))
                .disabledTransitions(List.of(new Transition()))
                .disabledBy(List.of(new Transition()))
                .isEnabled(true)
                .name("A5S1L1-garden")
                .build();
    }

    @Test
    @DisplayName("Get all Transitions should return list of all Transitions")
    void getAllTransitions () {
        List<Transition> transitionList = Arrays.asList(transition1);
        when(transitionRepository.findAll()).thenReturn(transitionList);
        when(transitionMapper.toTransitionDTO(transition1)).thenReturn(transition1DTO);

        List<TransitionDTO> result = transitionService.getAllTransitions();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(transitionRepository).findAll();
    }

    @Test
    @DisplayName("Get Transition by name should return null if Transition not found by name")
    void getTransitionByName () {
        when(transitionRepository.findByName("test-transition")).thenReturn(null);
        when(transitionMapper.toTransitionDTO(null)).thenReturn(null);

        TransitionDTO result = transitionService.getTransitionByName("test-transition");

        assertNull(result);
        verify(transitionRepository).findByName("test-transition");
    }

    @Test
    @DisplayName("Exists by name should return true if Transition by name is found")
    void existsByName () {
        when(transitionRepository.existsByName("A5S1L1-garden")).thenReturn(true);

        Boolean result = transitionService.existsByName("A5S1L1-garden");

        assertTrue(result);
        verify(transitionRepository).existsByName("A5S1L1-garden");
    }

    @Test
    @DisplayName("Get Transition by ID should return DTO when Transition exists")
    void getTransitionById () {
        when(transitionRepository.findById(1L)).thenReturn(Optional.of(transition1));
        when(transitionMapper.toTransitionDTO(transition1)).thenReturn(transition1DTO);

        TransitionDTO result = transitionService.getTransitionById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("A5S1L1-garden", result.getName());
        verify(transitionRepository).findById(1L);
        verify(transitionMapper).toTransitionDTO(transition1);
    }

    @Test
    @DisplayName("Create a Transition")
    void createTransition () {
        when(transitionMapper.toTransitionEntity(transition1DTO)).thenReturn(transition1);
        when(transitionRepository.save(transition1)).thenReturn(transition1);
        when(transitionMapper.toTransitionDTO(transition1)).thenReturn(transition1DTO);

        TransitionDTO result = transitionService.createTransition(transition1DTO);

        assertNotNull(result);
        assertEquals("A5S1L1-garden", result.getName());
        assertEquals(scene1, result.getOriginScene());
        assertEquals(scene2, result.getTargetScene());
        verify(transitionMapper).toTransitionEntity(transition1DTO);
        verify(transitionRepository).save(transition1);
        verify(transitionMapper).toTransitionDTO(transition1);
    }

    @Test
    @DisplayName("Update Transition should update values")
    void updateTransition () {
        TransitionDTO updatedDTO = new TransitionDTO.Builder()
                .id(1L)
                .originScene(scene2)
                .targetScene(scene1)
                .sceneDescription("Updated description")
                .choiceDescription("Updated choice")
                .consumesRequiredItems(true)
                .owner(item1)
                .enabledTransitions(List.of())
                .enabledBy(List.of())
                .disabledTransitions(List.of())
                .disabledBy(List.of())
                .isEnabled(false)
                .name("Updated-name")
                .build();

        when(transitionRepository.findById(1L)).thenReturn(Optional.of(transition1));
        when(transitionRepository.save(transition1)).thenReturn(transition1);
        when(transitionMapper.toTransitionDTO(transition1)).thenReturn(updatedDTO);

        TransitionDTO result = transitionService.updateTransition(1L, updatedDTO);

        assertNotNull(result);
        assertEquals("Updated-name", result.getName());
        assertEquals("Updated description", result.getSceneDescription());
        assertFalse(result.getEnabled());
        verify(transitionRepository).findById(1L);
        verify(transitionRepository).save(transition1);
        verify(transitionMapper).toTransitionDTO(transition1);
    }

    @Test
    @DisplayName("Delete Transition should delete transition")
    void deleteTransition () {
        when(transitionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(transitionRepository).deleteById(1L);

        transitionService.deleteTransition(1L);

        verify(transitionRepository).existsById(1L);
        verify(transitionRepository).deleteById(1L);
    }
}