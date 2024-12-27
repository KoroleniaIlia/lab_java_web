package com.example.lab_java_web.service.mapper;

import com.example.lab_java_web.domain.CosmoCatDetails;
import com.example.lab_java_web.dto.cat.CosmoCatsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CosmoCatDetailsMapperTest {
    private CosmoCatServiceMapper cosmoCatServiceMapper;

    @BeforeEach
    public void setUp() {
        cosmoCatServiceMapper = Mappers.getMapper(CosmoCatServiceMapper.class);
    }

    @Test
    void shouldMapCosmoCatToCosmoCatDto() {
        CosmoCatDetails cosmoCatDetails = CosmoCatDetails.builder()
                .id(1L)
                .cosmoCatId(UUID.fromString("FirstCatTest"))
                .realName("RealTest Cat")
                .catEmail("test@catmail.com")
                .build();
        CosmoCatsDTO cosmoCatDTO = cosmoCatServiceMapper.toCosmoCatDTO(cosmoCatDetails);
        assertNotNull(cosmoCatDTO);
        assertEquals(cosmoCatDTO.getCatCosmoName(), cosmoCatDetails.getCosmoCatId());
        assertEquals(cosmoCatDTO.getRealName(), cosmoCatDetails.getRealName());
        assertEquals(cosmoCatDTO.getCatEmail(), cosmoCatDetails.getCatEmail());
    }

    @Test
    void shouldMapCosmoCatDTOToCosmoCat() {
        CosmoCatsDTO cosmoCatDTO = CosmoCatsDTO.builder()
                .catCosmoName("FirstCatTest")
                .realName("RealTest Cat")
                .catEmail("test@catmail.com")
                .build();
        CosmoCatDetails cosmoCatDetails = cosmoCatServiceMapper.toCosmoCatDetails(cosmoCatDTO);
        assertNotNull(cosmoCatDetails);
        assertEquals(cosmoCatDetails.getCosmoCatId(), cosmoCatDTO.getCatCosmoName());
        assertEquals(cosmoCatDetails.getRealName(), cosmoCatDTO.getRealName());
        assertEquals(cosmoCatDetails.getCatEmail(), cosmoCatDTO.getCatEmail());
    }
}