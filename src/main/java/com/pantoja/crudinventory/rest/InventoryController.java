package com.pantoja.crudinventory.rest;

import com.pantoja.crudinventory.entity.Item;
import com.pantoja.crudinventory.service.InventoryService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/items")
@CrossOrigin(origins = "http://localhost:3000")
public class InventoryController {
    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService=inventoryService;
    }
    @GetMapping
    public Flux<Item> getItems(@RequestParam(required = false) String search){
        if(search!=null) return inventoryService.findAll(search);
        else return inventoryService.findAll();
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable("id") int id){
        return inventoryService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item addItem(@Valid @RequestBody Item item){
        return inventoryService.save(item);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Item update(@PathVariable("id") int id,@Valid @RequestBody Item item){
        Item oldItem = inventoryService.findById(id);

        return inventoryService.update(id,item);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable int id){
        Item item = inventoryService.findById(id);
        inventoryService.deleteById(id);
        return "Deleted item id: "+ id;

    }
}
