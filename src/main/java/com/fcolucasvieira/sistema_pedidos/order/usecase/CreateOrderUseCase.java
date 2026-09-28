package com.fcolucasvieira.sistema_pedidos.order.usecase;

import com.fcolucasvieira.sistema_pedidos.order.dto.request.CreateOrderRequest;
import com.fcolucasvieira.sistema_pedidos.order.dto.request.OrderItemRequest;
import com.fcolucasvieira.sistema_pedidos.order.dto.response.OrderResponse;
import com.fcolucasvieira.sistema_pedidos.order.mapper.OrderMapper;
import com.fcolucasvieira.sistema_pedidos.order.model.Order;
import com.fcolucasvieira.sistema_pedidos.order.model.OrderItem;
import com.fcolucasvieira.sistema_pedidos.order.repository.OrderRepository;
import com.fcolucasvieira.sistema_pedidos.product.model.Product;
import com.fcolucasvieira.sistema_pedidos.product.repository.ProductRepository;
import com.fcolucasvieira.sistema_pedidos.user.model.User;
import com.fcolucasvieira.sistema_pedidos.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;

    @Transactional
    public OrderResponse execute(CreateOrderRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found with Id: " + request.userId()));

        List<OrderItem> items = request.items().stream()
                .map(this::processOrderItem)
                        .toList();

        Order order = new Order(user, items);

        orderRepository.save(order);

        return mapper.toResponse(order);
    }

    private OrderItem processOrderItem(OrderItemRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found with Id: " + request.productId()));

        product.reduceStock(request.quantity());

        return new OrderItem(product, request.quantity());
    }
}
