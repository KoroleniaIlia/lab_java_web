package com.example.lab_java_web.dto.cat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CosmoCatsDTO {
    private UUID cosmoCatId;
    @Schema(description = "Name of the cosmocat")
    @NotBlank(message = "Cat cosmo name is mandatory")
    @Size(max = 25)
    private String catCosmoName;
    @Schema(description = "Real name of the cat")
    @Size(max = 64)
    @NotBlank(message = "Cat's real name is mandatory")
    private String realName;
    @Schema(description = "Email of the cat")
    @NotBlank(message = "Catemail is mandatory")
    @Email(message = "Cat's email must be valid")
    private String catEmail;
    private List<UUID> orders;
}