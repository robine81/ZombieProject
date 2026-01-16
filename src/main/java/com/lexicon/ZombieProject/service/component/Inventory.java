package com.lexicon.ZombieProject.service.component;

import com.lexicon.ZombieProject.entity.InventoryEntry;
import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Inventory {
    @Autowired
    private InventoryRepository repository;

    private List<InventoryEntry> inventoryEntries;

    public List<InventoryEntry> getInventoryEntries(){
        if (inventoryEntries == null) {
            inventoryEntries = repository.findAll();
        }
        return inventoryEntries;
    }

    public boolean hasItem(Item item){
        return !getInventoryEntries().stream()
                .filter((inventoryEntry -> item.equals(inventoryEntry.getItem())
                        && inventoryEntry.getAmount() > 0))
                .toList().isEmpty();
    }
}
