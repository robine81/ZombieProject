package com.lexicon.ZombieProject.controller;

import com.lexicon.ZombieProject.entity.dto.ItemDTO;
import com.lexicon.ZombieProject.service.ItemService;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ItemDTO> create(@RequestBody ItemDTO itemDTO){
        if(service.existsByName(itemDTO.getName())){
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.status(201).body(service.create(itemDTO));
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAlItems(){
        List <ItemDTO> items = service.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity <ItemDTO> getItemById(@PathVariable Long id){
        ItemDTO itemDTO = service.getItemById(id);
        return ResponseEntity.ok(itemDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> update(@PathVariable Long id, @RequestBody ItemDTO itemDTO){
        return  service.update(id, itemDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@Min(value = 1, message = "Enter a non-zero value for id") @PathVariable Long id){
        service.delete(id);
    }
}
