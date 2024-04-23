package com.pantoja.crudinventory.rest;

import com.pantoja.crudinventory.entity.Item;
import com.pantoja.crudinventory.entity.Location;
import com.pantoja.crudinventory.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InventoryControllerTest {

    InventoryService inventoryServiceMock = mock(InventoryService.class);

    InventoryController inventoryController =  new InventoryController(inventoryServiceMock);

    Flux<Item> itemsFlux;
    @BeforeEach
    void setup(){
        List<Item> items = new ArrayList<>();
        Location loc1 = new Location("available","WMD1",12345);
        Location loc2 = new Location("damaged","WMD1",54321);
        Location loc3 = new Location("available","WMD1",98766);
        loc1.setLocationId(1);loc2.setLocationId(2);loc3.setLocationId(3);
        Item item1 =  new Item("AHOOK","A hook",loc1);
        Item item2 =  new Item("BASEBALL","A baseball",loc2);
        Item item3 =  new Item("CAN","A can",loc3);
        item1.setItemId(1);item2.setItemId(2);item3.setItemId(3);
        items.add(item1);items.add(item2);items.add(item3);
        itemsFlux = Flux.fromIterable(items);
    }
    @Test
    void getItems() {

        when(inventoryServiceMock.findAll()).thenReturn(itemsFlux);
        Flux<Item> actual = inventoryController.getItems(null);
        StepVerifier.create(actual)
                .expectNextCount(3)
                .verifyComplete();

    }

    @Test
    void getItems_withState() {
        List<Item> items = new ArrayList<>();
        Location loc1 = new Location("available","WMD1",12345);
        Location loc2 = new Location("damaged","WMD1",54321);
        Location loc3 = new Location("available","WMD1",98766);
        loc1.setLocationId(1);loc2.setLocationId(2);loc3.setLocationId(3);
        Item item1 =  new Item("AHOOK","A hook",loc1);
        Item item2 =  new Item("BASEBALL","A baseball",loc2);
        Item item3 =  new Item("CAN","A can",loc3);
        item1.setItemId(1);item2.setItemId(2);item3.setItemId(3);
        items.add(item1);items.add(item2);items.add(item3);
        Flux<Item> fluxItems = Flux.fromIterable(items);
        when(inventoryServiceMock.findAll("damaged")).thenReturn(Flux.just(item2));
        Flux<Item> actual = inventoryController.getItems("damaged");
        StepVerifier.create(actual)
                .expectNextMatches(item -> item.equals(item2))
                .verifyComplete();

    }

    @Test
    void getItemById() {
    }

    @Test
    void addItem() {
    }

    @Test
    void deleteItem() {
    }
}