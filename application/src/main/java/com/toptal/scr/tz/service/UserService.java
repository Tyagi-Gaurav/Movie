package com.toptal.scr.tz.service;


import com.toptal.scr.tz.service.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User loadUserByUsername(String userName);
}
