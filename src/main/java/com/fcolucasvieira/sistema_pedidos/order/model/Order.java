package com.fcolucasvieira.sistema_pedidos.order.model;

import com.fcolucasvieira.sistema_pedidos.user.model.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // (mappedBy = "order") -> relacionamento entre order e order_items
    // Referenciada na tabela order_items através do atributo order (coluna order_id)
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status =  OrderStatus.IN_PROGRESS;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    protected Order() {}

    public Order(User user, List<OrderItem> items) {
        this.user = user;

        if (items != null) {
            items.forEach(this::addItem);
        }
    }

    // Adiciona item ao pedido relacionando os dois
    public void addItem(OrderItem item) {
        this.items.add(item);

        item.setOrder(this);
    }
}
