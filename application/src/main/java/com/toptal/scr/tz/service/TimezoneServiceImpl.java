package com.toptal.scr.tz.service;

import com.toptal.scr.tz.service.domain.ImmutableUser;
import com.toptal.scr.tz.service.domain.User;
import com.toptal.scr.tz.service.domain.UserTimezone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimezoneServiceImpl implements TimezoneService {

    @Autowired
    private UserService userService;

    @Override
    public void addTimezone(String user, UserTimezone userTimezone) {
        User userFromDatabase = userService.loadUserByUsername(user);
        List<UserTimezone> userTimezones = userFromDatabase.userTimeZones();

        if (userTimezones.isEmpty()) {
            userTimezones = new ArrayList<>();
        }

        userTimezones.add(userTimezone);

        userService.add(ImmutableUser.builder().from(userFromDatabase)
                .userTimeZones(userTimezones).build());
    }
}
