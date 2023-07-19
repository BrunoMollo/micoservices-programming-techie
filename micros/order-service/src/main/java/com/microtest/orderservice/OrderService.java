package com.microtest.orderservice;

import com.microtest.orderservice.dto.InventoryResponse;
import com.microtest.orderservice.dto.OrderLineItemsDto;
import com.microtest.orderservice.dto.PlaceOrderRequest;
import com.microtest.orderservice.model.Order;
import com.microtest.orderservice.model.OrderLineItems;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.stream;

@Service @AllArgsConstructor
public class OrderService {

    private final WebClient webClient;
    private final OrderRepository orderRepo;
    public void placeOrder(PlaceOrderRequest dto){

        List<String> skuCodes=dto.getLines().stream()
                .map(OrderLineItemsDto::getSkuCode)
                .toList();

       InventoryResponse[] inventoryResponses=webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder
                            .queryParam("skuCode", skuCodes)
                            .build()
                )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

       if(inventoryResponses==null){
           throw new IllegalStateException("inventory service is not responding");
       }

        List<InventoryResponse> outOfStock=stream(inventoryResponses)
                .filter(x->!x.getIsInStock())
                .toList();
        if(outOfStock.size()>0){
            throw new IllegalStateException("out of Stock: "
                    +outOfStock.stream()
                    .map(InventoryResponse::getSkuCode).toList().toString() );
        }


        Order order=Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .createdAt(LocalDate.now())
                .build();

        dto.getLines().stream().map(
                x-> OrderLineItems.builder()
                    .skuCode(x.getSkuCode())
                    .quantity(x.getQuantity())
                    .price(x.getPrice())
                    .build()
        ).forEach(order::addLine);

        orderRepo.save(order);

    }
}
