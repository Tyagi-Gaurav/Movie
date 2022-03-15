package com.gt.scr.user.handler;

import com.gt.scr.domain.User;
import com.gt.scr.user.exception.ExceptionMapper;
import com.gt.scr.user.resource.domain.UserDetailsResponseDTO;
import com.gt.scr.user.service.UserServiceV2;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserGetByNameHandler implements Handler<RoutingContext> {
    private final UserServiceV2 userServiceV2;
    private final ExceptionMapper exceptionMapper;

    public UserGetByNameHandler(UserServiceV2 userServiceV2,
                                ExceptionMapper exceptionMapper) {
        this.userServiceV2 = userServiceV2;
        this.exceptionMapper = exceptionMapper;
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
                            user.lastName(), user.getRole(), user.id(), user.dateOfBirth(), user.gender(), user.homeCountry()));
                    routingContext.response().write(responseBody.toBuffer());
                    routingContext.response().end();
                })
                .onFailure(th -> exceptionMapper.mapException(routingContext, 404));
    }
}
