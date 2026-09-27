package com.fcolucasvieira.sistema_pedidos.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank
        String name,
        @Min(1)
        int quantity,
        @Positive
        BigDecimal price
) {
}
