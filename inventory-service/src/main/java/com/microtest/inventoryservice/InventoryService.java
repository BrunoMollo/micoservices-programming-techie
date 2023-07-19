package com.microtest.inventoryservice;

import com.microtest.inventoryservice.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepo;

    @Transactional()
    public boolean isInStock(String skuCode){
        Inventory inventory=inventoryRepo.findBySkuCode(skuCode).orElseThrow();
        return inventory.getQuantity() > 0;
    }
}
