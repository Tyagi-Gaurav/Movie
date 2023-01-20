package com.gt.scr.crack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class URLIfyStringTest {
    private final URLIfyString urlIfyString = new URLIfyString();

    @Test
    void checkString() {
        assertThat(urlIfyString.doWork("Mr John Smith    ", 13))
                .isNotNull()
                .isEqualTo("Mr%20John%20Smith");
    }
}