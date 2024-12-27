package com.example.lab_java_web.service.implementation;


import com.example.lab_java_web.domain.CosmoCatDetails;
import com.example.lab_java_web.repository.CosmoCatRepository;
import com.example.lab_java_web.repository.entity.CosmoCatEntity;
import com.example.lab_java_web.repository.mapper.CosmoCatRepositoryMapper;
import com.example.lab_java_web.service.CosmoCatService;
import com.example.lab_java_web.service.exeption.CosmoCatNofFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CosmoCatServiceImplementation implements CosmoCatService {
    private final List<CosmoCats> cosmoCats = createCosmoCats();

    @Override
    public List<CosmoCats> getAllCosmoCats() {
        return cosmoCats;
    }

    private List<CosmoCats> createCosmoCats() {
        return List.of(
                CosmoCats.builder()
                        .id(1L)
                        .catCosmoName("Luntic")
                        .realName("Lunt Marsovych")
                        .catEmail("luntic54@gmail.com")
                        .build(),
                CosmoCats.builder()
                        .id(2L)
                        .catCosmoName("Sanny")
                        .realName("San Perto")
                        .catEmail("sanny23@gmail.com")
                        .build(),
                CosmoCats.builder()
                        .id(3L)
                        .catCosmoName("Pluton")
                        .realName("Pluton Aleksandrovich")
                        .catEmail("pluton@gmail.com")
                        .build()
        );
    }
}