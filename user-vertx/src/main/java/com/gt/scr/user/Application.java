package com.gt.scr.user;

import com.gt.scr.user.dao.ChangeLogVerticle;
import com.gt.scr.user.dao.UserDaoHandler;
import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.Factory;
import io.micronaut.inject.qualifiers.Qualifiers;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

public class Application extends AbstractVerticle {
    private final BeanContext beanContext = BeanContext.run();

    @Override
    public void start(Promise<Void> startPromise) {
        beanContext.registerSingleton(vertx);
        beanContext.getBeanDefinitions(Qualifiers.byStereotype(Factory.class));

        PgConnectOptions pgConnectOptions = getPgConnectOptions(config());
        Future<String> changeLogVerticle = vertx.deployVerticle(new ChangeLogVerticle(pgConnectOptions),
                new DeploymentOptions().setConfig(config()));
        Future<String> httpServerVerticle = vertx.deployVerticle(beanContext.getBean(HttpServerVerticle.class),
                new DeploymentOptions().setConfig(config()));
        Future<String> userDaoHandlerVerticle = vertx.deployVerticle(() -> new UserDaoHandler(getPgPool(pgConnectOptions)),
                new DeploymentOptions(config()));

        CompositeFuture.all(changeLogVerticle, httpServerVerticle, userDaoHandlerVerticle)
                .onComplete(ar -> {
                    if (ar.succeeded()) {
                        startPromise.complete();
                    } else {
                        startPromise.fail(ar.cause());
                    }
                });
    }

    private PgConnectOptions getPgConnectOptions(JsonObject config) {
        return new PgConnectOptions()
                .setPort(config.getInteger("db.port"))
                .setHost(config.getString("db.host"))
                .setDatabase(config.getString("db.name"))
                .setReconnectAttempts(2)
                .setReconnectInterval(1000)
                .setUser(config.getString("db.user"))
                .setPassword(config.getString("db.password"));
    }

    private PgPool getPgPool(PgConnectOptions pgConnectOptions) {
        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5)
                .setShared(true);

        return PgPool.pool(vertx, pgConnectOptions, poolOptions);
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        super.stop(stopPromise);
    }
}
