package com.example.lab_java_web.web;

import com.example.lab_java_web.featuretoggle.FeatureToggleExtension;
import com.example.lab_java_web.featuretoggle.FeatureToggles;
import com.example.lab_java_web.featuretoggle.annotation.DisabledFeatureToggle;
import com.example.lab_java_web.featuretoggle.annotation.EnabledFeatureToggle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("CosmoCat Controller IT")
@ExtendWith(FeatureToggleExtension.class)
public class CosmoCatControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldReturnAllCosmoCatsDisabled() throws Exception {
        mockMvc.perform(get("/api/v1/cosmo-cats")).andExpect(status().isNotFound());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldReturnAllCosmoCats() throws Exception {
        mockMvc.perform(get("/api/v1/cosmo-cats")).andExpect(status().isOk());
    }
}