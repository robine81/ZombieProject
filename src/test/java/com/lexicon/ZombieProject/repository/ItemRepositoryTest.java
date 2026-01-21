package com.lexicon.ZombieProject.repository;

import com.lexicon.ZombieProject.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

//use application-test.properties
@ActiveProfiles("test")
@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository repository;

    @BeforeEach
    void setup(){
        Item item1 = new Item();
        item1.setName("Hammer");
        item1.setDescription("A very heavy hammer");

        Item item2 = new Item();
        item2.setName("Knife");
        item2.setDescription("A sharp knife");

        Item item3 = new Item();
        item3.setName("Gun");
        item3.setDescription("A loaded gun");

        repository.save(item1);
        repository.save(item2);
        repository.save(item3);
    }

    @Test
    @DisplayName("findByName should return item when name exists")
    void findByName_ShouldReturnNameOfItem(){
        //arrange

        //act
        Optional<Item> result = repository.findByName("Hammer");

        //assert
        assertTrue(result.isPresent());
        assertEquals("Hammer", result.get().getName());
        assertEquals("A very heavy hammer", result.get().getDescription());
    }

    @Test
    @DisplayName("findByName should return empty Optional when name does not exist")
    void findByName_ShouldReturnEmpty_WhenNameDoesNotExist(){
        //arrange

        //act
        Optional<Item> result = repository.findByName("NonExistent");

        //assert
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("existsByName Should return true if name exists")
    void existsByName_ShouldReturnTrueIfNameExists(){
        //arrange

        //act
        boolean result = repository.existsByName("Hammer");

        //assert
        assertTrue(result);
    }

    @Test
    @DisplayName("existsByName Should return false if name does not exists")
    void existsByName_ShouldReturnFalseIfNameDoesNotExist(){
        //arrange

        //act
        boolean result = repository.existsByName("NonExistent");

        //assert
        assertFalse(result);
    }
}
