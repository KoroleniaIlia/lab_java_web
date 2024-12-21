package com.example.lab_java_web.service.mapper;

import com.example.lab_java_web.domain.CosmoCats;
import com.example.lab_java_web.dto.CosmoCatsDTO;
import com.example.lab_java_web.service.mapper.CosmoCatMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.jupiter.api.Assertions.*;
public class CosmoCatMapperTest {
    private CosmoCatMapper cosmoCatMapper;
    @BeforeEach
    public void setUp() {
        cosmoCatMapper = Mappers.getMapper(CosmoCatMapper.class);
    }
    @Test
    void shouldMapCosmoCatToCosmoCatsDto() {
        CosmoCats cosmoCat = CosmoCats.builder()
                .id(1L)
                .catCosmoName("FirstCatTest")
                .realName("First TestCat")
                .catEmail("test@catmail.com")
                .build();
        CosmoCatsDTO cosmoCatsDTO = cosmoCatMapper.toCosmoCatsDTO(cosmoCat);
        assertNotNull(cosmoCatsDTO);
        assertEquals(cosmoCatsDTO.getCatCosmoName(), cosmoCat.getCatCosmoName());
        assertEquals(cosmoCatsDTO.getRealName(), cosmoCat.getRealName());
        assertEquals(cosmoCatsDTO.getCatEmail(), cosmoCat.getCatEmail());
    }
    @Test
    void shouldMapCosmoCatsDTOToCosmoCat() {
        CosmoCatsDTO cosmoCatsDTO = CosmoCatsDTO.builder()
                .catCosmoName("FirstCatTest")
                .realName("First TestCat")
                .catEmail("test@catmail.com")
                .build();
        CosmoCats cosmoCat = cosmoCatMapper.toCosmoCats(cosmoCatsDTO);
        assertNotNull(cosmoCat);
        assertEquals(cosmoCat.getCatCosmoName(), cosmoCatsDTO.getCatCosmoName());
        assertEquals(cosmoCat.getRealName(), cosmoCatsDTO.getRealName());
        assertEquals(cosmoCat.getCatEmail(), cosmoCatsDTO.getCatEmail());
    }
}
