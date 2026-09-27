package com.fcolucasvieira.sistema_pedidos.user.dto;

import java.util.UUID;

public record CreateUserResponse(
        UUID id,
        String name
) {
}
