package com.gt.scr.user.service;


import com.gt.scr.domain.User;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.UUID;

public interface UserServiceV2 {
    Future<JsonObject> add(User user);

    Future<JsonObject> findByUsername(String userName);

    Future<JsonObject> findByUserId(UUID userId);

    Future<JsonObject> delete(UUID userId);

    Future<JsonObject> fetchAll();
}
