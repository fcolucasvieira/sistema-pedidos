package com.fcolucasvieira.sistema_pedidos.order.repository;

import com.fcolucasvieira.sistema_pedidos.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
