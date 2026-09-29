package com.devshowcase.api.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record ProjectRequestDTO(
    @NotBlank(message = "O título do projeto é obrigatório") String title,
    @NotBlank(message = "A descrição é obrigatória") String description,
    String repositoryUrl,
    Long profileId,
    List<Long> technologyIds
) {}
