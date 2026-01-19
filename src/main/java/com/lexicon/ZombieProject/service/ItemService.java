package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.dto.ItemDTO;
import com.lexicon.ZombieProject.exception.ResourceAlreadyExistsException;
import com.lexicon.ZombieProject.repository.ItemRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository repository;
    private final ItemMapper mapper;

    public ItemService(ItemRepository repository, ItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ItemDTO> getAllItems(){
        List<Item> items = repository.findAll();
        List<ItemDTO> itemDTOs = new ArrayList<>();

        for(Item i : items){
            itemDTOs.add(mapper.toItemDTO(i));
        }
        return itemDTOs;
    }

    public boolean existsByName(String name){ return repository.existsByName(name); }

    public ItemDTO getItemById(Long id){
        Item item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        return mapper.toItemDTO(item);
    }

    public ItemDTO create(ItemDTO itemDTO){
        if(repository.existsById(itemDTO.getId())){
            throw new ResourceAlreadyExistsException("Item " +
                    itemDTO.getName() +
                    " already exists");
        }
        return mapper.toItemDTO(repository.save(mapper.toItemEntity(itemDTO)));
    }

    public Optional<ItemDTO> update(Long id, ItemDTO dto){
        return repository.findById(id)
                .map(item -> {
                    item.setName(dto.getName());
                    item.setDescription(dto.getDescription());
                    item.setTransition(dto.getTransition());
                    item.setInventoryEntry(dto.getInventoryEntry());
                    item.setScene(dto.getScene());
                    Item updated = repository.save(item);
                    return mapper.toItemDTO(updated);
                });
    }

    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Scene not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
