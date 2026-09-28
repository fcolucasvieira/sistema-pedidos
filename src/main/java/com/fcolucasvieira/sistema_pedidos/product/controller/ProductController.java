package com.fcolucasvieira.sistema_pedidos.product.controller;

import com.fcolucasvieira.sistema_pedidos.product.dto.CreateProductRequest;
import com.fcolucasvieira.sistema_pedidos.product.dto.CreateProductResponse;
import com.fcolucasvieira.sistema_pedidos.product.model.Product;
import com.fcolucasvieira.sistema_pedidos.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/products")
@Tag(name = "Produtos", description = "Endpoints para gerenciamento do catálogo de produtos e estoque")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Criar um novo produto", description = "Cadastra um novo produto no catálogo com nome, preço e estoque inicial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos")
    })
    @PostMapping
    public ResponseEntity<CreateProductResponse> create(@RequestBody @Valid CreateProductRequest request) {
        CreateProductResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
