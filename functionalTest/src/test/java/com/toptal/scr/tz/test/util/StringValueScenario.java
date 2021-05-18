package com.toptal.scr.tz.test.util;

import org.apache.commons.lang3.RandomStringUtils;

public class StringValueScenario {

    public static String get(String stringValue, int letterCount) {
        if (stringValue == null || "<random>".equals(stringValue)) {
            return RandomStringUtils.randomAlphabetic(letterCount);
        }
        return stringValue;
    }
}
