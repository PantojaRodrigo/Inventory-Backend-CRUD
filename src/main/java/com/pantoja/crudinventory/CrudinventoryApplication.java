package com.pantoja.crudinventory;

import com.pantoja.crudinventory.dao.InventoryRepository;
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
	@Bean
	public CommandLineRunner loadData(InventoryRepository repository) {
		return (args) -> {
			// save a couple of customers

			repository.save(new Item(10, "AHOOK", "A Hook", new Location(10, "California", "WMD1", "12345")));
			repository.save(new Item(11, "TENIS", "A pair of Tennis Shoes", new Location(11, "Texas", "SportStore", "23456")));
			repository.save(new Item(12, "RACKET", "A Tennis Racket", new Location(12, "Florida", "SportCenter", "34567")));
			repository.save(new Item(13, "BALL", "A Football", new Location(13, "New York", "SportArena", "45678")));
			repository.save(new Item(14, "HELMET", "A Safety Helmet", new Location(14, "Illinois", "BikeStore", "56789")));
			repository.save(new Item(15, "GLOVES", "A pair of Gloves", new Location(15, "Ohio", "GloveShop", "67890")));
			repository.save(new Item(16, "BICYCLE", "A Mountain Bike", new Location(16, "Georgia", "CycleShop", "78901")));
			repository.save(new Item(17, "SKATES", "A pair of Skates", new Location(17, "North Carolina", "SkateWorld", "89012")));
			repository.save(new Item(18, "BACKPACK", "A Travel Backpack", new Location(18, "Michigan", "TravelStore", "90123")));
			repository.save(new Item(19, "TENT", "A Camping Tent", new Location(19, "Pennsylvania", "OutdoorGear", "01234")));
			repository.save(new Item(20, "FLASHLIGHT", "A LED Flashlight", new Location(20, "Arizona", "LightHouse", "12345")));
			repository.save(new Item(21, "GPS", "A Handheld GPS", new Location(21, "New Jersey", "TechShop", "23456")));
			repository.save(new Item(22, "CAMERA", "A Digital Camera", new Location(22, "Virginia", "CameraStore", "34567")));
			repository.save(new Item(23, "LAPTOP", "A Personal Laptop", new Location(23, "Washington", "TechHub", "45678")));
			repository.save(new Item(24, "HEADPHONES", "A pair of Headphones", new Location(24, "Massachusetts", "AudioShop", "56789")));
			repository.save(new Item(25, "WATCH", "A Smart Watch", new Location(25, "Indiana", "WatchStore", "67890")));
			repository.save(new Item(26, "TABLET", "A Tablet", new Location(26, "Missouri", "GadgetWorld", "78901")));
			repository.save(new Item(27, "CHAIR", "An Office Chair", new Location(27, "Maryland", "FurnitureShop", "89012")));
			repository.save(new Item(28, "DESK", "An Office Desk", new Location(28, "Wisconsin", "OfficeDepot", "90123")));
			repository.save(new Item(29, "LAMP", "A Desk Lamp", new Location(29, "Colorado", "LightShop", "01234")));
			repository.save(new Item(30, "MONITOR", "A Computer Monitor", new Location(30, "Minnesota", "TechMart", "12345")));
			repository.save(new Item(31, "MOUSE", "A Computer Mouse", new Location(31, "South Carolina", "GadgetStore", "23456")));
			repository.save(new Item(32, "KEYBOARD", "A Mechanical Keyboard", new Location(32, "Alabama", "KeyboardShop", "34567")));
			repository.save(new Item(33, "PRINTER", "A Laser Printer", new Location(33, "Louisiana", "OfficeTech", "45678")));
			repository.save(new Item(34, "SCANNER", "A Document Scanner", new Location(34, "Kentucky", "ScanStore", "56789")));
			repository.save(new Item(35, "SPEAKERS", "A pair of Speakers", new Location(35, "Oregon", "AudioWorld", "67890")));


		};
	}
}
