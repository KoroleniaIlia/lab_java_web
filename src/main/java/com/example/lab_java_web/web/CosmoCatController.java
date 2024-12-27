package com.example.lab_java_web.web;

import com.example.lab_java_web.dto.cat.CosmoCatsDTO;
import com.example.lab_java_web.featuretoggle.FeatureToggles;
import com.example.lab_java_web.featuretoggle.annotation.FeatureToggle;
import com.example.lab_java_web.service.CosmoCatService;
import com.example.lab_java_web.service.mapper.CosmoCatServiceMapper;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("api/v1/cosmo-cats")
public class CosmoCatController {
    private final CosmoCatService cosmoCatService;
    private final CosmoCatServiceMapper cosmoCatServiceMapper;

    public CosmoCatController(CosmoCatService cosmoCatService, CosmoCatServiceMapper cosmoCatServiceMapper) {
        this.cosmoCatService = cosmoCatService;
        this.cosmoCatServiceMapper = cosmoCatServiceMapper;
    }

    @FeatureToggle(FeatureToggles.COSMO_CATS)
    @GetMapping
    public ResponseEntity<List<CosmoCatsDTO>> getCosmoCats() {
        return ResponseEntity.ok(cosmoCatServiceMapper.toCosmoCatDTO(cosmoCatService.getAllCosmoCats()));
    }
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    @GetMapping("/{cosmoCatId}")
    public ResponseEntity<CosmoCatsDTO> getCosmoCat(@PathVariable UUID cosmoCatId) {
        return ResponseEntity.ok(cosmoCatServiceMapper.toCosmoCatDTO(cosmoCatService
                .getCosmoCatByCosmoCatId(cosmoCatId)));
    }
    @PostMapping
    public ResponseEntity<CosmoCatsDTO> createCosmoCat(@RequestBody @Valid CosmoCatsDTO cosmoCatDto) {
        return ResponseEntity.ok(cosmoCatServiceMapper.toCosmoCatDTO(cosmoCatService
                .saveCosmoCat(cosmoCatServiceMapper.toCosmoCatDetails(cosmoCatDto))));
    }
    @PutMapping("/{cosmoCatId}")
    public ResponseEntity<CosmoCatsDTO> updateCosmoCat(@PathVariable UUID cosmoCatId, @RequestBody @Valid CosmoCatsDTO cosmoCatDto) {
        return ResponseEntity.ok(cosmoCatServiceMapper.toCosmoCatDTO(cosmoCatService
                .saveCosmoCat(cosmoCatId, cosmoCatServiceMapper.toCosmoCatDetails(cosmoCatDto))));
    }
    @DeleteMapping("/{cosmoCatId}")
    public ResponseEntity<Void> deleteCosmoCat(@PathVariable UUID cosmoCatId) {
        cosmoCatService.deleteCosmoCat(cosmoCatId);
        return ResponseEntity.noContent().build();
    }
}