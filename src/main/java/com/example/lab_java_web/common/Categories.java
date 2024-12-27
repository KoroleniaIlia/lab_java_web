package com.example.lab_java_web.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Categories {

    COSMOMILK("CosmoMilk"),
    THREADS("Threads"),
    COSMOCAR("CosmoCar"),
    COSMOTOYS("CosmoToys"),
    GAMES("Games"),
    OTHER("Other");
    private final String displayName;
}