package com.microtest.productservice.dto;

import java.math.BigDecimal;

public record GetProductResponse(
        String name,
        String description,
        BigDecimal price
)
{
}
