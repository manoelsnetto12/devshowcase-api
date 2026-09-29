package com.devshowcase.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfileRequestDTO(
    @NotBlank(message = "O nome é obrigatório") String name,
    @NotBlank(message = "O e-mail é obrigatório") @Email(message = "E-mail inválido") String email,
    String bio
) {}
