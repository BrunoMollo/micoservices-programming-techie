package com.microtest.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderLineItems {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="fk_order", nullable = false, foreignKey = @ForeignKey(name = "FK_prod_orders_to_lines"))
    private Order order;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
