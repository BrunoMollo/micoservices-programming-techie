package com.microtest.inventoryservice;

import com.microtest.inventoryservice.dto.InventoryResponse;
import com.microtest.inventoryservice.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepo;

    @Transactional()
    public List<InventoryResponse> isInStock(List<String> skuCodes){
        List<InventoryResponse> founds = inventoryRepo.findBySkuCodeIn(skuCodes)
                .stream().map(
                        x -> InventoryResponse.builder()
                                .skuCode(x.getSkuCode())
                                .isInStock(x.getQuantity() > 0)
                                .build()
                ).toList();

        List<InventoryResponse> response = new LinkedList<>(founds);
        for(String sku : skuCodes){
            boolean alreadyIn=false;
            for(InventoryResponse dbSku : founds){
                if (Objects.equals(sku, dbSku.getSkuCode())) {
                    alreadyIn = true;
                    break;
                }
            }
            if(!alreadyIn){
                response.add(new InventoryResponse(sku, false));
            }
        }

        return response;
    }
}
