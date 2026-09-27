package com.fcolucasvieira.sistema_pedidos.product.service;

import com.fcolucasvieira.sistema_pedidos.product.dto.CreateProductRequest;
import com.fcolucasvieira.sistema_pedidos.product.dto.CreateProductResponse;
import com.fcolucasvieira.sistema_pedidos.product.model.Product;
import com.fcolucasvieira.sistema_pedidos.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository repository;

    private ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductService(repository);
    }

    @Test
    void shouldCreateProductSuccessfully() {
        CreateProductRequest request = new CreateProductRequest(
                "Product",
                10,
                new BigDecimal("100.00")
        );

        when(repository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateProductResponse response = service.create(request);

        assertNotNull(response);
        assertEquals(request.name(), response.name());
        assertEquals(request.price(), response.price());

        verify(repository, times(1)).save(any(Product.class));
    }
}