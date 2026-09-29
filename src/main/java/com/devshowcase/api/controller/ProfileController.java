package com.devshowcase.api.controller;

import com.devshowcase.api.dto.ProfileRequestDTO;
import com.devshowcase.api.dto.ProfileResponseDTO;
import com.devshowcase.api.model.Profile;
import com.devshowcase.api.repository.ProfileRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @PostMapping
    public ResponseEntity<ProfileResponseDTO> create(@Valid @RequestBody ProfileRequestDTO dto) {
        Profile profile = new Profile(dto.name(), dto.email(), dto.bio());
        Profile saved = profileRepository.save(profile);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ProfileResponseDTO(saved.getId(), saved.getName(), saved.getEmail(), saved.getBio()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> findById(@PathVariable Long id) {
        return profileRepository.findById(id)
                .map(p -> ResponseEntity.ok(new ProfileResponseDTO(p.getId(), p.getName(), p.getEmail(), p.getBio())))
                .orElse(ResponseEntity.notFound().build());
    }
}
