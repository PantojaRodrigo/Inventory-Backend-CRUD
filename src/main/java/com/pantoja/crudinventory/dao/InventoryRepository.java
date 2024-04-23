package com.pantoja.crudinventory.dao;

import com.pantoja.crudinventory.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Item,Integer> {

}
