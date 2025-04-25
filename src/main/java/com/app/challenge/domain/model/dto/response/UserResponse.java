package com.app.challenge.domain.model.dto.response;

import com.app.challenge.domain.model.dto.request.PhoneRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta del usuario creado")
public class UserResponse {

    UUID id;
    String name;
    String email;
    String token;
    LocalDateTime created;
    LocalDateTime modified;
    LocalDateTime lastLogin;
    List<PhoneRequest> phones;
    boolean isActive;
}
