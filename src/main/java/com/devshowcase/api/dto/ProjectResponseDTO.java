package com.devshowcase.api.dto;

public record ProjectResponseDTO(
    Long id, 
    String title, 
    String description, 
    String repositoryUrl, 
    Long profileId
) {}
