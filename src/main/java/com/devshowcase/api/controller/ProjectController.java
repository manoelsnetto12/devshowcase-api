package com.devshowcase.api.controller;

import com.devshowcase.api.dto.ProjectRequestDTO;
import com.devshowcase.api.dto.ProjectResponseDTO;
import com.devshowcase.api.model.Profile;
import com.devshowcase.api.model.Project;
import com.devshowcase.api.model.Technology;
import com.devshowcase.api.repository.ProfileRepository;
import com.devshowcase.api.repository.ProjectRepository;
import com.devshowcase.api.repository.TechnologyRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProfileRepository profileRepository;
    private final TechnologyRepository technologyRepository;

    public ProjectController(ProjectRepository projectRepository, 
                             ProfileRepository profileRepository, 
                             TechnologyRepository technologyRepository) {
        this.projectRepository = projectRepository;
        this.profileRepository = profileRepository;
        this.technologyRepository = technologyRepository;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> create(@Valid @RequestBody ProjectRequestDTO dto) {
        Profile profile = profileRepository.findById(dto.profileId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        Project project = new Project();
        project.setTitle(dto.title());
        project.setDescription(dto.description());
        project.setRepositoryUrl(dto.repositoryUrl());
        project.setProfile(profile);

        if (dto.technologyIds() != null && !dto.technologyIds().isEmpty()) {
            List<Technology> techs = technologyRepository.findAllById(dto.technologyIds());
            project.setTechnologies(new HashSet<>(techs));
        }

        Project saved = projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ProjectResponseDTO(saved.getId(), saved.getTitle(), saved.getDescription(), saved.getRepositoryUrl(), saved.getProfile().getId()));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> findAll() {
        List<ProjectResponseDTO> list = projectRepository.findAll().stream()
                .map(p -> new ProjectResponseDTO(p.getId(), p.getTitle(), p.getDescription(), p.getRepositoryUrl(), p.getProfile().getId()))
                .toList();
        return ResponseEntity.ok(list);
    }
}
