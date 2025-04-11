package com.app.challenge.domain.model.dto.request;

import com.app.challenge.infrastructure.config.util.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(description = "Request para crear un nuevo usuario")
public record CreateUserRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(example = "Juan Rodriguez")
    String name,
    @Email(message = "Formato de correo inválido")
    @Schema(example = "juan@rodriguez.org")
    String email,
    @ValidPassword
    @Schema(example = "Hunter2")
    String password,
    @Valid
    List<PhoneRequest> phones
) {
}
