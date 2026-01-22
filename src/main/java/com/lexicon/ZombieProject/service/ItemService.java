package com.lexicon.ZombieProject.service;

import com.lexicon.ZombieProject.entity.InventoryEntry;
import com.lexicon.ZombieProject.entity.Item;
import com.lexicon.ZombieProject.entity.Scene;
import com.lexicon.ZombieProject.entity.Transition;
import com.lexicon.ZombieProject.entity.dto.ItemDTO;
import com.lexicon.ZombieProject.exception.ResourceAlreadyExistsException;
import com.lexicon.ZombieProject.exception.ResourceNotFoundException;
import com.lexicon.ZombieProject.repository.InventoryRepository;
import com.lexicon.ZombieProject.repository.ItemRepository;
import com.lexicon.ZombieProject.repository.SceneRepository;
import com.lexicon.ZombieProject.repository.TransitionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper mapper;
    private final TransitionRepository transitionRepository;
    private final InventoryRepository inventoryRepository;
    private final SceneRepository sceneRepository;

    public ItemService(ItemRepository itemRepository, ItemMapper mapper, TransitionRepository transitionRepository, InventoryRepository inventoryRepository, SceneRepository sceneRepository) {
        this.itemRepository = itemRepository;
        this.mapper = mapper;
        this.transitionRepository = transitionRepository;
        this.inventoryRepository = inventoryRepository;
        this.sceneRepository = sceneRepository;
    }

    public List<ItemDTO> getAllItems(){
        List<Item> items = itemRepository.findAll();
        List<ItemDTO> itemDTOs = new ArrayList<>();

        for(Item i : items){
            itemDTOs.add(mapper.toItemDTO(i));
        }
        return itemDTOs;
    }

    public boolean existsByName(String name){ return itemRepository.existsByName(name); }

    public ItemDTO getItemById(Long id){
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
        return mapper.toItemDTO(item);
    }

    public ItemDTO create(ItemDTO itemDTO){
        if(itemRepository.existsByName(itemDTO.getName())){
            throw new ResourceAlreadyExistsException("Item " +
                    itemDTO.getName() +
                    " already exists");
        }

        Item item = mapper.toItemEntity(itemDTO);

        if(itemDTO.getSceneId() != null) {
            Scene scene = sceneRepository.findById(itemDTO.getSceneId())
                    .orElseThrow(() -> new ResourceNotFoundException("Scene not found with id: " + itemDTO.getSceneId()));
            item.setScene(scene);
        }

        if(itemDTO.getTransitionId() != null) {
            Transition transition =transitionRepository.findById(itemDTO.getTransitionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Transition not found with id: " + itemDTO.getTransitionId()));
            item.setTransition(transition);
        }

        if(itemDTO.getInventoryEntryId() != null) {
            InventoryEntry inventoryEntry = inventoryRepository.findById(itemDTO.getInventoryEntryId())
                    .orElseThrow(() -> new ResourceNotFoundException("InventoryEntry not found with id: " + itemDTO.getInventoryEntryId()));
            item.setInventoryEntry(inventoryEntry);
        }
        Item saved = itemRepository.save(item);
        return mapper.toItemDTO(saved);
    }

    public Optional<ItemDTO> update(Long id, ItemDTO dto){
        return itemRepository.findById(id)
                .map(item -> {
                    item.setName(dto.getName());
                    item.setDescription(dto.getDescription());

                    if(dto.getTransitionId() != null) {
                        Transition transition = transitionRepository.findById(dto.getTransitionId())
                                .orElse(null);
                        item.setTransition(transition);
                    } else {
                        item.setTransition(null);
                    }

                    if(dto.getInventoryEntryId() != null) {
                        InventoryEntry inventoryEntry = inventoryRepository.findById(dto.getInventoryEntryId())
                                .orElse(null);
                        item.setInventoryEntry(inventoryEntry);
                    } else {
                        item.setInventoryEntry(null);
                    }

                    if(dto.getSceneId() != null) {
                        Scene scene = sceneRepository.findById(dto.getSceneId())
                                .orElse(null);
                        item.setScene(scene);
                    } else {
                        item.setScene(null);
                    }

                    Item updated = itemRepository.save(item);
                    return mapper.toItemDTO(updated);
                });
    }

    public void delete(Long id){
        if(!itemRepository.existsById(id)){
            throw new ResourceNotFoundException("Scene not found with id: " + id);
        }
        itemRepository.deleteById(id);
    }
}
