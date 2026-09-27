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

    public OrderItem(Product product, int quantity) {
        if (product == null)
            throw new IllegalArgumentException("Product can't be null");

        this.product = product;

        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity can't be less than or 0");

        this.quantity = quantity;

        this.fullPrice = product.getPrice()
                .multiply(BigDecimal.valueOf(quantity));
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
