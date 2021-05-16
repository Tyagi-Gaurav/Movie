package com.toptal.scr.tz.service;

import com.toptal.scr.tz.service.domain.UserTimezone;

import java.util.Map;
import java.util.UUID;

public interface TimezoneService {
    void addTimezone(String user, UserTimezone userTimezone);

    Map<UUID, UserTimezone> getTimezones(String user);

    void deleteTimezone(String user, UUID timezoneId);
}

