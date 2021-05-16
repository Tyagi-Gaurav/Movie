package com.toptal.scr.tz.dao;

import com.toptal.scr.tz.service.domain.User;

public interface UserRepository {
    User findUserBy(String userName);

    void add(User user);
}
