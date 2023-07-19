package com.microtest.inventoryservice.config;

import com.microtest.inventoryservice.InventoryRepository;
import com.microtest.inventoryservice.model.Inventory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryConfig {

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepo){
        return args-> {
            if(inventoryRepo.findAll().size()==0) {
                inventoryRepo.save(
                        Inventory.builder()
                                .skuCode("chair")
                                .quantity(10)
                                .build()
                );
                inventoryRepo.save(
                        Inventory.builder()
                                .skuCode("fork")
                                .quantity(0)
                                .build()
                );
            }
        };
    }

}
