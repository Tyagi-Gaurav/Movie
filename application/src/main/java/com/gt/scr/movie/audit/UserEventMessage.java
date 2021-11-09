package com.gt.scr.movie.audit;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className")
public interface UserEventMessage {
    UUID eventId();
    UUID ownerUser();
    UUID originatorUser();
    long creationTimestamp();
    EventType eventType();
}
