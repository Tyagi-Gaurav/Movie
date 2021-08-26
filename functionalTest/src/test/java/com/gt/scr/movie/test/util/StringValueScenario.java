package com.gt.scr.movie.test.util;

import org.apache.commons.lang3.RandomStringUtils;

public class StringValueScenario {

    public static String actualOrRandom(String stringValue, int letterCount) {
        if (stringValue == null || "<random>".equals(stringValue)) {
            return RandomStringUtils.randomAlphabetic(letterCount);
        }
        return stringValue;
    }
}
