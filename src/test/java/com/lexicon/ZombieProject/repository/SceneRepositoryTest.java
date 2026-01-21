package com.lexicon.ZombieProject.repository;

import com.lexicon.ZombieProject.entity.Scene;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
public class SceneRepositoryTest {
    @Autowired
    private SceneRepository sceneRepository;

    private Scene testScene;

    @BeforeEach
    void setup(){
        sceneRepository.deleteAll();

        testScene = new Scene();
        testScene.setSceneName("Dark Forest");
        testScene.setDescription("A mysterious dark forest");
    }

    @AfterEach
    void tearDown(){
        sceneRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return scene when name exists")
    void findBySceneName_ShouldReturnScene_WhenNameExists() {
        //arrange
        sceneRepository.save(testScene);

        //act
        Optional<Scene> found = sceneRepository.findBySceneName("Dark Forest");

        //assert
        assertTrue(found.isPresent());
        assertEquals("Dark Forest", found.get().getSceneName());
        assertEquals("A mysterious dark forest", found.get().getDescription());
    }

    @Test
    @DisplayName("findBySceneName should return empty when name does not exist")
    void findBySceneName_ShouldReturnEmpty_WhenNameDoesNotExist(){
        //Act
        Optional<Scene> found = sceneRepository.findBySceneName("NonExistent");

        //Assert
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("existsBySceneName should return true when name exists")
    void existsBySceneName_ShouldReturnTrue_WhenNameExists(){
        //arrange
        sceneRepository.save(testScene);

        //act
        boolean exists = sceneRepository.existsBySceneName("Dark Forest");

        //assert
        assertTrue(exists);
    }

    @Test
    @DisplayName("existsBySceneName should return false when name does not exist")
    void existsBySceneName_ShouldReturnFalse_WhenNameDoesNotExist(){
        //arrange
        sceneRepository.save(testScene);

        //act
        boolean exists = sceneRepository.existsBySceneName("NonExistent");

        //assert
        assertFalse(exists);
    }

    @Test
    @DisplayName("findBySceneName should be case sensitive")
    void findBySceneName_ShouldBeCaseSensitive() {
        // Arrange
        sceneRepository.save(testScene);

        // Act
        Optional<Scene> found = sceneRepository.findBySceneName("dark forest");

        // Assert
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("existsBySceneName should be case sensitive")
    void existsBySceneName_ShouldBeCaseSensitive() {
        // Arrange
        sceneRepository.save(testScene);

        // Act
        boolean exists = sceneRepository.existsBySceneName("DARK FOREST");

        // Assert
        assertFalse(exists);
    }



}
