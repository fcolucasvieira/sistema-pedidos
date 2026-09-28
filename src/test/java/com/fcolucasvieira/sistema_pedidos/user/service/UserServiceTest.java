package com.fcolucasvieira.sistema_pedidos.user.service;

import com.fcolucasvieira.sistema_pedidos.common.exception.BusinessRuleException;
import com.fcolucasvieira.sistema_pedidos.user.dto.CreateUserRequest;
import com.fcolucasvieira.sistema_pedidos.user.dto.CreateUserResponse;
import com.fcolucasvieira.sistema_pedidos.user.model.Role;
import com.fcolucasvieira.sistema_pedidos.user.model.User;
import com.fcolucasvieira.sistema_pedidos.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository repository;

    private final String defaultPassword = "password";

    private UserService service;

    @BeforeEach
    void setUp(){
        service = new UserService(repository, defaultPassword);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class)
    void shouldCreateUserWithSuccessWhenEmailDoesNotExist(Role role) {
        String email = "user@gmail.com";

        CreateUserRequest request = new CreateUserRequest(
                "User",
                email,
                role
        );

        when(repository.existsByEmail(email))
                .thenReturn(false);
        when(repository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateUserResponse response = service.create(request);

        assertNotNull(response);

        assertEquals(request.name(), response.name());

        verify(repository, times(1)).existsByEmail(email);
        verify(repository, times(1)).save(any(User.class));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class)
    void shouldThrowExceptionWhenUserAlreadyExistsWithEmail(Role role) {
        String email = "user@gmail.com";

        CreateUserRequest request = new CreateUserRequest(
                "User",
                email,
                role
        );

        when(repository.existsByEmail(email)).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> service.create(request));

        verify(repository, times(1)).existsByEmail(email);
        verify(repository, never()).save(any(User.class));
    }
}