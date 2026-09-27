package com.fcolucasvieira.sistema_pedidos.order.repository;

import com.fcolucasvieira.sistema_pedidos.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
