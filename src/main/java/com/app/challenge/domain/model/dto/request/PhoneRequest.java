package com.app.challenge.domain.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Teléfono del usuario")
public record PhoneRequest(
    @NotBlank
    @Size(min = 7, max = 15, message = "El número debe tener entre 7 y 15 dígitos")
    @Schema(example = "123456789")
    String number,
    @NotBlank
    @Size(min = 1, max = 10, message = "El código de ciudad debe tener entre 1 y 10 dígitos")
    @Schema(example = "51")
    String citycode,
    @NotBlank
    @Size(min = 1, max = 10, message = "El código de país debe tener entre 1 y 10 dígitos")
    @Schema(example = "5557")
    String contrycode
) { }
