package com.app.challenge.application;

import com.app.challenge.application.handler.CreateUserHandler;
import com.app.challenge.application.mapper.UserMapper;
import com.app.challenge.application.service.CreateUserService;
import com.app.challenge.domain.model.dto.User;
import com.app.challenge.domain.model.dto.request.CreateUserRequest;
import com.app.challenge.domain.model.dto.request.PhoneRequest;
import com.app.challenge.domain.model.dto.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserHandlerTest {

    @Mock
    private CreateUserService service;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private CreateUserHandler handler;


    @Test
    void shouldMapAndCreateUser() {
        // Arrange
        var request = new CreateUserRequest("Juan", "juan@mail.com", "Hunter2", List.of(
            new PhoneRequest("1234567", "1", "57")
        ));

        var mockUser = new User(
            UUID.randomUUID(),
            "Juan",
            "juan@mail.com",
            "Hunter2",
            List.of(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "jwt-token-fake",
            true
        );

        when(service.createUser(any(User.class))).thenReturn(mockUser);

        // Act
        UserResponse response = handler.handle(request);

        // Assert
        assertEquals("juan@mail.com", response.email());
        assertEquals("Juan", response.name());
        assertEquals(true, response.isActive());
    }
}
