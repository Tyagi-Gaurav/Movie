package com.gt.scr.crack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CheckPermutationTest {

    private final CheckPermutation checkPermutation = new CheckPermutation();

    @Test
    void check1() {
        assertThat(checkPermutation.check1("abc", "bac")).isTrue();
        assertThat(checkPermutation.check1("abc", "bac ")).isFalse();
        assertThat(checkPermutation.check1("god", "dog")).isTrue();
    }

    @Test
    void check2() {
        assertThat(checkPermutation.check2("abc", "bac")).isTrue();
        assertThat(checkPermutation.check2("abc", "bac ")).isFalse();
        assertThat(checkPermutation.check2("god", "dog")).isTrue();
    }
}