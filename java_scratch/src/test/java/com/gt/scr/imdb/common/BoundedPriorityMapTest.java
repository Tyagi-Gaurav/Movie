package com.gt.scr.imdb.common;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BoundedPriorityMapTest {
    @Test
    void shouldRemoveExtraWhenFull() throws InterruptedException {
        BoundedPriorityMap<Integer, List<Integer>> map = new BoundedPriorityMap<>(3);

        map.putWithEviction(1, List.of(1));
        Thread.sleep(10);
        map.putWithEviction(3, List.of(3));
        Thread.sleep(10);
        map.putWithEviction(5, List.of(5));
        Thread.sleep(10);
        map.putWithEviction(9, List.of(6));

        final Map.Entry<Integer, List<Integer>> lastEvictedElement = map.getLastEvictedElement();
        assertThat(lastEvictedElement.getKey()).isEqualTo(1);
        assertThat(lastEvictedElement.getValue()).isEqualTo(List.of(1));
    }

    @Test
    void shouldUpdateAccessTimeWhenPutAgainAndNotEvict() throws InterruptedException {
        BoundedPriorityMap<Integer, List<Integer>> map = new BoundedPriorityMap<>(3);

        map.putWithEviction(1, List.of(1));
        Thread.sleep(10);
        map.putWithEviction(1, List.of(3));
        Thread.sleep(10);
        map.putWithEviction(5, List.of(5));
        Thread.sleep(10);
        map.putWithEviction(1, List.of(6, 7));

        final Map.Entry<Integer, List<Integer>> lastEvictedElement = map.getLastEvictedElement();
        assertThat(lastEvictedElement).isNull();
    }
}