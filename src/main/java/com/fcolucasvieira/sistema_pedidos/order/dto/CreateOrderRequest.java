package com.fcolucasvieira.sistema_pedidos.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        @NotNull
        UUID userId,

        @NotNull
        List<OrderItemRequest> items
) {
}
