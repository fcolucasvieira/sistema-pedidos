package com.fcolucasvieira.sistema_pedidos.order.usecase;

import com.fcolucasvieira.sistema_pedidos.common.exception.BusinessRuleException;
import com.fcolucasvieira.sistema_pedidos.common.exception.ResourceNotFoundException;
import com.fcolucasvieira.sistema_pedidos.order.dto.request.CreateOrderRequest;
import com.fcolucasvieira.sistema_pedidos.order.dto.request.OrderItemRequest;
import com.fcolucasvieira.sistema_pedidos.order.dto.response.OrderResponse;
import com.fcolucasvieira.sistema_pedidos.order.mapper.OrderMapper;
import com.fcolucasvieira.sistema_pedidos.order.model.Order;
import com.fcolucasvieira.sistema_pedidos.order.repository.OrderRepository;
import com.fcolucasvieira.sistema_pedidos.product.model.Product;
import com.fcolucasvieira.sistema_pedidos.product.repository.ProductRepository;
import com.fcolucasvieira.sistema_pedidos.user.model.User;
import com.fcolucasvieira.sistema_pedidos.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderMapper mapper;

    @InjectMocks
    private CreateOrderUseCase createOrderUseCase;

    private UUID userId;
    private UUID productId;
    private User mockUser;
    private Product product;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        productId = UUID.randomUUID();

        mockUser = mock(User.class);

        product = new Product("Product", new BigDecimal("100.00"), 10);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        int purchaseAmount = 2;
        OrderItemRequest itemRequest = new OrderItemRequest(productId, purchaseAmount);

        CreateOrderRequest request = new CreateOrderRequest(userId, List.of(itemRequest));

        OrderResponse expectedResponse = mock(OrderResponse.class);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(mockUser));
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        when(mapper.toResponse(any(Order.class)))
                .thenReturn(expectedResponse);

        OrderResponse response = createOrderUseCase.execute(request);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        assertEquals(8, product.getQuantity());

        verify(userRepository).findById(userId);
        verify(productRepository).findById(productId);
        verify(orderRepository).save(any(Order.class));
        verify(mapper).toResponse(any(Order.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        CreateOrderRequest request = new CreateOrderRequest(userId, List.of());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> createOrderUseCase.execute(request));

        verify(productRepository, never()).findById(any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        OrderItemRequest itemRequest = new OrderItemRequest(productId, 1);
        CreateOrderRequest request = new CreateOrderRequest(userId, List.of(itemRequest));

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(mockUser));
        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> createOrderUseCase.execute(request));

        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {
        int excessiveAmount = 15;
        OrderItemRequest itemRequest = new OrderItemRequest(productId, excessiveAmount);

        CreateOrderRequest request = new CreateOrderRequest(userId, List.of(itemRequest));

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(mockUser));
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        assertThrows(BusinessRuleException.class, () -> createOrderUseCase.execute(request));

        verify(orderRepository, never()).save(any());
    }
}