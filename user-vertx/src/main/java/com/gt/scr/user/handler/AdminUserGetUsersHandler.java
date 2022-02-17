package com.gt.scr.user.handler;

import com.gt.scr.domain.User;
import com.gt.scr.user.resource.domain.UserDetailsResponseDTO;
import com.gt.scr.user.service.UserServiceV2;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public class AdminUserGetUsersHandler implements Handler<RoutingContext> {

    private final UserServiceV2 userServiceV2;

    public AdminUserGetUsersHandler(UserServiceV2 userServiceV2) {
        this.userServiceV2 = userServiceV2;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        userServiceV2.fetchAll()
                .onSuccess(jo -> {
                    routingContext.response().setChunked(true);

                    List<JsonObject> userList = jo.getJsonArray("userList").getList();
                    List<JsonObject> jsonNodes = userList.stream()
                            .map(uo -> uo.mapTo(User.class))
                            .map(user -> new UserDetailsResponseDTO(user.username(), user.password(), user.firstName(),
                                    user.lastName(), user.getRole(), user.id()))
                            .map(JsonObject::mapFrom)
                            .toList();

                    routingContext.response().setStatusCode(200);
                    routingContext.response().write(new JsonObject().put("userList", new JsonArray(jsonNodes)).toBuffer());
                    routingContext.response().end();
                })
                .onFailure(th -> routingContext.response().setStatusCode(404).end());
    }
}
