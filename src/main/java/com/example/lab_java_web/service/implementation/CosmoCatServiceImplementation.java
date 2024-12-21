package com.example.lab_java_web.service.implementation;


import com.example.lab_java_web.domain.CosmoCats;
import com.example.lab_java_web.service.CosmoCatService;
import org.springframework.stereotype.Service;

import java.util.List;

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