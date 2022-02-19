package com.gt.scr.user.handler;

import com.gt.scr.domain.User;
import com.gt.scr.user.service.UserServiceV2;
import com.gt.scr.user.service.domain.Role;
import com.gt.scr.utils.JwtTokenUtil;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class SecurityHandler implements Handler<RoutingContext> {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityHandler.class);

    private final UserServiceV2 userServiceV2;
    private final Key key;
    public static final String ALLOWED_ROLES = "allowedRoles";

    public SecurityHandler(UserServiceV2 userServiceV2, Key key) {
        this.userServiceV2 = userServiceV2;
        this.key = key;
    }

    @Override
    public void handle(RoutingContext event) {
        String token = event.request().getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isEmpty(token)) {
            event.response().setStatusCode(400);
            event.end();
        } else {
            JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(token.substring(6), key);
            if (!jwtTokenUtil.isTokenValid()) {
                LOG.error("Found invalid token");
                event.response().setStatusCode(400);
                event.end();
            }

            Object allowedRoles = event.currentRoute().getMetadata(ALLOWED_ROLES);
            Set<String> roles = Arrays.stream(allowedRoles.toString().toLowerCase(Locale.ROOT).split(","))
                    .collect(Collectors.toSet());

            userServiceV2.findByUserId(UUID.fromString(jwtTokenUtil.getUserIdFromToken()))
                    .onSuccess(jsonObject -> {
                        User user = jsonObject.mapTo(User.class);
                        if (!roles.contains(Role.ALL.toString().toLowerCase(Locale.ROOT))
                                && !roles.contains(user.getRole().toLowerCase())) {
                            event.response().setStatusCode(403).end();
                        } else {
                            event.next();
                        }
                    }).onFailure(th -> event.response().setStatusCode(401).end());
        }
    }
}
