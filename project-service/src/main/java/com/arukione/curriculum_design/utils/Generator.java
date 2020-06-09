package com.arukione.curriculum_design.utils;

import java.util.Date;
import java.util.UUID;

public class Generator {

    public static String generateAccessToken() {
        return UUID.randomUUID().toString();
    }

    public static String generateTopicID() {
        long time = new Date().getTime()/10;
        return String.valueOf(time);
    }

}
