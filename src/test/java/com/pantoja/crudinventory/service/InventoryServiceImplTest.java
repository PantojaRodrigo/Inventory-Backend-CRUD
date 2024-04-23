package com.pantoja.crudinventory.service;

import com.pantoja.crudinventory.dao.InventoryRepository;
import com.pantoja.crudinventory.entity.Item;
import com.pantoja.crudinventory.entity.Location;
import com.pantoja.crudinventory.misc.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InventoryServiceImplTest {

    private InventoryRepository inventoryRepositoryMock = mock(InventoryRepository.class);

    private InventoryService inventoryService = new InventoryServiceImpl(inventoryRepositoryMock);

    List<Item> items;
    @BeforeEach
    void setup(){
        items = new ArrayList<>();
        Location loc1 = new Location("available","WMD1",12345);
        Location loc2 = new Location("damaged","WMD1",54321);
        Location loc3 = new Location("available","WMD1",98766);
        loc1.setLocationId(1);loc2.setLocationId(2);loc3.setLocationId(3);
        Item item1 =  new Item("AHOOK","A hook",loc1);
        Item item2 =  new Item("BASEBALL","A baseball",loc2);
        Item item3 =  new Item("CAN","A can",loc3);
        item1.setItemId(1);item2.setItemId(2);item3.setItemId(3);
        items.add(item1);items.add(item2);items.add(item3);
    }

    @Test
    void findAll() {
        when(inventoryRepositoryMock.findAll()).thenReturn(items);
        Flux<Item> actual = inventoryService.findAll();
        StepVerifier.create(actual)
                .expectNextSequence(items)
                .verifyComplete();
    }
    @Test
    void findAll_withState_damaged() {
        when(inventoryRepositoryMock.findAll()).thenReturn(items);
        Flux<Item> actual = inventoryService.findAll("damaged");
        StepVerifier.create(actual)
                .expectNext(items.get(1))
                .verifyComplete();
    }
    @Test
    void findAll_withState_available() {
        when(inventoryRepositoryMock.findAll()).thenReturn(items);
        Flux<Item> actual = inventoryService.findAll("available");
        StepVerifier.create(actual)
                .expectNext(items.get(0))
                .expectNext(items.get(2))
                .verifyComplete();
    }

    @Test
    void findById() {
        when(inventoryRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(items.get(1)));
        Item actual = inventoryService.findById(1);
        assertEquals(items.get(1),actual);
    }

    @Test
    void findById_notFound() {
        when(inventoryRepositoryMock.findById(99)).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class,()->inventoryService.findById(99)) ;

    }

    @Test
    void save() {
        Location loc = new Location("available","WMD1",12345);
        Item item = new Item("REMO","A remo",loc);
        Item item2 = item;
        item2.setItemId(1);
        item2.getLocation().setLocationId(1);
        when(inventoryRepositoryMock.save(item)).thenReturn(item2);
        Item actual = inventoryService.save(item);
        assertEquals(item2,actual);
    }
    //TODO:Unit Test unhappy path



    @Test
    void deleteById() {
        when(inventoryRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(items.get(1)));
        inventoryService.deleteById(1);
    }

    @Test
    void deleteById_notFound() {
        when(inventoryRepositoryMock.findById(99)).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class,()->inventoryService.deleteById(99)) ;

    }
}