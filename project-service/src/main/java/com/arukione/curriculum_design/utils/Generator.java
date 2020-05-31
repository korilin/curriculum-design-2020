package com.arukione.curriculum_design.utils;

import java.util.UUID;

public class Generator {

    public static String generateAccessToken() {
        return UUID.randomUUID().toString();
    }



}
