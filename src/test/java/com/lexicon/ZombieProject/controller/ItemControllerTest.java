package com.lexicon.ZombieProject.controller;

import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.repository.ItemRepository;
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
class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    SceneRepository sceneRepository;

    Long savedItemId;
    Long savedSceneId;

    @BeforeEach
    void setup() {
        itemRepository.deleteAll();
        sceneRepository.deleteAll();

        Scene scene = new Scene();
        scene.setSceneName("Dark Room");
        scene.setDescription("A dimly lit room");
        savedSceneId = sceneRepository.save(scene).getId();

        Item item = new Item();
        item.setName("Flashlight");
        item.setDescription("A battery-powered flashlight");
        item.setScene(scene);

        savedItemId = itemRepository.save(item).getId();
    }

    @Test
    @DisplayName("getItemById should return status 200 and itemDTO")
    void getItemById_ReturnsItem() throws Exception {
        mockMvc.perform(get("/item/" + savedItemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Flashlight"))
                .andExpect(jsonPath("$.description").value("A battery-powered flashlight"))
                .andExpect(jsonPath("$.sceneId").value(savedSceneId))
                .andExpect(jsonPath("$.sceneName").value("Dark Room"));
    }

    @Test
    @DisplayName("getItemById should return status 404 when item not found")
    void getItemById_ReturnsNotFound_WhenItemDoesNotExist() throws Exception {
        mockMvc.perform(get("/item/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("getAllItems should return status 200 and list of items")
    void getAllItems_ReturnsList() throws Exception {
        mockMvc.perform(get("/item"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Flashlight"));
    }

    @Test
    @DisplayName("create should return status 201 and created item")
    void create_ReturnsCreatedItem() throws Exception {
        String itemJson = """
                {
                    "name": "Key",
                    "description": "A rusty old key",
                    "sceneId": %d
                }
                """.formatted(savedSceneId);

        mockMvc.perform(post("/item")
                        .contentType(APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Key"))
                .andExpect(jsonPath("$.description").value("A rusty old key"));
    }

    @Test
    @DisplayName("create should return status 409 when item name already exists")
    void create_ReturnsConflict_WhenNameExists() throws Exception {
        String itemJson = """
                {
                    "name": "Flashlight",
                    "description": "Another flashlight",
                    "sceneId": %d
                }
                """.formatted(savedSceneId);

        mockMvc.perform(post("/item")
                        .contentType(APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("update should return status 200 and updated item")
    void update_ReturnsUpdatedItem() throws Exception {
        String updateJson = """
                {
                    "name": "Updated Flashlight",
                    "description": "A modified flashlight",
                    "sceneId": %d
                }
                """.formatted(savedSceneId);

        mockMvc.perform(put("/item/" + savedItemId)
                        .contentType(APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Flashlight"))
                .andExpect(jsonPath("$.description").value("A modified flashlight"));
    }

    @Test
    @DisplayName("update should return status 404 when item not found")
    void update_ReturnsNotFound_WhenItemDoesNotExist() throws Exception {
        String updateJson = """
                {
                    "name": "Updated Item",
                    "description": "Description"
                }
                """;

        mockMvc.perform(put("/item/9999")
                        .contentType(APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("delete should return status 204 when item deleted")
    void delete_ReturnsNoContent_WhenItemDeleted() throws Exception {
        mockMvc.perform(delete("/item/" + savedItemId))
                .andExpect(status().isNoContent());
    }
}
