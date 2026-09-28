package com.fcolucasvieira.sistema_pedidos.order.service;

import com.fcolucasvieira.sistema_pedidos.common.exception.ResourceNotFoundException;
import com.fcolucasvieira.sistema_pedidos.order.dto.request.AlterOrderStatusRequest;
import com.fcolucasvieira.sistema_pedidos.order.dto.response.OrderResponse;
import com.fcolucasvieira.sistema_pedidos.order.mapper.OrderMapper;
import com.fcolucasvieira.sistema_pedidos.order.model.Order;
import com.fcolucasvieira.sistema_pedidos.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    public OrderService(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<OrderResponse> findAll() {
        List<Order> orders = repository.findAll();

        return orders.stream()
                .map(mapper::toResponse)
                .toList();
    }

    public OrderResponse findById(UUID id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with Id: " + id));

        return mapper.toResponse(order);
    }

    @Transactional
    public OrderResponse alterOrderStatus(UUID id, AlterOrderStatusRequest request) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with Id: " + id));

        order.updateStatus(request.status());

        repository.save(order);

        return mapper.toResponse(order);
    }
}
