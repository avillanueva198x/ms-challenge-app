package com.app.challenge.infrastructure.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Respuesta del usuario creado")
public record UserResponse(
    UUID id,
    LocalDateTime created,
    LocalDateTime modified,
    @JsonProperty("last_login")
    LocalDateTime lastLogin,
    String token,
    boolean isActive,
    String name,
    String email,
    List<PhoneRequest> phones
) {
}
