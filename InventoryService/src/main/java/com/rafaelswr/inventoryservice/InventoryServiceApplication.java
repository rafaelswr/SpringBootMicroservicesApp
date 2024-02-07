package com.rafaelswr.inventoryservice;

import com.rafaelswr.inventoryservice.models.Inventory;
import com.rafaelswr.inventoryservice.repositories.InventoryRepository;
import com.rafaelswr.inventoryservice.services.InventoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(InventoryService inventoryService, InventoryRepository inventoryRepository){
        return args -> {
            Inventory inv = new Inventory("Iphone 13",2);
            Inventory inv2 = new Inventory("Macbook Air 13",10);
            inventoryRepository.save(inv2);
            inventoryRepository.save(inv);

        };
    }
}
