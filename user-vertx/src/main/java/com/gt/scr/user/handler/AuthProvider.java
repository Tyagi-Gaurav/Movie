package com.gt.scr.user.handler;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authorization.AuthorizationProvider;

public class AuthProvider implements AuthorizationProvider {
    @Override
    public String getId() {
        return null;
    }

    @Override
    public void getAuthorizations(User user, Handler<AsyncResult<Void>> handler) {
        handler.handle(Future.failedFuture(""));
    }
}
