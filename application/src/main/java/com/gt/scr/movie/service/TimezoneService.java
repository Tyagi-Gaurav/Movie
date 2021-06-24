package com.gt.scr.movie.service;

import com.gt.scr.movie.service.domain.UserTimezone;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Map;
import java.util.UUID;

public interface TimezoneService {
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    void addTimezone(UUID userId, UserTimezone userTimezone);

    Map<UUID, UserTimezone> getTimezones(UUID userId);

    void deleteTimezone(UUID userId, UUID timezoneId);

    void updateTimezone(UUID userId, UserTimezone timezone);
}

