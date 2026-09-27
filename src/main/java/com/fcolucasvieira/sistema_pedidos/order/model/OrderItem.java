package com.fcolucasvieira.sistema_pedidos.order.model;

import com.fcolucasvieira.sistema_pedidos.product.model.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "full_price", nullable = false)
    private BigDecimal fullPrice;

    protected OrderItem() {}

    public OrderItem(Product product, int quantity, BigDecimal fullPrice) {
        this.product = product;
        this.quantity = quantity;
        this.fullPrice = fullPrice;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
