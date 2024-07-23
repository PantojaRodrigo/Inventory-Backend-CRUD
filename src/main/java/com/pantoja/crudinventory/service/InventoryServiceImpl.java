package com.pantoja.crudinventory.service;

import com.pantoja.crudinventory.misc.ItemIdExistingException;
import com.pantoja.crudinventory.misc.ItemNotFoundException;
import com.pantoja.crudinventory.dao.InventoryRepository;
import com.pantoja.crudinventory.entity.Item;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Locale;

@Repository
public class InventoryServiceImpl implements InventoryService{

    private InventoryRepository inventoryRepository;
    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Flux<Item> findAll(String search) {
        Flux<Item> items = Flux.fromIterable(inventoryRepository.findAll());
        String searchLow = search.toLowerCase();
        return items.filter(i -> ((i.getItemName() != null && i.getItemName().toLowerCase().contains(searchLow)) ||
                                  (i.getDescription() != null && i.getDescription().toLowerCase().contains(searchLow)) ||
                                  (i.getLocation() != null && i.getLocation().getState() != null && i.getLocation().getState().toLowerCase().contains(searchLow))
        ));
    }

    @Override
    public Flux<Item> findAll() {
        return Flux.fromIterable(inventoryRepository.findAll());
    }

    @Override
    public Item findById(int id) {
        return  inventoryRepository.findById(id).orElseThrow(()->new ItemNotFoundException(id));
    }

    @Override
    @Transactional
    public Item save(Item item) {
        //Return DTO
        if(inventoryRepository.existsById(item.getItemId())) throw  new ItemIdExistingException(item.getItemId());
        //DAO to DTO
        return inventoryRepository.save(item);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        if(!inventoryRepository.existsById(id)) throw  new ItemNotFoundException(id);
        inventoryRepository.deleteById(id);
    }
}
