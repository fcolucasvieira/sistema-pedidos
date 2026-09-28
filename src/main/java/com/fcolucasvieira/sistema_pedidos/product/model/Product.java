package com.fcolucasvieira.sistema_pedidos.product.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    public void reduceStock(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Reduction amount can't be less than or 0");

        if (this.quantity < amount)
            throw new IllegalArgumentException(
                    String.format("Insufficient stock for product '%s'. Request: %d, Available: %d",
                    this.name, amount, this.quantity)
            );

        quantity -= amount;
    }

    protected Product() {}

    public Product(String name, BigDecimal price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
