package com.fcolucasvieira.sistema_pedidos.user.controller;

import com.fcolucasvieira.sistema_pedidos.user.dto.CreateUserRequest;
import com.fcolucasvieira.sistema_pedidos.user.dto.CreateUserResponse;
import com.fcolucasvieira.sistema_pedidos.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> create(@RequestBody @Valid CreateUserRequest request){
        CreateUserResponse response = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
