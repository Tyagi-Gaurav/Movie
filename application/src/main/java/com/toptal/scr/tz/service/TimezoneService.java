package com.toptal.scr.tz.service;

import com.toptal.scr.tz.service.domain.UserTimezone;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Map;
import java.util.UUID;

public interface TimezoneService {
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    void addTimezone(String userName, UserTimezone userTimezone);

    Map<UUID, UserTimezone> getTimezones(String userName);

    void deleteTimezone(String userName, UUID timezoneId);

    void updateTimezone(String userName, UserTimezone timezone);
}

