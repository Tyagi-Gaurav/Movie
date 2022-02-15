package com.gt.scr.user.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    final static ObjectMapper mapper = new ObjectMapper();

    public static String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
