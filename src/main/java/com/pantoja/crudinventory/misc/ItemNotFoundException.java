package com.pantoja.crudinventory.misc;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(int id) {
        super("Item not found : "+id);
    }
}
