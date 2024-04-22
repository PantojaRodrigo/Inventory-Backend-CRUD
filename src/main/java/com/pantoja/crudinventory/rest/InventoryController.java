package com.pantoja.crudinventory.rest;

import com.pantoja.crudinventory.entity.Item;
import com.pantoja.crudinventory.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InventoryController {
    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService=inventoryService;
    }
    @GetMapping("/items")
    public List<Item> getItems(){
        return inventoryService.findAll();
    }

    @GetMapping("/items/{id}")
    public Item getEmployee(@PathVariable int id){

        Item item = inventoryService.findById(id);
        if(item == null){
            throw new RuntimeException("Item not found: "+id);
        }
        return  item;
    }

    @PostMapping("/items")
    public Item addEmployee(@RequestBody Item item){

        item.setItemId(0);
        Item dbItem = inventoryService.save(item);
        return dbItem;
    }

    @DeleteMapping("/items/{id}")
    public String deleteEmployee(@PathVariable int id){
        Item item = inventoryService.findById(id);
        if(item == null){
            throw new RuntimeException("Item not found: "+id);
        }
        inventoryService.deleteById(id);
        return "Deleted item id: "+ id;

    }
}
