package com.gt.scr.user.service;

import com.gt.scr.domain.User;
import com.gt.scr.user.config.EventBusAddress;
import com.gt.scr.user.exception.UserCreateException;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.core.json.JsonObject;

import java.util.UUID;

public class UserServiceVertxImpl implements UserServiceV2 {

    private final Vertx vertx;

    public UserServiceVertxImpl(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public Future<JsonObject> add(User user) {
        return vertx.eventBus().request(EventBusAddress.REPO_ACCOUNT_CREATE.getAddress(),
                        JsonObject.mapFrom(user))
                .map(objectMessage -> {
                    Object body = objectMessage.body();
                    if (body instanceof ReplyException exp) {
                        throw new UserCreateException(exp);
                    }

                    return new JsonObject();
                });
    }

    @Override
    public Future<JsonObject> findByUsername(String userName) {
        return vertx.eventBus().request(EventBusAddress.REPO_USER_FETCH_BY_USER_NAME.getAddress(),
                        new JsonObject().put("userName", userName))
                .map(msg -> (JsonObject) msg.body());
    }

    @Override
    public Future<JsonObject> findByUserId(UUID userId) {
        return vertx.eventBus().request(EventBusAddress.REPO_USER_FETCH_BY_USER_ID.getAddress(),
                        new JsonObject().put("userId", userId.toString()))
                .map(msg -> (JsonObject) msg.body());
    }

    @Override
    public Future<JsonObject> delete(UUID userId) {
        return vertx.eventBus().request(EventBusAddress.REPO_USER_DELETE_BY_USER_ID.getAddress(),
                new JsonObject().put("userId", userId.toString()))
                .map(msg -> (JsonObject) msg.body());
    }

    @Override
    public Future<JsonObject> fetchAll() {
        return vertx.eventBus().request(EventBusAddress.REPO_USER_FETCH_ALL.getAddress(),
                        null)
                .map(msg -> (JsonObject) msg.body());
    }
}
