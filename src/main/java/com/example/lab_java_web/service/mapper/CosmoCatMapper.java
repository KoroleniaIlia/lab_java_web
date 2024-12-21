package com.example.lab_java_web.service.mapper;

import com.example.lab_java_web.domain.CosmoCats;
import com.example.lab_java_web.dto.CosmoCatsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CosmoCatMapper {
    @Mapping(source = "catCosmoName", target = "catCosmoName")
    @Mapping(source = "realName", target = "realName")
    @Mapping(source = "catEmail", target = "catEmail")
    CosmoCatsDTO toCosmoCatsDTO(CosmoCats cosmoCats);

    List<CosmoCatsDTO> toCosmoCatsDTO(List<CosmoCats> cosmoCats);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "catCosmoName", target = "catCosmoName")
    @Mapping(source = "realName", target = "realName")
    @Mapping(source = "catEmail", target = "catEmail")
    CosmoCats toCosmoCats(CosmoCatsDTO cosmoCatsDTO);
}