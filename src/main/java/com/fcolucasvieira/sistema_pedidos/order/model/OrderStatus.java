package com.fcolucasvieira.sistema_pedidos.order.model;

public enum OrderStatus {
    PENDING,
    PROCESSING,
    DELIVERED,
    CANCELLED;

    public boolean isTerminal() {
        return this == DELIVERED || this == CANCELLED;
    }
}
