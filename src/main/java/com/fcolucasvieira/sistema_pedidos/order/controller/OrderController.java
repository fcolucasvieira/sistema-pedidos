package com.fcolucasvieira.sistema_pedidos.order.controller;

import com.fcolucasvieira.sistema_pedidos.order.dto.request.AlterOrderStatusRequest;
import com.fcolucasvieira.sistema_pedidos.order.dto.request.CreateOrderRequest;
import com.fcolucasvieira.sistema_pedidos.order.dto.response.OrderResponse;
import com.fcolucasvieira.sistema_pedidos.order.service.OrderService;
import com.fcolucasvieira.sistema_pedidos.order.usecase.CreateOrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Pedidos", description = "Endpoints para criação, consulta e atualização de status de pedidos")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderService service;

    public OrderController(CreateOrderUseCase createOrderUseCase, OrderService service) {
        this.createOrderUseCase = createOrderUseCase;
        this.service = service;
    }


    @Operation(summary = "Criar um novo pedido", description = "Valida usuário, estoque dos produtos e efetua a criação do pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou estoque insuficiente"),
            @ApiResponse(responseCode = "404", description = "Usuário ou produto não encontrado")
    })
    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid CreateOrderRequest request) {
        OrderResponse response = createOrderUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todos os pedidos")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    @GetMapping()
    public ResponseEntity<List<OrderResponse>> findAll() {
        List<OrderResponse> response = service.findAll();

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Buscar pedido por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable UUID id) {
        OrderResponse response = service.findById(id);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Atualizar status do pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status inválido ou transição não permitida para pedido finalizado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable UUID id,
                                                      @RequestBody AlterOrderStatusRequest request) {
        OrderResponse response = service.alterOrderStatus(id, request);

        return ResponseEntity.ok().body(response);
    }
}
