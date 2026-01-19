package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.dto.ItemDTO;
import com.lexicon.ZombieProject.repository.ItemRepository;

public class ItemMapper {
    private final ItemRepository repository;

    public ItemMapper(ItemRepository repository) {
        this.repository = repository;
    }

    public ItemDTO toItemDTO(Item item){
        return  new ItemDTO(item.getId(), item.getName(), item.getDescription(), item.getTransition(), item.getInventoryEntry(), item.getScene());
    }

    public Item toItemEntity(ItemDTO itemDTO){
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setTransition(itemDTO.getTransition());
        item.setInventoryEntry(itemDTO.getInventoryEntry());
        item.setScene(itemDTO.getScene());

        return item;
    }
}
