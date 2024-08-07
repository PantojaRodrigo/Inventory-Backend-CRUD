package com.pantoja.crudinventory.misc;

public class ItemIdExistingException extends RuntimeException {
    public ItemIdExistingException(int id) {
        super("The item with ID: "+id+" already exists");
    }
}
