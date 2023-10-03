package com.cronocurso.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MateriaRecordDto(
    @NotNull
    @NotBlank
    String nome
) {}
