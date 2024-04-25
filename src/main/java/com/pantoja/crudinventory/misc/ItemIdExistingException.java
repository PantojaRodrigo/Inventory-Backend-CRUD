package com.pantoja.crudinventory.misc;

public class ItemIdExistingException extends RuntimeException {
    public ItemIdExistingException(int id) {
        super("Ya existe el item : "+id);
    }
}
