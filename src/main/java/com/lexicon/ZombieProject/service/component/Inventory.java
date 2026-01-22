package com.lexicon.ZombieProject.service.component;

import com.lexicon.ZombieProject.entity.InventoryEntry;
import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
        Optional<InventoryEntry> inventoryEntry = findEntryForItem(item);
        return inventoryEntry.isPresent() && inventoryEntry.get().getAmount() > 0;
    }

    public Integer getAmountInInventory(Item item){
        Optional<InventoryEntry> inventoryEntry = findEntryForItem(item);
        if (inventoryEntry.isPresent()){
            return inventoryEntry.get().getAmount();
        }
        return 0;
    }

    public void addItem(Item item){
        Optional<InventoryEntry> inventoryEntry = findEntryForItem(item);
        if(inventoryEntry.isPresent()){
            inventoryEntry.get().incrementAmount();
        } else {
            InventoryEntry newEntry = new InventoryEntry(item, 1);
            repository.save(newEntry);
            inventoryEntries.add(newEntry);
        }
    }

    public void consumeItem(Item item){
        Optional<InventoryEntry> inventoryEntry = findEntryForItem(item);
        if(inventoryEntry.isPresent() && inventoryEntry.get().getAmount() > 0) {
            inventoryEntry.get().decrementAmount();
        }
    }

    private Optional<InventoryEntry> findEntryForItem(Item item){
        return getInventoryEntries().stream()
                .filter(entry -> item.getId().equals(entry.getItem().getId()))
                .findFirst();
    }
}
