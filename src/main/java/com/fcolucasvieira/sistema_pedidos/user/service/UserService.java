package com.fcolucasvieira.sistema_pedidos.user.service;

import com.fcolucasvieira.sistema_pedidos.common.exception.BusinessRuleException;
import com.fcolucasvieira.sistema_pedidos.user.dto.CreateUserRequest;
import com.fcolucasvieira.sistema_pedidos.user.dto.CreateUserResponse;
import com.fcolucasvieira.sistema_pedidos.user.model.User;
import com.fcolucasvieira.sistema_pedidos.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final String defaultPassword;

    public UserService(UserRepository repository,
                       @Value("${app.security.default-password}") String defaultPassword) {
        this.repository = repository;
        this.defaultPassword = defaultPassword;
    }

    @Transactional
    public CreateUserResponse create(CreateUserRequest request) {
        boolean existsEmail = repository.existsByEmail(request.email());

        if (existsEmail)
            throw new BusinessRuleException("User already exists with Email: " + request.email());

        User user = new User(
                request.name(),
                request.email(),
                defaultPassword,
                request.role()
        );

        repository.save(user);

        return new CreateUserResponse(
                user.getId(),
                user.getName()
        );
    }
}
