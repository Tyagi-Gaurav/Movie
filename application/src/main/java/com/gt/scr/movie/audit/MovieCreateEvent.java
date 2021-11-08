package com.gt.scr.movie.audit;

import java.math.BigDecimal;
import java.util.UUID;

public record MovieCreateEvent(
        UUID eventId,
        EventType eventType,
        long creationTimestamp,
        String name,
        int yearProduced,
        BigDecimal rating) implements EventMessage {

    public MovieCreateEvent(String name, int yearProduced, BigDecimal rating) {
        this(UUID.randomUUID(),
                EventType.MOVIE_CREATE,
                System.nanoTime(),
                name,
                yearProduced,
                rating);
    }
}
