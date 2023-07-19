package com.microtest.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data @Builder @AllArgsConstructor
public class CreateProductRequest{
   private String name;
   private String description;
   private BigDecimal price;


}
