package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.Transition;
import com.lexicon.ZombieProject.entity.dto.TransitionDTO;
import com.lexicon.ZombieProject.repository.TransitionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransitionMapperTest {

    @Mock
    private TransitionRepository transitionRepository;
    private TransitionMapper transitionMapper;

    @BeforeEach
    void setUp () {
        transitionMapper = new TransitionMapper(transitionRepository);
    }

    private Transition createSampleTransition() {
        return new Transition.Builder()
                .originScene(new Scene())
                .targetScene(new Scene())
                .sceneDescription("You are in a lush garden pregnant with the stench of decay")
                .choiceDescription("Dunno")
                .requiredItems(List.of(new Item()))
                .consumesRequiredItems(false)
                .owner(new Item())
                .enabledTransitions(List.of(new Transition()))
                .enabledBy(List.of(new Transition()))
                .disabledTransitions(List.of(new Transition()))
                .disabledBy(List.of(new Transition()))
                .isEnabled(true)
                .name("A5S1L1-garden")
                .build();
    }

    private TransitionDTO createSampleDTO() {
        return new TransitionDTO.Builder()
                .id(1L)
                .originScene(new Scene())
                .targetScene(new Scene())
                .sceneDescription("You are in dimly lit kitchen caked, walls caked in blood and mould")
                .choiceDescription("Choice description goes here")
                .requiredItems(List.of(new Item()))
                .consumesRequiredItems(false)
                .owner(new Item())
                .enabledTransitions(List.of(new Transition()))
                .enabledBy(List.of(new Transition()))
                .disabledTransitions(List.of(new Transition()))
                .disabledBy(List.of(new Transition()))
                .isEnabled(true)
                .name("A5S2L2-kitchen")
                .build();
    }

    @AfterEach
    void tearDown () {
    }

    @Test
    @DisplayName("Should map a Transition to a TransitionDTO")
    void toTransitionDTO () {

        Transition transitionSample = createSampleTransition();

        TransitionDTO transitionDTO = transitionMapper.toTransitionDTO(transitionSample);

        assertInstanceOf(TransitionDTO.class, transitionDTO);
        assertEquals("A5S1L1-garden", transitionDTO.getName());
    }

    @Test
    @DisplayName("Should map a TransitionDTO to a Transition")
    void toTransitionEntity () {
        TransitionDTO transitionDTOSample = createSampleDTO();

        Transition transition = transitionMapper.toTransitionEntity(transitionDTOSample);

        assertInstanceOf(Transition.class, transition);
        assertEquals("A5S2L2-kitchen", transition.getName());
    }
}