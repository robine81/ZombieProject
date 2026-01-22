package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.dto.ItemDTO;
import com.lexicon.ZombieProject.repository.InventoryRepository;
import com.lexicon.ZombieProject.repository.ItemRepository;
import com.lexicon.ZombieProject.repository.SceneRepository;
import com.lexicon.ZombieProject.repository.TransitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    private final ItemRepository repository;

    public ItemMapper(ItemRepository repository) {
        this.repository = repository;
    }

    public ItemDTO toItemDTO(Item item){
        return  new ItemDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getTransition() != null ? item.getTransition().getId() : null,
                item.getInventoryEntry() != null ? item.getInventoryEntry().getId() : null,
                item.getScene() != null ? item.getScene().getId() : null,
                item.getScene() != null ? item.getScene().getSceneName() : null
        );
    }

    public Item toItemEntity(ItemDTO itemDTO){
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());

        return item;
    }
}
