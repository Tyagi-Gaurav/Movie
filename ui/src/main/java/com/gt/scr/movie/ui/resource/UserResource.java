package com.gt.scr.movie.ui.resource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserResource {
    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        String name = principal.getAttribute("name");
        String unknown = Optional.ofNullable(name).orElse("unknown");
        return Collections.singletonMap("name", unknown);
    }
}
