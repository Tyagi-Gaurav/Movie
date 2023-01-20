package com.gt.scr.crack;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CheckUniqueTest {
    private final CheckUnique checkUnique = new CheckUnique();

    @Test
    void isUnique1() {
        assertThat(checkUnique.hasUniqueCharacters1("abc")).isTrue();
        assertThat(checkUnique.hasUniqueCharacters1("aba")).isFalse();
        assertThat(checkUnique.hasUniqueCharacters1("abcd")).isTrue();
    }

    @Test
    void isUnique2() {
        assertThat(checkUnique.hasUniqueCharacters2("abc")).isTrue();
        assertThat(checkUnique.hasUniqueCharacters2("aba")).isFalse();
        assertThat(checkUnique.hasUniqueCharacters2("abcd")).isTrue();
    }

    @Test
    void isUnique3() {
        assertThat(checkUnique.hasUniqueCharacters3("abc")).isTrue();
        assertThat(checkUnique.hasUniqueCharacters3("aba")).isFalse();
        assertThat(checkUnique.hasUniqueCharacters3("abcd")).isTrue();
    }

    @Test
    void isUnique4() {
        assertThat(checkUnique.hasUniqueCharacters4("abc")).isTrue();
        assertThat(checkUnique.hasUniqueCharacters4("aba")).isFalse();
        assertThat(checkUnique.hasUniqueCharacters4("abcd")).isTrue();
    }
}