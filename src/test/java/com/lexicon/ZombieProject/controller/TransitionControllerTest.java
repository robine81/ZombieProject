package com.lexicon.ZombieProject.controller;

import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.dto.TransitionDTO;
import com.lexicon.ZombieProject.service.TransitionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TransitionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransitionService transitionService;

    private TransitionDTO transitionDTO;
    private TransitionDTO transitionDTO2;
    private Scene scene1;
    private Scene scene2;

    @BeforeEach
    void setUp () {
        Item item1 = new Item();
        item1.setName("Sword");

        scene1 = new Scene();
        scene1.setId(1L);
        scene1.setSceneName("StartScene");
        scene1.setDescription("You find yourself at the start");

        scene2 = new Scene();
        scene2.setId(2L);
        scene2.setSceneName("EndScene");
        scene2.setDescription("You find yourself at the end");

        transitionDTO = new TransitionDTO.Builder()
                .id(1L)
                .originScene(scene1)
                .targetScene(scene2)
                .sceneDescription("You are in a lush garden")
                .choiceDescription("Enter the garden")
                .requiredItems(List.of(item1))
                .consumesRequiredItems(false)
                .enabledTransitions(List.of())
                .enabledBy(List.of())
                .disabledTransitions(List.of())
                .disabledBy(List.of())
                .isEnabled(true)
                .name("garden-transition")
                .build();

        transitionDTO2 = new TransitionDTO.Builder()
                .id(2L)
                .originScene(scene2)
                .targetScene(scene1)
                .sceneDescription("A dark corridor")
                .choiceDescription("Enter the corridor")
                .isEnabled(true)
                .name("corridor-transition")
                .build();
    }

    @Test
    @DisplayName("getAllTransitions should return list of Transitions")
    void getAllTransitions () throws Exception {
        List<TransitionDTO> transitions = Arrays.asList(transitionDTO, transitionDTO2);
        when(transitionService.getAllTransitions()).thenReturn(transitions);

        mockMvc.perform(get("/transitions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("garden-transition"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("corridor-transition"));

        verify(transitionService).getAllTransitions();
    }

    @Test
    @DisplayName("getTransitionById should return Transition when Transition by ID exists")
    void getTransitionById () throws Exception {
        when(transitionService.getTransitionById(1L)).thenReturn(transitionDTO);

        mockMvc.perform(get("/transitions/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("garden-transition"))
                .andExpect(jsonPath("$.sceneDescription").value("You are in a lush garden"))
                .andExpect(jsonPath("$.choiceDescription").value("Enter the garden"))
                .andExpect(jsonPath("$.enabled").value((true)));

        verify(transitionService).getTransitionById(1L);
    }

    @Test
    void debugTransitionDTO() throws Exception {
        System.out.println("getName(): " + transitionDTO.getName());
        System.out.println("JSON: " + objectMapper.writeValueAsString(transitionDTO));
    }

    @Test
    void createTransition () throws Exception {
        when(transitionService.existsByName("garden-transition")).thenReturn(false);
        when(transitionService.createTransition(ArgumentMatchers.<TransitionDTO>any())).thenReturn(transitionDTO);

        mockMvc.perform(post("/transitions/create")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transitionDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("garden-transition"));

        verify(transitionService).existsByName("garden-transition");
        verify(transitionService).createTransition(ArgumentMatchers.<TransitionDTO>any());
    }

    @Test
    void updateTransition () throws Exception {
        TransitionDTO updatedDTO = new TransitionDTO.Builder()
                .id(1L)
                .originScene(scene1)
                .targetScene(scene2)
                .sceneDescription("Updated garden description")
                .choiceDescription("Updated choice")
                .isEnabled(false)
                .name("updated-garden-transition")
                .build();

        when(transitionService.updateTransition(eq(1L), ArgumentMatchers.<TransitionDTO>any())).thenReturn(updatedDTO);

        mockMvc.perform(put("/transitions/1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("updated-garden-transition"))
                .andExpect(jsonPath("$.sceneDescription").value("Updated garden description"))
                .andExpect(jsonPath("$.enabled").value(false));

        verify(transitionService).updateTransition(eq(1L), ArgumentMatchers.<TransitionDTO>any());
    }

    @Test
    void deleteTransition () throws Exception {
        doNothing().when(transitionService).deleteTransition(1L);

        mockMvc.perform(delete("/transitions/1"))
                .andExpect(status().isOk());

        verify(transitionService).deleteTransition(1L);
    }
}