package com.fcolucasvieira.sistema_pedidos.user.dto;

import com.fcolucasvieira.sistema_pedidos.user.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotBlank
        String name,

        @Email @NotBlank
        String email,

        @NotNull
        Role role
) {}
