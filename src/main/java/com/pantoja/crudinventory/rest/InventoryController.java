package com.pantoja.crudinventory.rest;

import com.pantoja.crudinventory.entity.Item;
import com.pantoja.crudinventory.service.InventoryService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class InventoryController {
    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService=inventoryService;
    }
    @GetMapping("/items")
    public Flux<Item> getItems(@RequestParam(required = false) String state){
        if(state!=null) return inventoryService.findAll(state);
        else return inventoryService.findAll();
    }

    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable("id") int id){
        return inventoryService.findById(id);
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public Item addItem(@Valid @RequestBody Item item){
        Item dbItem = inventoryService.save(item);
        return dbItem;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/items/{id}")
    public String deleteItem(@PathVariable int id){
        Item item = inventoryService.findById(id);
        inventoryService.deleteById(id);
        return "Deleted item id: "+ id;

    }


}
