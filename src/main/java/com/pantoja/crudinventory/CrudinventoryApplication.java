package com.pantoja.crudinventory;

import com.pantoja.crudinventory.entity.Item;
import com.pantoja.crudinventory.entity.Location;
import com.pantoja.crudinventory.service.InventoryService;
import com.pantoja.crudinventory.service.InventoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudinventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudinventoryApplication.class, args);
	}

}
