package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.dto.ItemDTO;
import com.lexicon.ZombieProject.repository.ItemRepository;
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
public class ItemServiceTest {
    @Mock
    private ItemRepository repository;

    @Mock
    private ItemMapper mapper;

    @InjectMocks
    private ItemService service;

    private Item testItem;
    private ItemDTO testItemDTO;
    private Scene testScene;

    @BeforeEach
    void setup() {
        testScene = new Scene();
        testScene.setId(1L);
        testScene.setSceneName("TestScene");

        testItem = new Item();
        testItem.setName("Flashlight");
        testItem.setDescription("A battery-powered flashlight");
        testItem.setScene(testScene);

        testItemDTO = new ItemDTO(1L,
                "Flashlight",
                "A battery-powered flashlight",
                null,
                null,
                testScene
        );
    }

    @Test
    @DisplayName("Should return created Item DTO")
    void create_ShouldReturnCreatedItemDTO(){
        //Arrange
        when(mapper.toItemEntity(testItemDTO)).thenReturn(testItem);
        when(repository.save(any(Item.class))).thenReturn(testItem);
        when(mapper.toItemDTO(testItem)).thenReturn(testItemDTO);

        //Act
        ItemDTO result = service.create(testItemDTO);

        //Assert
        assertNotNull(result);
        assertEquals("Flashlight", result.getName());
        verify(repository, times(1)).save(any(Item.class));
    }

    @Test
    @DisplayName("Should return list of itemDTOs as properly formatted")
    void getAllItems_ShouldReturnListOfItemDTOs(){
        //Arrange
        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("Key");
        item2.setDescription("Rusty key");

        ItemDTO itemDTO2 = new ItemDTO(2L, "Key", "Rusty key", null, null, null);

        List<Item> items = Arrays.asList(testItem, item2);
        when(repository.findAll()).thenReturn(items);
        when(mapper.toItemDTO(testItem)).thenReturn(testItemDTO);
        when(mapper.toItemDTO(item2)).thenReturn(itemDTO2);

        //Act
        List<ItemDTO> result = service.getAllItems();

        //Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Flashlight", result.get(0).getName());
        assertEquals("Key", result.get(1).getName());
        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).toItemDTO(any(Item.class));
    }

    @Test
    @DisplayName("Should return updated itemDTO when item exists")
    void update_ShouldReturnUpdatedItemDTO_WhenItemExists() {
        //Arrange
        ItemDTO updateDTO = new ItemDTO(null, "Updated Flashlight", "New description", null, null, testScene);
        Item updatedItem = new Item();
        updatedItem.setId(1L);
        updatedItem.setName("Updated Flashlight");
        updatedItem.setDescription("New description");

        when(repository.findById(1L)).thenReturn(Optional.of(testItem));
        when(repository.save(any(Item.class))).thenReturn(updatedItem);
        when(mapper.toItemDTO(updatedItem)).thenReturn(
                new ItemDTO(1L, "Updated Flashlight", "New description", null, null, testScene)
        );

        //Act
        Optional<ItemDTO> result = service.update(1L, updateDTO);

        //Assert
        assertTrue(result.isPresent());
        assertEquals("Updated Flashlight", result.get().getName());
    }

    @Test
    @DisplayName("Should return empty list when no items exists")
    void getAlllScenes_returnsEmptyList_WhenNoScenesExists() {
        //Arrange
        when(repository.findAll()).thenReturn(new ArrayList<>());

        //Act
        List<ItemDTO> result = service.getAllItems();

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
        verify(mapper, never()).toItemDTO(any(Item.class));
    }

    @Test
    @DisplayName("Returns ItemDTO when item exists")
    void getItem_ReturnsItemDTO_WhenItemExists() {
        //Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(testItem));
        when(mapper.toItemDTO(testItem)).thenReturn(testItemDTO);

        //Act
        ItemDTO result = service.getItemById(1L);

        //Assert
        assertNotNull(result);
        assertEquals("Flashlight", result.getName());
        assertEquals("A battery-powered flashlight", result.getDescription());
        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).toItemDTO(testItem);
    }

    @Test
    @DisplayName("getItem throws RuntimeException when item not found")
    void getItem_ThrowsRuntimeException_WhenItemNotFound() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.getItemById(999L));

        assertTrue(exception.getMessage().contains("Item not found with id: 999"));
        verify(repository, times(1)).findById(999L);
        verify(mapper, never()).toItemDTO(any(Item.class));
    }

    @Test
    @DisplayName("updateItem successfully updates existing item")
    void updateItem_UpdatesItem_WhenItemExists() {
        // Arrange
        ItemDTO updateDTO = new ItemDTO(null, "Updated Flashlight", "New description", null, null, testScene);

        Item updatedItem = new Item();
        updatedItem.setId(1L);
        updatedItem.setName("Updated Flashlight");
        updatedItem.setDescription("New description");
        updatedItem.setScene(testScene);

        ItemDTO updatedDTO = new ItemDTO(1L, "Updated Flashlight", "New description", null, null, testScene);

        when(repository.findById(1L)).thenReturn(Optional.of(testItem));
        when(repository.save(any(Item.class))).thenReturn(updatedItem);
        when(mapper.toItemDTO(updatedItem)).thenReturn(updatedDTO);

        // Act
        Optional<ItemDTO> result = service.update(1L, updateDTO);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Updated Flashlight", result.get().getName());
        assertEquals("New description", result.get().getDescription());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Item.class));
        verify(mapper, times(1)).toItemDTO(updatedItem);
    }

    @Test
    @DisplayName("updateItem returns empty Optional when item not found")
    void updateItem_ReturnsEmpty_WhenItemNotFound() {
        // Arrange
        ItemDTO updateDTO = new ItemDTO(null, "Updated", "Description", null, null, null);
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<ItemDTO> result = service.update(999L, updateDTO);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(999L);
        verify(repository, never()).save(any(Item.class));
        verify(mapper, never()).toItemDTO(any(Item.class));
    }

    @Test
    @DisplayName("deleteItem successfully deletes existing item")
    void deleteItem_DeletesItem_WhenItemExists() {
        // Arrange
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        // Act
        service.delete(1L);

        // Assert
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteItem throws RuntimeException when item not found")
    void deleteItem_ThrowsRuntimeException_WhenItemNotFound() {
        // Arrange
        when(repository.existsById(999L)).thenReturn(false);

        // Act
        assertThrows(RuntimeException.class, () -> service.delete(999L));

        verify(repository, times(1)).existsById(999L);
        verify(repository, never()).deleteById(anyLong());
    }
}
