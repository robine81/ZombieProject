package com.lexicon.ZombieProject.repository;

import com.lexicon.ZombieProject.entity.InventoryEntry;
import com.lexicon.ZombieProject.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryEntry, Long> {
}
