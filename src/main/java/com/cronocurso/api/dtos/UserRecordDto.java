package com.cronocurso.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRecordDto(
        @NotNull @NotBlank String email, @NotNull String name,
        @NotNull @NotBlank String password
) {

}
