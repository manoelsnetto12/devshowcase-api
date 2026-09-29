package com.devshowcase.api.controller;

import com.devshowcase.api.dto.TechnologyRequestDTO;
import com.devshowcase.api.dto.TechnologyResponseDTO;
import com.devshowcase.api.model.Technology;
import com.devshowcase.api.repository.TechnologyRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technologies")
public class TechnologyController {

    private final TechnologyRepository technologyRepository;

    public TechnologyController(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @PostMapping
    public ResponseEntity<TechnologyResponseDTO> create(@Valid @RequestBody TechnologyRequestDTO dto) {
        Technology tech = new Technology(dto.name());
        Technology saved = technologyRepository.save(tech);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TechnologyResponseDTO(saved.getId(), saved.getName()));
    }

    @GetMapping
    public ResponseEntity<List<TechnologyResponseDTO>> findAll() {
        List<TechnologyResponseDTO> list = technologyRepository.findAll().stream()
                .map(t -> new TechnologyResponseDTO(t.getId(), t.getName()))
                .toList();
        return ResponseEntity.ok(list);
    }
}
