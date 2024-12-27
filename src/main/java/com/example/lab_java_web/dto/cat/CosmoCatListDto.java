package com.example.lab_java_web.dto.cat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CosmoCatListDto {
    private List<CosmoCatsDTO> cosmoCats;
}
