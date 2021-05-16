package com.toptal.scr.tz.service;

import com.toptal.scr.tz.service.domain.UserTimezone;

public interface TimezoneService {
    void addTimezone(String user, UserTimezone userTimezone);
}
