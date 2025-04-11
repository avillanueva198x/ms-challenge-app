package com.app.challenge.infrastructure.rest;

import com.app.challenge.application.handler.CreateUserHandler;
import com.app.challenge.config.TestSecurityConfig;
import com.app.challenge.domain.model.dto.request.CreateUserRequest;
import com.app.challenge.domain.model.dto.request.PhoneRequest;
import com.app.challenge.domain.model.dto.response.UserResponse;
import com.app.challenge.infrastructure.config.util.PasswordRegexProperties;
import com.app.challenge.infrastructure.rest.advice.EmailAlreadyExistsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(UserController.class)
@Import({TestSecurityConfig.class, PasswordRegexProperties.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateUserHandler createUserHandler;

    @Test
    @DisplayName("Debería crear un usuario exitosamente")
    void shouldCreateUserSuccessfully() throws Exception {
        // Arrange
        var request = new CreateUserRequest("Juan", "juan@mail.com", "HunterApp2",
            List.of(new PhoneRequest("1234567", "1", "57")));

        var expectedResponse = new UserResponse(
            UUID.randomUUID(),
            "Juan",
            "juan@mail.com",
            "jwt-token-sample",
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            request.phones(),
            true
        );

        when(createUserHandler.handle(any(CreateUserRequest.class)))
            .thenReturn(expectedResponse);

        // Act + Assert
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.email").value("juan@mail.com"))
            .andExpect(jsonPath("$.name").value("Juan"))
            .andExpect(jsonPath("$.token").value("jwt-token-sample"))
            .andExpect(jsonPath("$.isActive").value(true));
    }

    @Test
    @DisplayName("Debe retornar error si el email es inválido")
    void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        var request = new CreateUserRequest("Juan", "correo-malo", "HunterApp2", List.of());

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.mensaje").exists());
    }

    @Test
    @DisplayName("Debe retornar error si la contraseña no cumple con la expresión regular")
    void shouldReturnBadRequestWhenPasswordIsWeak() throws Exception {
        var request = new CreateUserRequest("Juan", "juan@mail.com", "1234", List.of());

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.mensaje").value("La contraseña debe tener al menos 8 caracteres, incluyendo una mayúscula, una minúscula y un número"));
    }

    @Test
    @DisplayName("Debe retornar error si el correo ya está registrado")
    void shouldReturnConflictIfEmailExists() throws Exception {
        var request = new CreateUserRequest("Juan", "juan@mail.com", "HunterApp2", List.of());

        when(createUserHandler.handle(any(CreateUserRequest.class)))
            .thenThrow(new EmailAlreadyExistsException("El correo ya registrado"));

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.mensaje").value("El correo ya registrado"));
    }

    @Test
    @DisplayName("Debe retornar error 500 si ocurre una excepción no controlada")
    void shouldReturnInternalServerErrorOnUnhandledException() throws Exception {
        var request = new CreateUserRequest("Juan", "juan@mail.com", "HunterApp2", List.of());

        when(createUserHandler.handle(any(CreateUserRequest.class)))
            .thenThrow(new RuntimeException("Fallo inesperado"));

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.mensaje").value("Error interno del servidor"));
    }

}
