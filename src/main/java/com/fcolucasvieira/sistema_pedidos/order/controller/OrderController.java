package com.fcolucasvieira.sistema_pedidos.order.controller;

import com.fcolucasvieira.sistema_pedidos.order.dto.request.AlterOrderStatusRequest;
import com.fcolucasvieira.sistema_pedidos.order.dto.request.CreateOrderRequest;
import com.fcolucasvieira.sistema_pedidos.order.dto.response.OrderResponse;
import com.fcolucasvieira.sistema_pedidos.order.service.OrderService;
import com.fcolucasvieira.sistema_pedidos.order.usecase.CreateOrderUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderService service;

    public OrderController(CreateOrderUseCase createOrderUseCase, OrderService service) {
        this.createOrderUseCase = createOrderUseCase;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid CreateOrderRequest request) {
        OrderResponse response = createOrderUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<OrderResponse>> findAll() {
        List<OrderResponse> response = service.findAll();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable UUID id) {
        OrderResponse response = service.findById(id);

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable UUID id,
                                                      @RequestBody AlterOrderStatusRequest request) {
        OrderResponse response = service.alterOrderStatus(id, request);

        return ResponseEntity.ok().body(response);
    }
}
