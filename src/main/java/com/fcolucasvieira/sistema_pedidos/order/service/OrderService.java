package com.fcolucasvieira.sistema_pedidos.order.service;

import com.fcolucasvieira.sistema_pedidos.order.repository.OrderRepository;
import com.fcolucasvieira.sistema_pedidos.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public
}
