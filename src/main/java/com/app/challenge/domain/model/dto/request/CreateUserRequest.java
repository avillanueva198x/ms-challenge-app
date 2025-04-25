package com.app.challenge.domain.model.dto.request;

import com.app.challenge.infrastructure.config.util.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Schema(description = "Request para crear un nuevo usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(example = "Juan Rodriguez")
    private String name;

    @Email(message = "Formato de correo inválido")
    @Schema(example = "juan@rodriguez.org")
    private String email;

    @ValidPassword
    @Schema(example = "Hunter2")
    private String password;

    @Valid
    private List<PhoneRequest> phones;

}
