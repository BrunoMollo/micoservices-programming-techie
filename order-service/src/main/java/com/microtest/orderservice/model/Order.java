package com.microtest.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="prod_order")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Order {
    @Id @GeneratedValue(strategy = IDENTITY) @Column(name = "id")
    private Long id;
    private String orderNumber;
    private LocalDate createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItems;



    public void addLine(OrderLineItems line){
        if(orderLineItems==null){
            orderLineItems=new ArrayList<>();
        }
        orderLineItems.add(line);
        line.setOrder(this);
    }
}
