package com.gt.scr.movie.config;

import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.UserService;
import com.gt.scr.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.List;
import java.util.UUID;

public class AuthenticationManager implements ReactiveAuthenticationManager {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationManager.class);

    private final UserService userService;
    private final Key signingKey;

    public AuthenticationManager(UserService userService, Key signingKey) {
        this.userService = userService;
        this.signingKey = signingKey;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getPrincipal().toString();
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(token, signingKey);
        String userId = getUserIdFromToken(jwtTokenUtil);
        LOG.info("Token: {}", token);

        if (userId != null) {
            LOG.info("Fetch user details for: {}", userId);

            return userService.findUserBy(UUID.fromString(userId))
                    .filter(jwtTokenUtil::validateToken)
                    .switchIfEmpty(Mono.error(() -> new IllegalCallerException(userId)))
                    .flatMap(ud -> {
                        // After setting the Authentication in the context, we specify
                        // that the current user is authenticated. So it passes the
                        // Spring Security Configurations successfully.

                        List<Object> authoritiesObjects = (List<Object>) jwtTokenUtil.getClaimFromToken(claims -> claims.get("Authorities"));

                        LOG.info("Authorities Object {}", authoritiesObjects);

                        String authority = (String) authoritiesObjects.get(0);
                        var userprofile = new UserProfile(ud.id(), authority, token);

                        LOG.info("User {} authenticated with role: {}", userId, authority);
                        UsernamePasswordAuthenticationToken userTokenData =
                                new UsernamePasswordAuthenticationToken(userprofile, "",
                                        ud.authorities().stream().map(SimpleGrantedAuthority::new)
                                                .toList());
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
