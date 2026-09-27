package com.fcolucasvieira.sistema_pedidos.product.controller;

import com.fcolucasvieira.sistema_pedidos.product.dto.CreateProductRequest;
import com.fcolucasvieira.sistema_pedidos.product.dto.CreateProductResponse;
import com.fcolucasvieira.sistema_pedidos.product.model.Product;
import com.fcolucasvieira.sistema_pedidos.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> create(@RequestBody @Valid CreateProductRequest request) {
        CreateProductResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
