package com.pantoja.crudinventory.service;

import com.pantoja.crudinventory.dao.InventoryRepository;
import com.pantoja.crudinventory.entity.Item;
import com.pantoja.crudinventory.entity.Location;
import com.pantoja.crudinventory.misc.ItemIdExistingException;
import com.pantoja.crudinventory.misc.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    InventoryRepository inventoryRepositoryMock;
    @InjectMocks
    InventoryServiceImpl inventoryService;

    List<Item> items;
    @BeforeEach
    void setup(){
         items = new ArrayList<>();
        Location loc1 = new Location(1,"available","WMD1",12345);
        Location loc2 = new Location(2,"damaged","WMD1",54321);
        Location loc3 = new Location(3,"available","WMD1",98766);
        Item item1 =  new Item(1,"AHOOK","A hook",loc1);
        Item item2 =  new Item(2,"BASEBALL","A baseball",loc2);
        Item item3 =  new Item(3,"CAN","A can",loc3);
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
        Location loc = new Location(1,"available","WMD1",12345);
        Item item = new Item(1,"REMO","A remo",loc);
        when(inventoryRepositoryMock.save(item)).thenReturn(item);
        Item actual = inventoryService.save(item);
        assertEquals(item,actual);
    }
    //TODO:Unit Test unhappy path
    @Test
    void save_Duplicate() {
        Location loc = new Location(1,"available","WMD1",12345);
        Item item = new Item(1,"REMO","A remo",loc);
        when(inventoryRepositoryMock.save(item)).thenReturn(item);
        Item actual = inventoryService.save(item);
        assertEquals(item,actual);

        Location loc_dup = new Location(2,"available","WMD1",12345);
        Item item_dup = new Item(1,"REMO","A remo",loc);
        doThrow(new ItemIdExistingException(item.getItemId()))
                .when(inventoryRepositoryMock).save(item_dup);
        assertThrows(ItemIdExistingException.class,()->inventoryService.save(item_dup));
    }
    @Test
    void save_withNullArgs() {
        Location loc = new Location(1,null,"WMD1",12345);
        Item item = new Item(1,"REMO","A remo",loc);
        doThrow(new MethodArgumentNotValidException(null, null))
                .when(inventoryRepositoryMock).save(item);
        assertThrows(MethodArgumentNotValidException.class,()->inventoryService.save(item));
    }


    @Test
    void deleteById() {
        when(inventoryRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(items.get(1)));

        inventoryService.deleteById(1);

        verify(inventoryRepositoryMock).findById(1);
        verify(inventoryRepositoryMock).deleteById(1);
    }

    @Test
    void deleteById_notFound() {
        when(inventoryRepositoryMock.findById(99)).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class,()->inventoryService.deleteById(99)) ;

    }


}