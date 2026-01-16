package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.dto.SceneDTO;
import com.lexicon.ZombieProject.repository.SceneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    private List<Item> items;

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
        sceneDTO1.setItems(items);

        sceneDTO2 = new SceneDTO();
        sceneDTO2.setId(2L);
        sceneDTO2.setSceneName("EndScene");
        sceneDTO2.setDescription("You find yourself at the end");
        sceneDTO2.setItems(new ArrayList<>());
    }

    @Test
    @DisplayName("getId returns true only if id exists")
    public void testSceneExistsReturnTrue(Long id){
       // Mockito.when(repository.getSceneById(id).thenRet;
    }

    @Test
    @DisplayName("getAllScenes returns a list of all scenes as a properly formatted dto")
    void getAllScenes_ReturnListOfSceneDTOs(){
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
    }

    @Test
    @DisplayName("getScene throws RuntimeException when scene not found")
    void getScene_ThrowsRuntimeException_WhenSceneNotFound() {}

    @Test
    @DisplayName("createScene successfully creates new scene")
    void createScene_CreatesNewScene_WhenSceneDoesNotExist() {}

    @Test
    @DisplayName("createScene throws ResourceAlreadyExistsException when scene exists")
    void createScene_ThrowsResourceAlreadyExistsException_WhenSceneExists() {}

    @Test
    @DisplayName("updateScene successfully updates existing scene")
    void updateScene_UpdatesScene_WhenSceneExists() {}

    @Test
    @DisplayName("updateScene throws RuntimeException when scene not found")
    void updateScene_ThrowsRuntimeException_WhenSceneNotFound() {}

    @Test
    @DisplayName("deleteScene successfully deletes existing scene")
    void deleteScene_DeletesScene_WhenSceneExists() {}

    @Test
    @DisplayName("deleteScene throws RuntimeException when scene not found")
    void deleteScene_ThrowsRuntimeException_WhenSceneNotFound() {}
}
