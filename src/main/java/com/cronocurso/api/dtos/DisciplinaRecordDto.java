package com.cronocurso.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DisciplinaRecordDto(@NotNull @NotBlank String nome) {}
