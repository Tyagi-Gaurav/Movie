package com.gt.scr.movie.ui.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserResource {
    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        Object name = principal.getAttribute("name");
        Object login = principal.getAttribute("login");
        Object email = principal.getAttribute("email");
        Object id = principal.getAttribute("id");

        LOG.info("Name: {}\n, Login: {}\n, email: {}, id: {}",
                name, login, email, id);

        Object unknown = Optional.ofNullable(name).orElse("unknown");
        return Collections.singletonMap("name", unknown);
    }
}
