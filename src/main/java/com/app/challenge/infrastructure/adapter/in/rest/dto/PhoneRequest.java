package com.app.challenge.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Teléfono del usuario")
public record PhoneRequest(
		@NotBlank @Schema(example = "1234567") String number,
		@NotBlank @Schema(example = "1") String citycode,
		@NotBlank @Schema(example = "57") String contrycode
) {}
