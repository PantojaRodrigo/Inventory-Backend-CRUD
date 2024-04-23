package com.pantoja.crudinventory.service;

import com.pantoja.crudinventory.entity.Item;
import reactor.core.publisher.Flux;

import java.util.Optional;

public interface InventoryService {
    Flux<Item> findAll(String state);
    Flux<Item> findAll();
    Item findById(int id);
    Item save(Item item);
    void deleteById(int id);
}