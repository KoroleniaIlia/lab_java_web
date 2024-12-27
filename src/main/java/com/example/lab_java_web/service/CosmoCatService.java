package com.example.lab_java_web.service;


import com.example.lab_java_web.domain.CosmoCatDetails;
import java.util.UUID;
import java.util.List;
public interface CosmoCatService {
    List<CosmoCatDetails> getAllCosmoCats();
    CosmoCatDetails getCosmoCatByCosmoCatId(UUID cosmoCatId);
    CosmoCatDetails saveCosmoCat(CosmoCatDetails cosmoCatDetails);
    CosmoCatDetails saveCosmoCat(UUID catReference, CosmoCatDetails cosmoCatDetails);
    void deleteCosmoCat(UUID cosmoCatId);
}