package com.toptal.scr.tz.service;

import com.toptal.scr.tz.service.domain.UserTimezone;

import java.util.List;

public interface TimezoneService {
    void addTimezone(String user, UserTimezone userTimezone);

    List<UserTimezone> getTimezones(String user);
}
