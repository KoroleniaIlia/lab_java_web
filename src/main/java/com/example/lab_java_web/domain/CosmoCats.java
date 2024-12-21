package com.example.lab_java_web.domain;

import lombok.Builder;
import lombok.Value;
@Value
@Builder(toBuilder = true)
public class CosmoCats {
    Long id;
    String catCosmoName;
    String realName;
    String catEmail;
}