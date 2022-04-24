package com.gt.scr.user.util;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class Bus {
    private Bus() {}

    public static Future<Message<Object>> request(Vertx vertx, String address, JsonObject jsonObject) {
        Long sendTimeout = vertx.getOrCreateContext()
                .config()
                .getLong("bus" + "." + address + ".timeoutInMs",
                        DeliveryOptions.DEFAULT_TIMEOUT);
        return vertx.eventBus().request(address, jsonObject, new DeliveryOptions().setSendTimeout(sendTimeout));
    }
}
