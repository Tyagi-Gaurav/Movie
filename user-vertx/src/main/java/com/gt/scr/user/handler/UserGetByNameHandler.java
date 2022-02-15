package com.gt.scr.user.handler;

import com.gt.scr.domain.User;
import com.gt.scr.user.resource.domain.UserDetailsResponseDTO;
import com.gt.scr.user.service.UserServiceV2;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserGetByNameHandler implements Handler<RoutingContext> {
    private final UserServiceV2 userServiceV2;

    public UserGetByNameHandler(UserServiceV2 userServiceV2) {
        this.userServiceV2 = userServiceV2;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        String userName = routingContext.request().getParam("userName");

        userServiceV2.findByUsername(userName)
                .onSuccess(jsonObject -> {
                    User user = jsonObject.mapTo(User.class);
                    routingContext.response().setStatusCode(200);
                    routingContext.response().setChunked(true);
                    JsonObject responseBody = JsonObject.mapFrom(new UserDetailsResponseDTO(user.username(), user.password(), user.firstName(),
                            user.lastName(), user.getRole(), user.id()));
                    routingContext.response().write(responseBody.toBuffer());
                    routingContext.response().end();
                })
                .onFailure(th -> {
                    routingContext.response().setStatusCode(404);
                    routingContext.response().end();
                });
    }
}
