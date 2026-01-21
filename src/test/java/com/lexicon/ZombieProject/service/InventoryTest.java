package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.InventoryEntry;
import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.repository.InventoryRepository;
import com.lexicon.ZombieProject.service.component.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventoryTest {

    @Mock
    InventoryRepository repository;

    @InjectMocks
    Inventory inventory;

    Item item1;
    Item item2;
    Item item3;

    @BeforeEach
    void setup(){
        List<InventoryEntry> entries = new ArrayList<>();
        item1 = new Item();
        InventoryEntry ie1 = new InventoryEntry(item1, 1);
        entries.add(ie1);
        item2 = new Item();
        InventoryEntry ie2 = new InventoryEntry(item2, 0);
        entries.add(ie2);
        item3 = new Item();

        when(repository.findAll()).thenReturn(entries);
    }

    @Test
    @DisplayName("getInventoryEntries returns all entries")
    void getInventoryEntries(){
        List<InventoryEntry> entries = inventory.getInventoryEntries();

        assertEquals(2, entries.size());
    }

    @Test
    @DisplayName("hasItem returns true for present item with amount > 0")
    void hasItemPresentPositive(){
        boolean result = inventory.hasItem(item1);
        assertTrue(result);
    }

    @Test
    @DisplayName("hasItem returns false for present item with amount == 0")
    void hasItemPresentZero(){
        boolean result = inventory.hasItem(item2);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasItem returns false for absent item")
    void hasItemAbsent(){
        boolean result = inventory.hasItem(item3);
        assertFalse(result);
    }

    @Test
    @DisplayName("addItem increments existing entry with amount > 0")
    void addItemIncrementsPositive(){
        inventory.addItem(item1);
        assertEquals(2, inventory.getAmountInInventory(item1));
    }

    @Test
    @DisplayName("addItem increments existing entry with amount == 0")
    void addItemIncrementsZero(){
        inventory.addItem(item2);
        assertEquals(1, inventory.getAmountInInventory(item2));
    }

    @Test
    @DisplayName("addItem creates an entry for absent item")
    void addItemInsertsAbsent(){
        InventoryEntry ie3 = new InventoryEntry(item3, 1);
        when(repository.save(any())).thenReturn(ie3);

        inventory.addItem(item3);

        assertTrue(inventory.hasItem(item3));
        assertEquals(1, inventory.getAmountInInventory(item3));
    }

    @Test
    @DisplayName("addItem increments item formerly absent after being called twice")
    void addItemInsertsAbsentTwice(){
        InventoryEntry ie3 = new InventoryEntry(item3, 1);
        when(repository.save(any())).thenReturn(ie3);

        inventory.addItem(item3);
        inventory.addItem(item3)
        ;
        assertTrue(inventory.hasItem(item3));
        assertEquals(2, inventory.getAmountInInventory(item3));
    }

    @Test
    @DisplayName("consumeItem decrements existing entry with amount > 0")
    void consumeItemDecrementsPositive(){
        inventory.consumeItem(item1);
        assertEquals(0, inventory.getAmountInInventory(item1));
    }

    @Test
    @DisplayName("consumeItem doesn't decrement existing entry with amount == 0")
    void consumeItemDoesNotDecrementZero(){
        inventory.consumeItem(item2);
        assertEquals(0, inventory.getAmountInInventory(item2));
    }

    @Test
    @DisplayName("consumeItem doesn't add an absent item")
    void consumeItemDoesNotAddAbsent(){
        inventory.consumeItem(item3);
        assertFalse(inventory.hasItem(item3));
        assertTrue(inventory.getInventoryEntries().stream()
                .filter(inventoryEntry -> inventoryEntry.getItem().equals(item3))
                .toList()
                .isEmpty());
    }
}
