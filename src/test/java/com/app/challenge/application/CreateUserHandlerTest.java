package com.app.challenge.application;

import com.app.challenge.application.handler.CreateUserHandler;
import com.app.challenge.application.mapper.UserMapper;
import com.app.challenge.application.service.CreateUserService;
import com.app.challenge.domain.model.dto.User;
import com.app.challenge.domain.model.dto.request.CreateUserRequest;
import com.app.challenge.domain.model.dto.request.PhoneRequest;
import com.app.challenge.domain.model.dto.response.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


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
        this.request = new CreateUserRequest(
            "Juan", "juan@mail.com", "HunterApp2",
            List.of(new PhoneRequest("1234567", "1", "57"))
        );

        this.domainUser = new User(
            UUID.randomUUID(),
            this.request.name(),
            this.request.email(),
            this.request.password(),
            List.of(), // Podrías mapear Phone si quieres ir más detallado
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "jwt-token-fake",
            true
        );

        this.expectedResponse = new UserResponse(
            this.domainUser.getId(),
            this.domainUser.getName(),
            this.domainUser.getEmail(),
            this.domainUser.getToken(),
            this.domainUser.getCreated(),
            this.domainUser.getModified(),
            this.domainUser.getLastLogin(),
            this.request.phones(),
            this.domainUser.isActive()
        );
    }

    @Test
    @DisplayName("Debe mapear correctamente y crear el usuario")
    void shouldMapAndCreateUserSuccessfully() {
        // Arrange
        Mockito.when(this.mapper.toDomain(this.request)).thenReturn(this.domainUser);
        Mockito.when(this.service.createUser(this.domainUser)).thenReturn(this.domainUser);
        Mockito.when(this.mapper.toResponse(this.domainUser)).thenReturn(this.expectedResponse);

        // Act
        UserResponse response = this.handler.handle(this.request);

        // Assert
        Assertions.assertEquals(this.expectedResponse.email(), response.email());
        Assertions.assertEquals(this.expectedResponse.name(), response.name());
        Assertions.assertEquals(this.expectedResponse.token(), response.token());
        Mockito.verify(this.mapper).toDomain(this.request);
        Mockito.verify(this.service).createUser(this.domainUser);
        Mockito.verify(this.mapper).toResponse(this.domainUser);
    }
}
