package com.fcolucasvieira.sistema_pedidos.order.dto.request;

import com.fcolucasvieira.sistema_pedidos.order.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record AlterOrderStatusRequest(
        @NotNull
        OrderStatus status
) {
}
