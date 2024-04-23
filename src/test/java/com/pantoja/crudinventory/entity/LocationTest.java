package com.pantoja.crudinventory.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    public void createLocation(){
        Location location = new Location("available","WMD1",9991);
    }
}