package com.gt.scr.imdb.common;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BoundedPriorityMapTest {
    @Test
    void shouldRemoveExtraWhenFull()  {
        BoundedPriorityMap<Integer, List<Integer>> map = new BoundedPriorityMap<>(3);

        map.putWithEviction(1, List.of(1));
        map.putWithEviction(3, List.of(3));
        map.putWithEviction(5, List.of(5));
        map.putWithEviction(9, List.of(6));

        Map.Entry<Integer, List<Integer>> lastEvictedElement = map.getLastEvictedElement();
        assertThat(lastEvictedElement.getKey()).isEqualTo(1);
        assertThat(lastEvictedElement.getValue()).isEqualTo(List.of(1));
    }

    @Test
    void shouldUpdateAccessTimeWhenPutAgainAndNotEvict() {
        BoundedPriorityMap<Integer, List<Integer>> map = new BoundedPriorityMap<>(3);

        map.putWithEviction(1, List.of(1));
        map.putWithEviction(2, List.of(3));
        map.putWithEviction(5, List.of(5));
        map.putWithEviction(4, List.of(6, 7));
        Map.Entry<Integer, List<Integer>> lastEvictedElement = map.getLastEvictedElement();
        assertThat(lastEvictedElement.getKey()).isEqualTo(1);
        map.putWithEviction(2, List.of(6, 7));

        lastEvictedElement = map.getLastEvictedElement();
        assertThat(lastEvictedElement.getKey()).isEqualTo(1);
    }
}