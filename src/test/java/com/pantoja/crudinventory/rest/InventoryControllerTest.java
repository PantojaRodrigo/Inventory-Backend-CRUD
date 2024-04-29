package com.pantoja.crudinventory.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pantoja.crudinventory.entity.Item;
import com.pantoja.crudinventory.entity.Location;
import com.pantoja.crudinventory.misc.ItemIdExistingException;
import com.pantoja.crudinventory.misc.ItemNotFoundException;
import com.pantoja.crudinventory.service.InventoryService;
import com.pantoja.crudinventory.service.InventoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class InventoryControllerTest {


    @Mock
    InventoryServiceImpl inventoryServiceMock;
    @InjectMocks
    InventoryController inventoryController;
    Flux<Item> itemsFlux;

    @Autowired
    private LocalValidatorFactoryBean validator;

    @BeforeEach
    void setup() {
        List<Item> items = new ArrayList<>();
        Location loc1 = new Location(1, "available", "WMD1", 12345);
        Location loc2 = new Location(2, "damaged", "WMD1", 54321);
        Location loc3 = new Location(3, "available", "WMD1", 98766);

        Item item1 = new Item(1, "AHOOK", "A hook", loc1);
        Item item2 = new Item(2, "BASEBALL", "A baseball", loc2);
        Item item3 = new Item(3, "CAN", "A can", loc3);

        items.add(item1);
        items.add(item2);
        items.add(item3);
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
        assertEquals(itemsFlux.elementAt(1).block(), actual);
    }

    @Test
    void getItemById_noExistingItem() {
        when(inventoryServiceMock.findById(5)).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> inventoryController.getItemById(5));

    }

    @Test
    void addItem_goodAttributes() {
        Location loc = new Location(1, "available", "WMD1", 12345);
        Item item = new Item(1, "REMO", "A remo", loc);
        when(inventoryServiceMock.save(item)).thenReturn(item);
        Item actual = inventoryController.addItem(item);
        assertEquals(item, actual);
    }

    @Test
    void addItem_Duplicate() {
        Location loc = new Location(1, "available", "WMD1", 12345);
        Item item = new Item(1, "REMO", "A remo", loc);
        when(inventoryServiceMock.save(item)).thenReturn(item);
        Item actual = inventoryController.addItem(item);
        assertEquals(item, actual);

        Location loc_dup = new Location(2, "available", "WMD1", 12345);
        Item item_dup = new Item(1, "REMO", "A remo", loc);
        doThrow(new ItemIdExistingException(item.getItemId()))
                .when(inventoryServiceMock).save(item_dup);
        assertThrows(ItemIdExistingException.class, () -> inventoryController.addItem(item_dup));
    }

    //No funciona (pasa la prueba pero no se valida nada)
    @Test
    void addItem_badAttributes() {
        Location loc = new Location(1, "asda", "WMD1", 12345);
        Item item = new Item(1, null, "A remo", loc);
        Item item2 = new Item();
        //System.out.println(validator.getConstraintValidatorFactory());
        validator.validate(item2);
        validator.validateProperty(item2, "itemName");

    }
    //El testing del @Valid esta en la otra clase (ya no es unit testing)
    @Test
    void deleteItem() {
        when(inventoryServiceMock.findById(1)).thenReturn(itemsFlux.elementAt(1).block());
        String actual = inventoryController.deleteItem(1);
        assertEquals("Deleted item id: 1", actual);
    }

    @Test
    void deleteById_notFound() {
        when(inventoryServiceMock.findById(99)).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> inventoryController.deleteItem(99));

    }





}

