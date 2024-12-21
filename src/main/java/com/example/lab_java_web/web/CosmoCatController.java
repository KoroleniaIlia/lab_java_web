package com.example.lab_java_web.web;

import com.example.lab_java_web.dto.CosmoCatsDTO;
import com.example.lab_java_web.featuretoggle.FeatureToggles;
import com.example.lab_java_web.featuretoggle.annotation.FeatureToggle;
import com.example.lab_java_web.service.CosmoCatService;
import com.example.lab_java_web.service.mapper.CosmoCatMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/cosmo-cats")
public class CosmoCatController {
    private final CosmoCatService cosmoCatService;
    private final CosmoCatMapper cosmoCatMapper;

    public CosmoCatController(CosmoCatService cosmicCatService, CosmoCatMapper cosmoCatMapper) {
        this.cosmoCatService = cosmicCatService;
        this.cosmoCatMapper = cosmoCatMapper;
    }

    @FeatureToggle(FeatureToggles.COSMO_CATS)
    @GetMapping
    public ResponseEntity<List<CosmoCatsDTO>> getCosmicCats() {
        return ResponseEntity.ok(cosmoCatMapper.toCosmoCatsDTO(cosmoCatService.getAllCosmicCats()));
    }
}