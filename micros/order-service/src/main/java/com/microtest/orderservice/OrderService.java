package com.microtest.orderservice;

import com.microtest.orderservice.dto.OrderLineItemsDto;
import com.microtest.orderservice.dto.PlaceOrderRequest;
import com.microtest.orderservice.model.Order;
import com.microtest.orderservice.model.OrderLineItems;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service @AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    public void placeOrder(PlaceOrderRequest dto){

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
