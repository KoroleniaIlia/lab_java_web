package com.example.lab_java_web.service.exeption;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class ParamsViolationException {
    String fieldName;
    String reason;
}