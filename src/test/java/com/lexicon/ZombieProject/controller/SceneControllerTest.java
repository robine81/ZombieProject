package com.lexicon.ZombieProject.controller;


import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.repository.SceneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class SceneControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    SceneRepository sceneRepository;

    Long savedSceneId;

    @BeforeEach
    void setup() {
        sceneRepository.deleteAll();

        Scene scene = new Scene();
        scene.setSceneName("Dark Forest");
        scene.setDescription("A spooky forest at night");
        savedSceneId = sceneRepository.save(scene).getId();
    }

    @Test
    @DisplayName("getSceneById should return status 200 and sceneDTO")
    void getSceneById_ReturnsScene() throws Exception {
        mockMvc.perform(get("/scene/" + savedSceneId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sceneName").value("Dark Forest"))
                .andExpect(jsonPath("$.description").value("A spooky forest at night"));
    }

    @Test
    @DisplayName("getSceneById should return status 404 when scene not found")
    void getSceneById_ReturnsNotFound_WhenSceneDoesNotExist() throws Exception {
        mockMvc.perform(get("/scene/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("getAllScenes should return status 200 and list of scenes")
    void getAllScenes_ReturnsList() throws Exception {
        mockMvc.perform(get("/scene"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].sceneName").value("Dark Forest"));
    }

    @Test
    @DisplayName("create should return status 201 and created scene")
    void create_ReturnsCreatedScene() throws Exception {
        String sceneJson = """
                {
                    "sceneName": "Abandoned Hospital",
                    "description": "A creepy old hospital"
                }
                """;

        mockMvc.perform(post("/scene")
                        .contentType(APPLICATION_JSON)
                        .content(sceneJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sceneName").value("Abandoned Hospital"))
                .andExpect(jsonPath("$.description").value("A creepy old hospital"));
    }

    @Test
    @DisplayName("create should return status 409 when scene name already exists")
    void create_ReturnsConflict_WhenNameExists() throws Exception {
        String sceneJson = """
                {
                    "sceneName": "Dark Forest",
                    "description": "Another dark forest"
                }
                """;

        mockMvc.perform(post("/scene")
                        .contentType(APPLICATION_JSON)
                        .content(sceneJson))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("update should return status 200 and updated scene")
    void update_ReturnsUpdatedScene() throws Exception {
        String updateJson = """
                {
                    "sceneName": "Updated Forest",
                    "description": "A modified forest description"
                }
                """;

        mockMvc.perform(put("/scene/" + savedSceneId)
                        .contentType(APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sceneName").value("Updated Forest"))
                .andExpect(jsonPath("$.description").value("A modified forest description"));
    }

    @Test
    @DisplayName("update should return status 404 when scene not found")
    void update_ReturnsNotFound_WhenSceneDoesNotExist() throws Exception {
        String updateJson = """
                {
                    "sceneName": "Updated Scene",
                    "description": "Description"
                }
                """;

        mockMvc.perform(put("/scene/9999")
                        .contentType(APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("delete should return status 204 when scene deleted")
    void delete_ReturnsNoContent_WhenSceneDeleted() throws Exception {
        mockMvc.perform(delete("/scene/" + savedSceneId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("delete should return status 404 when scene not found")
    void delete_ReturnsNotFound_WhenSceneDoesNotExist() throws Exception {
        mockMvc.perform(delete("/scene/9999"))
                .andExpect(status().isNotFound());
    }
}
