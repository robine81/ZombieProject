package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.dto.SceneDTO;
import com.lexicon.ZombieProject.exception.ResourceAlreadyExistsException;
import com.lexicon.ZombieProject.exception.ResourceNotFoundException;
import com.lexicon.ZombieProject.repository.SceneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SceneServiceTest {

    @Mock
    private SceneRepository repository;

    @Mock
    private SceneMapper mapper;

    @InjectMocks
    private SceneService service;

    private Scene scene1;
    private Scene scene2;
    private SceneDTO sceneDTO1;
    private SceneDTO sceneDTO2;

    @BeforeEach
    public void setup(){
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

        sceneDTO1 = new SceneDTO();
        sceneDTO1.setId(1L);
        sceneDTO1.setSceneName("StartScene");
        sceneDTO1.setDescription("You find yourself at the start");

        sceneDTO2 = new SceneDTO();
        sceneDTO2.setId(2L);
        sceneDTO2.setSceneName("EndScene");
        sceneDTO2.setDescription("You find yourself at the end");
    }

    @Test
    @DisplayName("createScene should return created SceneDTO")
    void createScene_ShouldReturnCreatedSceneDTO() {
        //Arrange
        when(mapper.toSceneEntity(sceneDTO1)).thenReturn(scene1);
        when(repository.save(any(Scene.class))).thenReturn(scene1);
        when(mapper.toSceneDTO(scene1)).thenReturn(sceneDTO1);

        //Act
        SceneDTO result = service.create(sceneDTO1);

        //Assert
        assertNotNull(result);
        assertEquals("StartScene", result.getSceneName());
        verify(repository, times(1)).save(any(Scene.class));
        verify(mapper, times(1)).toSceneDTO(scene1);
    }

    @Test
    @DisplayName("createScene throws ResourceAlreadyExistsException when scene exists")
    void createScene_ThrowsResourceAlreadyExistsException_WhenSceneExists() {
        //Arrange
        when(repository.existsBySceneName("StartScene")).thenReturn(true);

        //Act & Assert
        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> service.create(sceneDTO1));

        assertTrue(exception.getMessage().contains("StartScene"));
        verify(repository, times(1)).existsBySceneName("StartScene");
        verify(repository, never()).save(any(Scene.class));
    }

    @Test
    @DisplayName("Should return list of sceneDTOs as a properly formatted dto")
    void getAllScenes_ShouldReturnListOfSceneDTOs(){
        //Arrange
        List<Scene> scenes = Arrays.asList(scene1, scene2);
        when(repository.findAll()).thenReturn(scenes);
        when(mapper.toSceneDTO(scene1)).thenReturn(sceneDTO1);
        when(mapper.toSceneDTO(scene2)).thenReturn(sceneDTO2);

        //Act
        List<SceneDTO> result = service.getAllScenes();

        //Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("StartScene", result.get(0).getSceneName());
        assertEquals("EndScene", result.get(1).getSceneName());
        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).toSceneDTO(any(Scene.class));
    }

    @Test
    @DisplayName("getAllScenes returns empty list when no scenes exist")
    void getAllScenes_ReturnsEmptyList_WhenNoScenesExist() {
        //Arrange
        when(repository.findAll()).thenReturn(new ArrayList<>());

        //Act
        List<SceneDTO> result = service.getAllScenes();

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
        verify(mapper, never()).toSceneDTO(any(Scene.class));
    }

    @Test
    @DisplayName("getScene returns SceneDTO when scene exists")
    void getScene_ReturnsSceneDTO_WhenSceneExists() {
        //Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(scene1));
        when(mapper.toSceneDTO(scene1)).thenReturn(sceneDTO1);

        //Act
        SceneDTO result = service.getSceneById(1L);

        //Assert
        assertNotNull(result);
        assertEquals("StartScene", result.getSceneName());
        assertEquals("You find yourself at the start", result.getDescription());
        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).toSceneDTO(scene1);
    }

    @Test
    @DisplayName("getScene throws ResourceNotFoundException when scene not found")
    void getScene_ThrowsResourceNotFoundException_WhenSceneNotFound() {
        //Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.getSceneById(999L));

        assertEquals("Scene not found with id: 999", exception.getMessage());
        verify(repository, times(1)).findById(999L);
        verify(mapper, never()).toSceneDTO(any(Scene.class));
    }

    @Test
    @DisplayName("Should return updated sceneDTO when item exists")
    void update_ShouldReturnUpdatedSceneDTO_WhenSceneExists() {
        //Arrange
        SceneDTO updateDTO = new SceneDTO(null, "Updated StartScene", "Updated description");
        Scene updatedScene = new Scene();
        updatedScene.setId(1L);
        updatedScene.setSceneName("Updated StartScene");
        updatedScene.setDescription("Updated description");

        when(repository.findById(1L)).thenReturn(Optional.of(scene1));
        when(repository.save(any(Scene.class))).thenReturn(updatedScene);
        when(mapper.toSceneDTO(updatedScene)).thenReturn(
                new SceneDTO(1L, "Updated StartScene", "Updated description")
        );

        //Act
        Optional<SceneDTO> result = service.update(1L, updateDTO);

        //Assert
        assertTrue(result.isPresent());
        assertEquals("Updated StartScene", result.get().getSceneName());
        assertEquals("Updated description", result.get().getDescription());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Scene.class));
        verify(mapper, times(1)).toSceneDTO(updatedScene);
    }

    @Test
    @DisplayName("Should return empty Optional when scene not found")
    void updateScene_ShouldReturnsEmpty_WhenSceneNotFound() {
        //Arrange
        SceneDTO updateDTO = new SceneDTO(null, "Updated", "Description");
        when(repository.findById(999L)).thenReturn(Optional.empty());

        //Act
        Optional<SceneDTO> result = service.update(999L, updateDTO);

        //Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(999L);
        verify(repository, never()).save(any(Scene.class));
        verify(mapper, never()).toSceneDTO(any(Scene.class));
    }

    @Test
    @DisplayName("deleteScene successfully deletes existing scene")
    void deleteScene_DeletesScene_WhenSceneExists() {
        //Arrange
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        //Act
        service.delete(1L);

        //Assert
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteScene throws ResourceNotFoundException when scene not found")
    void deleteScene_ThrowsResourceNotFoundException_WhenSceneNotFound() {
        //Arrange
        when(repository.existsById(999L)).thenReturn(false);

        //Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.delete(999L));

        assertEquals("Scene not found with id: 999", exception.getMessage());
        verify(repository, times(1)).existsById(999L);
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("nameExists should return true if scene name exists")
    void nameExists_ShouldReturnTrue_WhenNameExists() {
        //Arrange
        when(repository.existsBySceneName("StartScene")).thenReturn(true);

        //Act
        boolean result = service.existsByName("StartScene");

        //Assert
        assertTrue(result);
        verify(repository, times(1)).existsBySceneName("StartScene");
    }

    @Test
    @DisplayName("nameExists should return false if scene name does not exist")
    void nameExists_ShouldReturnFalse_WhenNameDoesNotExist() {
        //Arrange
        when(repository.existsBySceneName("NonExistent")).thenReturn(false);

        //Act
        boolean result = service.existsByName("NonExistent");

        //Assert
        assertFalse(result);
        verify(repository, times(1)).existsBySceneName("NonExistent");
    }
}