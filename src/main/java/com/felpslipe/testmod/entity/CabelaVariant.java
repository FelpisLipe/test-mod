package com.felpslipe.testmod.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum CabelaVariant {
    NORMAL(0),
    CRY(1);

    private static final CabelaVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(CabelaVariant::getId)).toArray(CabelaVariant[]::new);
    private final int id;

    CabelaVariant(int id) {
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public static CabelaVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
