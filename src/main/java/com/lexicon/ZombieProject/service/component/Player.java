package com.lexicon.ZombieProject.service.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Player {
    @Autowired
    private Inventory inventory;

    public Inventory getInventory() {
        return inventory;
    }

    public void reset() { inventory.clear(); }
}
