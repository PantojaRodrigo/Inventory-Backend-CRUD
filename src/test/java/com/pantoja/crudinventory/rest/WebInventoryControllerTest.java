package com.pantoja.crudinventory.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pantoja.crudinventory.entity.Item;
import com.pantoja.crudinventory.entity.Location;
import com.pantoja.crudinventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

//@SpringBootTest
@WebMvcTest(InventoryController.class)
class WebInventoryControllerTest {
    
    //Funciona (ya no es unit testing, pero se prueba el valid)
    @MockBean
    private InventoryService inventoryService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void addItem_testValidAnnotaiton_invalidId() throws Exception{
        Location loc = new Location(1, "asda", "WMD1", 12345);
        Item item = new Item(0, "REMO", "A remo", loc);
        Item item2 = new Item();
        String reqBody = new ObjectMapper().writeValueAsString(item);

        //doNothing().when(inventoryService.save(any(Item.class)));
        when(inventoryService.save(item)).thenReturn(item);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/items")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(reqBody)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
    @Test
    void addItem_testValidAnnotaiton_noName() throws Exception{
        Location loc = new Location(1, "asda", "WMD1", 12345);
        Item item = new Item(1, null, "A remo", loc);
        Item item2 = new Item();
        String reqBody = new ObjectMapper().writeValueAsString(item);

        //doNothing().when(inventoryService.save(any(Item.class)));
        when(inventoryService.save(item)).thenReturn(item);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/items")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(reqBody)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void addItem_testValidAnnotaiton_noState() throws Exception{
        Location loc = new Location(1, null, "WMD1", 12345);
        Item item = new Item(1, "REMO", "A remo", loc);
        Item item2 = new Item();
        String reqBody = new ObjectMapper().writeValueAsString(item);

        //doNothing().when(inventoryService.save(any(Item.class)));
        when(inventoryService.save(item)).thenReturn(item);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/items")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(reqBody)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

    }






}

