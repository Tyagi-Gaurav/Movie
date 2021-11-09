package com.gt.scr.movie.audit;

import java.math.BigDecimal;
import java.util.UUID;

public record MovieCreateEvent(
        UUID eventId,
        UUID userId,
        EventType eventType,
        long creationTimestamp,
        String name,
        int yearProduced,
        BigDecimal rating) implements UserEventMessage {

    public MovieCreateEvent(UUID userId, String name, int yearProduced, BigDecimal rating) {
        this(UUID.randomUUID(),
                userId,
                EventType.MOVIE_CREATE,
                System.nanoTime(),
                name,
                yearProduced,
                rating);
    }
}
