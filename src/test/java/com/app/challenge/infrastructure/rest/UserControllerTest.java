package com.app.challenge.infrastructure.rest;

import com.app.challenge.application.handler.CreateUserHandler;
import com.app.challenge.config.TestSecurityConfig;
import com.app.challenge.domain.model.dto.request.CreateUserRequest;
import com.app.challenge.domain.model.dto.request.PhoneRequest;
import com.app.challenge.domain.model.dto.response.UserResponse;
import com.app.challenge.infrastructure.config.util.PasswordRegexProperties;
import com.app.challenge.infrastructure.rest.advice.EmailAlreadyExistsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@WebMvcTest(UserController.class)
@Import({TestSecurityConfig.class, PasswordRegexProperties.class})
class UserControllerTest {

    private static final String USER_NAME = "Juan";
    private static final String EMAIL = "juan@mail.com";
    private static final String CONTRASENA = "HunterApp2";
    private static final String PHONE_NUMBER = "+5112334567";
    private static final String ENDPOINT = "/api/v1/users";
    private static final String JSON_MENSAJE = "$.mensaje";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CreateUserHandler createUserHandler;

    @Test
    @DisplayName("Debería crear un usuario exitosamente")
    void shouldCreateUserSuccessfully() {
        // Arrange
        var request = new CreateUserRequest(USER_NAME, EMAIL, CONTRASENA,
            List.of(new PhoneRequest(PHONE_NUMBER, "1", "57")));

        var expectedResponse = new UserResponse(
            UUID.randomUUID(),
            USER_NAME,
            EMAIL,
            "jwt-token-sample",
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            request.phones(),
            true
        );

        Assertions.assertDoesNotThrow(() -> {
            Mockito.when(this.createUserHandler.handle(Mockito.any(CreateUserRequest.class)))
                .thenReturn(expectedResponse);

            // Act + Assert
            this.mockMvc.perform(post(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.name").value(USER_NAME))
                .andExpect(jsonPath("$.token").value("jwt-token-sample"))
                .andExpect(jsonPath("$.isActive").value(true));
        });
    }

    @Test
    @DisplayName("Debe retornar error si el email es inválido")
    void shouldReturnBadRequestWhenEmailIsInvalid() {
        var request = new CreateUserRequest(USER_NAME, "correo-malo", CONTRASENA, List.of());

        Assertions.assertDoesNotThrow(() -> {
            this.mockMvc.perform(post(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JSON_MENSAJE).exists());
        });
    }

    @Test
    @DisplayName("Debe retornar error si la contraseña no cumple con la expresión regular")
    void shouldReturnBadRequestWhenPasswordIsWeak() {
        var request = new CreateUserRequest(USER_NAME, EMAIL, "1234", List.of());

        try {
            this.mockMvc.perform(post(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JSON_MENSAJE).value("La contraseña debe tener al menos 8 caracteres, incluyendo una mayúscula, una minúscula y un número"));
        } catch (Exception e) {
            Assertions.fail("Error al serializar el objeto a JSON: " + e.getMessage());
        }

    }

    @Test
    @DisplayName("Debe retornar error si el correo ya está registrado")
    void shouldReturnConflictIfEmailExists() {
        var request = new CreateUserRequest(USER_NAME, EMAIL, CONTRASENA, List.of());

        Mockito.when(this.createUserHandler.handle(Mockito.any(CreateUserRequest.class)))
            .thenThrow(new EmailAlreadyExistsException("El correo ya registrado"));

        try {
            this.mockMvc.perform(post(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath(JSON_MENSAJE).value("El correo ya registrado"));
        } catch (Exception e) {
            Assertions.fail("Error al serializar el objeto a JSON: " + e.getMessage());
        }

    }

    @Test
    @DisplayName("Debe retornar error 500 si ocurre una excepción no controlada")
    void shouldReturnInternalServerErrorOnUnhandledException() {
        var request = new CreateUserRequest(USER_NAME, EMAIL, CONTRASENA, List.of());

        // Configuramos el mock para que lance una excepción inesperada
        Mockito.when(this.createUserHandler.handle(Mockito.any(CreateUserRequest.class)))
            .thenThrow(new RuntimeException("Fallo inesperado"));

        try {
            // Realizamos la llamada a la API y verificamos que se devuelve el código 500
            this.mockMvc.perform(post(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())  // Verifica que el status sea 500
                .andExpect(jsonPath(JSON_MENSAJE).value("Error interno del servidor"));  // Verifica el mensaje de error
        } catch (Exception e) {
            Assertions.fail("Error al serializar el objeto a JSON: " + e.getMessage());
        }
    }

}
