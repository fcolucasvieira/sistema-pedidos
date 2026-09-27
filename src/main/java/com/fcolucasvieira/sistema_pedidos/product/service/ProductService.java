package com.fcolucasvieira.sistema_pedidos.product.service;

import com.fcolucasvieira.sistema_pedidos.product.dto.CreateProductRequest;
import com.fcolucasvieira.sistema_pedidos.product.dto.CreateProductResponse;
import com.fcolucasvieira.sistema_pedidos.product.model.Product;
import com.fcolucasvieira.sistema_pedidos.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CreateProductResponse create(CreateProductRequest request){
        Product product = new Product(
                request.name(),
                request.price(),
                request.quantity()
        );

        repository.save(product);

        return new CreateProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }
}
