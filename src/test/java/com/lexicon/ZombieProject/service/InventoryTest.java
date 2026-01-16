package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.InventoryEntry;
import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.repository.InventoryRepository;
import com.lexicon.ZombieProject.service.component.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("hasItem returns false for present item with amount > 0")
    void hasItemAbsent(){
        boolean result = inventory.hasItem(item3);
        assertFalse(result);
    }
}
