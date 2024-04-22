package com.pantoja.crudinventory.service;

import com.pantoja.crudinventory.entity.Item;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    List<Item> findAll();
    Item findById(int id);
    Item save(Item item);
    void deleteById(int id);
}