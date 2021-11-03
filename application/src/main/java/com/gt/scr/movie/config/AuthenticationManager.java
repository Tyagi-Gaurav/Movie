package com.gt.scr.movie.config;

import com.gt.scr.movie.filter.JwtTokenUtil;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class AuthenticationManager implements ReactiveAuthenticationManager {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationManager.class);

    private final UserService userService;
    private final Key signingKey;

    public AuthenticationManager(UserService userService,
                                 Key signingKey) {
        this.userService = userService;
        this.signingKey = signingKey;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getPrincipal().toString();
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(token, signingKey);
        String userId = getUserIdFromToken(jwtTokenUtil);

        if (userId != null) {
            LOG.info("Fetch user from repository: {}", userId);

            return userService.findUserBy(UUID.fromString(userId))
                    .filter(jwtTokenUtil::validateToken)
                    .switchIfEmpty(Mono.defer(Mono::empty))
                    .flatMap(ud -> {
                        LOG.info("Token validated for user {}", userId);
                        // After setting the Authentication in the context, we specify
                        // that the current user is authenticated. So it passes the
                        // Spring Security Configurations successfully.

                        List<Object> authoritiesObjects = (List<Object>) jwtTokenUtil.getClaimFromToken(claims -> claims.get("Authorities"));

                        LinkedHashMap<String, Object> authorities = (LinkedHashMap<String, Object>) authoritiesObjects.get(0);
                        String authority = authorities.get("authority").toString();
                        var userprofile = new UserProfile(ud.id(), authority);

                        LOG.info("User authenticated with role: {}", authority);
                        UsernamePasswordAuthenticationToken userTokenData =
                                new UsernamePasswordAuthenticationToken(userprofile, "", ud.authorities());
                        return Mono.just(userTokenData);
                    });
        }

        return Mono.empty();
    }

    private String getUserIdFromToken(JwtTokenUtil jwtTokenUtil) {
        try {
            return jwtTokenUtil.getUserIdFromToken();
        } catch(ExpiredJwtException e) {
            LOG.warn("Expired user token found");
            return null;
        }
    }
}
