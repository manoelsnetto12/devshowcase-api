package com.devshowcase.api.dto;

import jakarta.validation.constraints.NotBlank;

public record TechnologyRequestDTO(
    @NotBlank(message = "O nome da tecnologia é obrigatório") String name
) {}
