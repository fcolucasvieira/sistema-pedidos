package com.fcolucasvieira.sistema_pedidos.order.dto.response;

import com.fcolucasvieira.sistema_pedidos.order.model.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        UUID userId,
        String userName,
        List<OrderItemResponse> items,
        BigDecimal totalPrice,
        OrderStatus status,
        OffsetDateTime createdAt
) {}
