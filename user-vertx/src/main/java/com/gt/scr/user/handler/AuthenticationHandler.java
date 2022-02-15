package com.gt.scr.user.handler;

import com.gt.scr.domain.User;
import com.gt.scr.user.resource.domain.LoginRequestDTO;
import com.gt.scr.user.resource.domain.LoginResponseDTO;
import com.gt.scr.user.service.UserServiceV2;
import com.gt.scr.utils.DataEncoder;
import com.gt.scr.utils.JwtTokenUtil;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.io.IOException;
import java.security.Key;
import java.time.Duration;

public class AuthenticationHandler implements Handler<RoutingContext> {
    private final UserServiceV2 userServiceV2;
    private final DataEncoder dataEncoder;
    private final Key key;

    public AuthenticationHandler(UserServiceV2 userServiceV2,
                                 DataEncoder dataEncoder,
                                 Key key) {
        this.userServiceV2 = userServiceV2;
        this.dataEncoder = dataEncoder;
        this.key = key;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        LoginRequestDTO loginRequestDTO = routingContext.getBodyAsJson().mapTo(LoginRequestDTO.class);

        userServiceV2.findByUsername(loginRequestDTO.userName())
                .onSuccess(event -> {
                    User user = event.mapTo(User.class);
                    if (user.password().equals(encode(loginRequestDTO.password()))) {
                        JsonObject response = JsonObject.mapFrom(new LoginResponseDTO(
                                JwtTokenUtil.generateTokenV2(user, Duration.ofMinutes(10),
                                        key),
                                user.id()));
                        routingContext.response().setChunked(true);
                        routingContext.response().setStatusCode(200);
                        routingContext.response().write(response.toBuffer());
                    } else {
                        routingContext.response().setStatusCode(401);
                    }
                    routingContext.response().end();
                }).onFailure(th -> {
                    routingContext.response().setStatusCode(401);
                    routingContext.response().end();
                });
    }

    private String encode(String password) {
        try {
            return dataEncoder.encode(password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
