package com.pantoja.crudinventory.rest;

import com.pantoja.crudinventory.entity.Item;
import com.pantoja.crudinventory.entity.Location;
import com.pantoja.crudinventory.misc.ItemNotFoundException;
import com.pantoja.crudinventory.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MethodArgumentNotValidException;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

        when(inventoryServiceMock.findAll("damaged")).thenReturn(itemsFlux.elementAt(1).flux());
        Flux<Item> actual = inventoryController.getItems("damaged");
        StepVerifier.create(actual)
                .expectNextMatches(item -> item.equals(itemsFlux.elementAt(1).block()))
                .verifyComplete();

    }

    @Test
    void getItemById_existingItem() {
        when(inventoryServiceMock.findById(1)).thenReturn(itemsFlux.elementAt(1).block());
        Item actual = inventoryController.getItemById(1);
        assertEquals(itemsFlux.elementAt(1).block(),actual);
    }

    @Test
    void getItemById_noExistingItem() {
        when(inventoryServiceMock.findById(5)).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class,()->inventoryController.getItemById(5));

    }

    @Test
    void addItem_goodAttributes() {
        Location loc = new Location("available","WMD1",12345);
        Item item = new Item("REMO","A remo",loc);
        Item item2 = item;
        item2.setItemId(4);
        item2.getLocation().setLocationId(4);
        when(inventoryServiceMock.save(item)).thenReturn(item2);
        Item actual = inventoryController.addItem(item);
        assertEquals(item2,actual);
    }

    /*@Test
    void addItem_badAttributes() {
        Location loc = new Location("null","WMD1",12345);
        Item item = new Item("null","A remo",loc);
        when(inventoryServiceMock.save(any(Item.class))).thenThrow(new MethodArgumentNotValidException());
        assertThrows(MethodArgumentNotValidException.class,()->inventoryController.addItem(item));
    }*/

    @Test
    void deleteItem() {
        when(inventoryServiceMock.findById(1)).thenReturn(itemsFlux.elementAt(1).block());
        String actual = inventoryController.deleteItem(1);
        assertEquals("Deleted item id: 1",actual);
    }
}