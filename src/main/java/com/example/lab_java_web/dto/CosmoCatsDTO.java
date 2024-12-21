package com.example.lab_java_web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class CosmoCatsDTO {
    @Schema(description = "Name of the cosmocat")
    @NotBlank(message = "Cat cosmo name is mandatory")
    @Size(max = 25)
    String catCosmoName;
    @Schema(description = "Real name of the cat")
    @Size(max = 64)
    @NotBlank(message = "Cat's real name is mandatory")
    String realName;
    @Schema(description = "Email of the cat")
    @Email(message = "Cat's email must be valid")
    String catEmail;
}