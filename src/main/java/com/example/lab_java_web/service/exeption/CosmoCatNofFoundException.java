package com.example.lab_java_web.service.exeption;

import java.util.UUID;
public class CosmoCatNofFoundException extends RuntimeException {
    private static final String COSMo_CAT_NOT_FOUND_MESSAGE = "Cosmo cat with id %s not found";
    public CosmoCatNofFoundException(UUID id) {
        super(String.format(COSMo_CAT_NOT_FOUND_MESSAGE, id));
    }
}
