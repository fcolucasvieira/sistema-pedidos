package com.fcolucasvieira.sistema_pedidos.order.mapper;

import com.fcolucasvieira.sistema_pedidos.order.dto.response.OrderItemResponse;
import com.fcolucasvieira.sistema_pedidos.order.dto.response.OrderResponse;
import com.fcolucasvieira.sistema_pedidos.order.model.Order;
import com.fcolucasvieira.sistema_pedidos.order.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getUser().getName(),
                toOrderItemListResponse(order.getItems()),
                order.calculateTotalPrice(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }

    private OrderItemResponse toOrderItemResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getFullPrice()
        );
    }

    public List<OrderItemResponse> toOrderItemListResponse(List<OrderItem> items) {
        return items.stream()
                .map(this::toOrderItemResponse)
                .toList();
    }
}
