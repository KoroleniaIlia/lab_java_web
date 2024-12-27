package com.example.lab_java_web.web;

import com.example.lab_java_web.AbstractIt;
import com.example.lab_java_web.domain.CosmoCatDetails;
import com.example.lab_java_web.dto.cat.CosmoCatsDTO;
import com.example.lab_java_web.featuretoggle.FeatureToggleExtension;
import com.example.lab_java_web.featuretoggle.FeatureToggles;
import com.example.lab_java_web.featuretoggle.annotation.DisabledFeatureToggle;
import com.example.lab_java_web.featuretoggle.annotation.EnabledFeatureToggle;
import com.example.lab_java_web.repository.CosmoCatRepository;
import com.example.lab_java_web.repository.entity.CosmoCatEntity;
import com.example.lab_java_web.repository.mapper.CosmoCatRepositoryMapper;
import com.example.lab_java_web.service.CosmoCatService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CosmicCat Controller IT")
@ExtendWith(FeatureToggleExtension.class)
public class CosmoCatControllerIT extends AbstractIt {
    @Autowired
    MockMvc mockMvc;
    @SpyBean
    private CosmoCatService cosmoCatService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CosmoCatRepositoryMapper cosmoCatRepositoryMapper;
    @Autowired
    private CosmoCatRepository cosmoCatRepository;
    @BeforeEach
    void setUp() {
        reset(cosmoCatService);
        cosmoCatRepository.deleteAll();
    }
    @Test
    @SneakyThrows
    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldReturnAllCosmoCats() {
        mockMvc.perform(get("/api/v1/cosmo-cats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
    @DisabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldReturnAllCosmoCatsDisabled() {
        mockMvc.perform(get("/api/v1/cosmo-cats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    @SneakyThrows
    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldReturnCosmoCatByCosmoCatIdEnabled() {
        CosmoCatEntity cosmoCatEntity = createCosmoCat();
        mockMvc.perform(get("/api/v1/cosmo-cats/{cosmoCatId}", cosmoCatEntity.getCosmoCatId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
    @DisabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldReturnCosmoCatByCosmoCatIdDisabled() {
        CosmoCatEntity cosmoCatEntity = createCosmoCat();
        mockMvc.perform(get("/api/v1/cosmo-cats/{cosmoCatId}", cosmoCatEntity.getCosmoCatId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    @SneakyThrows
    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldReturnCosmoCatByCosmoCatIdNotFound() {
        mockMvc.perform(get("/api/v1/cosmo-cats/{cosmoCatId}", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    @SneakyThrows
    void shouldCreateCosmoCat() {
        CosmoCatsDTO cosmoCatDto = new CosmoCatsDTO().toBuilder()
                .catCosmoName("Create")
                .realName("Create Test")
                .catEmail("createtest@catmail.com")
                .build();
        mockMvc.perform(post("/api/v1/cosmo-cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cosmoCatDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @SneakyThrows
    void shouldCreateCosmoCatValidationError() {
        CosmoCatsDTO cosmoCatDto = new CosmoCatsDTO().toBuilder()
                .catCosmoName("Create")
                .realName("Create Test")
                .catEmail("createtest.com")
                .build();
        mockMvc.perform(post("/api/v1/cosmo-cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cosmoCatDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @SneakyThrows
    void shouldUpdateCosmoCat() {
        CosmoCatEntity cosmoCatEntity = createCosmoCat();
        CosmoCatsDTO cosmoCatDto = new CosmoCatsDTO().toBuilder()
                .catCosmoName("Update")
                .realName("Update Test")
                .catEmail("updatetetest@catmail.com")
                .build();
        mockMvc.perform(put("/api/v1/cosmo-cats/{cosmoCatId}", cosmoCatEntity.getCosmoCatId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cosmoCatDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @SneakyThrows
    void shouldUpdateCosmoCatValidationError() {
        CosmoCatEntity cosmoCatEntity = createCosmoCat();
        CosmoCatsDTO cosmoCatDto = new CosmoCatsDTO().toBuilder()
                .catCosmoName("Update")
                .realName("Update Test")
                .catEmail("updatetetest.com")
                .build();
        mockMvc.perform(put("/api/v1/cosmo-cats/{cosmoCatId}", cosmoCatEntity.getCosmoCatId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cosmoCatDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @SneakyThrows
    void shouldUpdateCosmoCatNotFound() {
        CosmoCatsDTO cosmoCatDto = new CosmoCatsDTO().toBuilder()
                .cosmoCatId(UUID.randomUUID())
                .catCosmoName("Update")
                .realName("Update Test")
                .catEmail("updatetetest@catemail.com")
                .build();
        mockMvc.perform(put("/api/v1/cosmo-cats/{cosmoCatId}", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cosmoCatDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @SneakyThrows
    void shouldDeleteCosmoCat() {
        CosmoCatEntity cosmoCatEntity = createCosmoCat();
        mockMvc.perform(delete("/api/v1/cosmo-cats/{cosmoCatId}", cosmoCatEntity.getCosmoCatId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/cosmo-cats/{cosmoCatId}", cosmoCatEntity.getCosmoCatId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    private CosmoCatEntity createCosmoCat() {
        return cosmoCatRepositoryMapper.toCosmoCatEntity(cosmoCatService.saveCosmoCat(CosmoCatDetails.builder()
                .cosmoCatId(UUID.randomUUID())
                .catCosmoName("TestCat")
                .realName("Test Cat")
                .catEmail("TestMail@catmail.com")
                .build()));
    }
}