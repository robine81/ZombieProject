package com.lexicon.ZombieProject.repository;

import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.Transition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class TransitionRepositoryTest {
    /*
    @Autowired
    private TransitionRepository transitionRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Transition savedTransition;

    @BeforeEach
    void setUp() {
        transitionRepository.deleteAll();
        entityManager.flush();

        Transition transition = new Transition.Builder()
                .name("TestTransition")
                .originScene(new Scene())
                .targetScene(new Scene())
                .sceneDescription("A test scene")
                .choiceDescription("Make a choice")
                .isEnabled(true)
                .build();

        savedTransition = entityManager.persistAndFlush(transition);
    }

    @Test
    void findByName() {
        Transition result = transitionRepository.findByName("TestTransition");

        assertEquals("TestTransition", result.getName());
    }

    @Test
    void existsByName () {
    }
    */
}