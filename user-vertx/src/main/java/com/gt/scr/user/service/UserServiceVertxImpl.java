package com.gt.scr.user.service;

import com.gt.scr.domain.User;
import com.gt.scr.user.config.EventBusAddress;
import com.gt.scr.user.exception.UserCreateException;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
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
        Long sendTimeout = vertx.getOrCreateContext()
                .config()
                .getLong("bus" + "." + EventBusAddress.REPO_ACCOUNT_CREATE.getAddress() + ".timeoutInMs",
                        DeliveryOptions.DEFAULT_TIMEOUT);
        return vertx.eventBus().request(EventBusAddress.REPO_ACCOUNT_CREATE.getAddress(),
                        JsonObject.mapFrom(user),
                        new DeliveryOptions().setSendTimeout(sendTimeout))
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
        Long sendTimeout = vertx.getOrCreateContext()
                .config()
                .getLong("bus" + "." + EventBusAddress.REPO_USER_FETCH_BY_USER_NAME.getAddress() + ".timeoutInMs");
        return vertx.eventBus().request(EventBusAddress.REPO_USER_FETCH_BY_USER_NAME.getAddress(),
                        new JsonObject().put("userName", userName),
                        new DeliveryOptions().setSendTimeout(sendTimeout))
                .map(msg -> (JsonObject) msg.body());
    }

    @Override
    public Future<JsonObject> findByUserId(UUID userId) {
        Long sendTimeout = vertx.getOrCreateContext()
                .config()
                .getLong("bus" + "." + EventBusAddress.REPO_USER_FETCH_BY_USER_ID.getAddress() + ".timeoutInMs");
        return vertx.eventBus().request(EventBusAddress.REPO_USER_FETCH_BY_USER_ID.getAddress(),
                        new JsonObject().put("userId", userId.toString()),
                        new DeliveryOptions().setSendTimeout(sendTimeout))
                .map(msg -> (JsonObject) msg.body());
    }

    @Override
    public Future<JsonObject> delete(UUID userId) {
        Long sendTimeout = vertx.getOrCreateContext()
                .config()
                .getLong("bus" + "." + EventBusAddress.REPO_USER_DELETE_BY_USER_ID.getAddress() + ".timeoutInMs");
        return vertx.eventBus().request(EventBusAddress.REPO_USER_DELETE_BY_USER_ID.getAddress(),
                new JsonObject().put("userId", userId.toString()),
                        new DeliveryOptions().setSendTimeout(sendTimeout))
                .map(msg -> (JsonObject) msg.body());
    }

    @Override
    public Future<JsonObject> fetchAll() {
        Long sendTimeout = vertx.getOrCreateContext()
                .config()
                .getLong("bus" + "." + EventBusAddress.REPO_USER_FETCH_ALL.getAddress() + ".timeoutInMs");
        return vertx.eventBus().request(EventBusAddress.REPO_USER_FETCH_ALL.getAddress(),
                        null,
                        new DeliveryOptions().setSendTimeout(sendTimeout))
                .map(msg -> (JsonObject) msg.body());
    }
}
