package com.gt.scr.movie.audit;

import java.math.BigDecimal;
import java.util.UUID;

public record MovieCreateEvent(
        UUID eventId,
        UUID ownerUser,
        UUID originatorUser,
        UUID movieId,
        EventType eventType,
        long creationTimestamp,
        String name,
        int yearProduced,
        BigDecimal rating) implements UserEventMessage {

    public MovieCreateEvent(UUID ownerUser,
                            UUID originatorUser,
                            UUID movieId,
                            String name,
                            int yearProduced,
                            BigDecimal rating) {
        this(UUID.randomUUID(),
                ownerUser,
                originatorUser,
                movieId,
                EventType.MOVIE_CREATE,
                System.nanoTime(),
                name,
                yearProduced,
                rating);
    }
}
