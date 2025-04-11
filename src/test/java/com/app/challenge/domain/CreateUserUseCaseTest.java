package com.app.challenge.domain;

import com.app.challenge.domain.model.dto.User;
import com.app.challenge.domain.port.SaveUserPort;
import com.app.challenge.domain.usecase.CreateUserUseCase;
import com.app.challenge.infrastructure.config.util.JwtUtil;
import com.app.challenge.infrastructure.rest.advice.EmailAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private SaveUserPort saveUserPort;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private CreateUserUseCase useCase;

    private User inputUser;

    @BeforeEach
    void setUp() {
        inputUser = new User(
            null,
            "Juan",
            "juan@mail.com",
            "HunterApp2",
            List.of(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            null,
            false
        );
    }

    @Test
    @DisplayName("Debe crear usuario correctamente cuando no existe el correo")
    void shouldCreateUserSuccessfully() {
        // Arrange
        when(saveUserPort.existsByEmail(inputUser.getEmail())).thenReturn(false);
        when(jwtUtil.generateToken(any(User.class))).thenReturn("jwt-token-fake");
        when(saveUserPort.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User createdUser = useCase.createUser(inputUser);

        // Assert
        assertNotNull(createdUser.getId());
        assertEquals("jwt-token-fake", createdUser.getToken());
        assertTrue(createdUser.isActive());
        verify(saveUserPort).existsByEmail(inputUser.getEmail());
        verify(saveUserPort).save(any(User.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el correo ya está registrado")
    void shouldThrowExceptionIfEmailExists() {
        // Arrange
        when(saveUserPort.existsByEmail(inputUser.getEmail())).thenReturn(true);

        // Act + Assert
        var exception = assertThrows(EmailAlreadyExistsException.class, () -> {
            useCase.createUser(inputUser);
        });

        assertEquals("El correo ya registrado", exception.getMessage());
        verify(saveUserPort).existsByEmail(inputUser.getEmail());
        verify(saveUserPort, never()).save(any());
        verify(jwtUtil, never()).generateToken(any());
    }
}
