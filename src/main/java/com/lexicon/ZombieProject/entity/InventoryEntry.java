package com.lexicon.ZombieProject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class InventoryEntry {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer amount;

    public InventoryEntry() {
    }

    public InventoryEntry(Item item, Integer amount) {
        this.item = item;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
