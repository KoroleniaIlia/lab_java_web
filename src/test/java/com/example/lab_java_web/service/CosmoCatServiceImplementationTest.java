package com.example.lab_java_web.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.lab_java_web.config.MappersTestConfiguration;
import com.example.lab_java_web.domain.CosmoCatDetails;
import com.example.lab_java_web.repository.CosmoCatRepository;
import com.example.lab_java_web.repository.entity.CosmoCatEntity;
import com.example.lab_java_web.repository.mapper.CosmoCatRepositoryMapper;
import com.example.lab_java_web.service.exeption.CosmoCatNofFoundException;
import com.example.lab_java_web.service.implementation.CosmoCatServiceImplementation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
@SpringBootTest(classes = {CosmoCatServiceImplementation.class})
@ExtendWith(MockitoExtension.class)
@Import({MappersTestConfiguration.class})
@DisplayName("CosmicCat Service Test")
class CosmoCatServiceImplementationTest {
    @MockBean
    private CosmoCatRepository cosmoCatRepository;
    @MockBean
    private CosmoCatRepositoryMapper cosmoCatRepositoryMapper;
    @Autowired
    private CosmoCatServiceImplementation cosmoCatService;
    @Test
    void testGetAllCosmoCats() {
        List<CosmoCatEntity> entities = List.of(new CosmoCatEntity());
        List<CosmoCatDetails> details = List.of(new CosmoCatDetails());
        when(cosmoCatRepository.findAll()).thenReturn(entities);
        when(cosmoCatRepositoryMapper.toCosmoCatDetails(entities)).thenReturn(details);
        List<CosmoCatDetails> result = cosmoCatService.getAllCosmoCats();
        assertEquals(details, result);
    }
    @Test
    void testGetCosmoCatByCosmoCatId() {
        UUID CosmoCatId = UUID.randomUUID();
        CosmoCatEntity entity = new CosmoCatEntity();
        CosmoCatDetails details = new CosmoCatDetails();
        when(cosmoCatRepository.findByNaturalId(CosmoCatId)).thenReturn(Optional.of(entity));
        when(cosmoCatRepositoryMapper.toCosmoCatDetails(entity)).thenReturn(details);
        CosmoCatDetails result = cosmoCatService.getCosmoCatByCosmoCatId(CosmoCatId);
        assertEquals(details, result);
    }
    @Test
    void testGetCosmoCatByCosmoCatIdNotFound() {
        UUID cosmoCatId = UUID.randomUUID();
        when(cosmoCatRepository.findByNaturalId(cosmoCatId)).thenReturn(Optional.empty());
        assertThrows(CosmoCatNofFoundException.class, () -> cosmoCatService.getCosmoCatByCosmoCatId(cosmoCatId));
    }
    @Test
    void testSaveCosmoCat() {
        CosmoCatDetails details = new CosmoCatDetails();
        CosmoCatEntity entity = new CosmoCatEntity();
        when(cosmoCatRepositoryMapper.toCosmoCatEntity(details)).thenReturn(entity);
        when(cosmoCatRepository.save(entity)).thenReturn(entity);
        when(cosmoCatRepositoryMapper.toCosmoCatDetails(entity)).thenReturn(details);
        CosmoCatDetails result = cosmoCatService.saveCosmoCat(details);
        assertEquals(details, result);
    }
    @Test
    void testSaveCosmoCatWithCosmoCatId() {
        UUID cosmoCatId = UUID.randomUUID();
        CosmoCatDetails details = new CosmoCatDetails();
        CosmoCatEntity entity = new CosmoCatEntity();
        when(cosmoCatRepository.findByNaturalId(cosmoCatId)).thenReturn(Optional.of(entity));
        when(cosmoCatRepository.save(entity)).thenReturn(entity);
        when(cosmoCatRepositoryMapper.toCosmoCatDetails(entity)).thenReturn(details);
        CosmoCatDetails result = cosmoCatService.saveCosmoCat(cosmoCatId, details);
        assertEquals(details, result);
    }
    @Test
    void testSaveCosmoCatWithCosmoCatIdNotFound() {
        UUID cosmoCatId = UUID.randomUUID();
        CosmoCatDetails details = new CosmoCatDetails();
        when(cosmoCatRepository.findByNaturalId(cosmoCatId)).thenReturn(Optional.empty());
        assertThrows(CosmoCatNofFoundException.class, () -> cosmoCatService.saveCosmoCat(cosmoCatId, details));
    }
    @Test
    void testDeleteCosmoCat() {
        UUID cosmoCatId = UUID.randomUUID();
        CosmoCatEntity entity = new CosmoCatEntity();
        when(cosmoCatRepository.findByNaturalId(cosmoCatId)).thenReturn(Optional.of(entity));
        cosmoCatService.deleteCosmoCat(cosmoCatId);
        verify(cosmoCatRepository).deleteByNaturalId(cosmoCatId);
    }
}