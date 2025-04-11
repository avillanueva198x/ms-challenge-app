package com.app.challenge.application;

import com.app.challenge.application.handler.CreateUserHandler;
import com.app.challenge.application.mapper.UserMapper;
import com.app.challenge.application.service.CreateUserService;
import com.app.challenge.domain.model.dto.User;
import com.app.challenge.domain.model.dto.request.CreateUserRequest;
import com.app.challenge.domain.model.dto.request.PhoneRequest;
import com.app.challenge.domain.model.dto.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserHandlerTest {

    @Mock
    private CreateUserService service;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private CreateUserHandler handler;

    private CreateUserRequest request;
    private User domainUser;
    private UserResponse expectedResponse;

    @BeforeEach
    void setup() {
        request = new CreateUserRequest(
            "Juan", "juan@mail.com", "HunterApp2",
            List.of(new PhoneRequest("1234567", "1", "57"))
        );

        domainUser = new User(
            UUID.randomUUID(),
            request.name(),
            request.email(),
            request.password(),
            List.of(), // Podrías mapear Phone si quieres ir más detallado
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "jwt-token-fake",
            true
        );

        expectedResponse = new UserResponse(
            domainUser.getId(),
            domainUser.getName(),
            domainUser.getEmail(),
            domainUser.getToken(),
            domainUser.getCreated(),
            domainUser.getModified(),
            domainUser.getLastLogin(),
            request.phones(),
            domainUser.isActive()
        );
    }

    @Test
    @DisplayName("Debe mapear correctamente y crear el usuario")
    void shouldMapAndCreateUserSuccessfully() {
        // Arrange
        when(mapper.toDomain(request)).thenReturn(domainUser);
        when(service.createUser(domainUser)).thenReturn(domainUser);
        when(mapper.toResponse(domainUser)).thenReturn(expectedResponse);

        // Act
        UserResponse response = handler.handle(request);

        // Assert
        assertEquals(expectedResponse.email(), response.email());
        assertEquals(expectedResponse.name(), response.name());
        assertEquals(expectedResponse.token(), response.token());
        verify(mapper).toDomain(request);
        verify(service).createUser(domainUser);
        verify(mapper).toResponse(domainUser);
    }
}
