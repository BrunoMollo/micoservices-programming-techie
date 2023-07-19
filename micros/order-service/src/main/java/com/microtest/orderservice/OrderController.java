package com.microtest.orderservice;

import com.microtest.orderservice.dto.PlaceOrderRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController @RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping@ResponseStatus(CREATED)
    public String place_order(@RequestBody PlaceOrderRequest placeOrderRequest){
        orderService.placeOrder(placeOrderRequest);
        return "Order placed successfully";
    }
}
