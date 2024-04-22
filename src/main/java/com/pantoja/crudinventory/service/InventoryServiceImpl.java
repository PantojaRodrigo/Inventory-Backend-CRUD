package com.pantoja.crudinventory.service;

import com.pantoja.crudinventory.misc.ItemNotFoundException;
import com.pantoja.crudinventory.dao.InventoryDAO;
import com.pantoja.crudinventory.entity.Item;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class InventoryServiceImpl implements InventoryService{

    private InventoryDAO inventoryRepository;
    @Autowired
    public InventoryServiceImpl(InventoryDAO inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<Item> findAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public Item findById(int id) {
        return  inventoryRepository.findById(id).orElseThrow(()->new ItemNotFoundException(id));
    }

    @Override
    @Transactional
    public Item save(Item item) {
        return inventoryRepository.save(item);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Item theItem =  inventoryRepository.findById(id).orElseThrow(()->new ItemNotFoundException(id));
        inventoryRepository.deleteById(id);
    }
}
