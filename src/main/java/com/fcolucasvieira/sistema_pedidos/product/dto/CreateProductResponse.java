package com.fcolucasvieira.sistema_pedidos.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductResponse(
        UUID id,
        String name,
        BigDecimal price
) {
}
