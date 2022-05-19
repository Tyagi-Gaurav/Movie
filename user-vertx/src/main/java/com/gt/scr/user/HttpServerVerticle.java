package com.gt.scr.user;

import com.gt.scr.user.handler.ConfigPublishHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.healthchecks.HealthCheckHandler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ResponseContentTypeHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(HttpServerVerticle.class);

    @Inject
    private HealthCheckHandler statusHandler;

    @Inject
    @Named("userApiRouter")
    private Router userApiRoute;

    @Inject
    @Named("adminRoute")
    private Router adminRoute;

    @Inject
    @Named("webFilters")
    private Router filterRoute;

    @Override
    public void start(Promise<Void> startPromise) {
        HttpServer httpServer = vertx.createHttpServer();
        int port = config().getInteger("http.port", 6060);
        Router privateApi = Router.router(vertx);

        privateApi.get()
                .handler(ResponseContentTypeHandler.create())
                .handler(new ConfigPublishHandler(config()));

        Router mainRouter = Router.router(vertx);
        Router healthcheckApi = Router.router(vertx);

        healthcheckApi.get("/status").handler(statusHandler);

        filterRoute.route("/*").subRouter(mainRouter);
        mainRouter.route("/api/user*").subRouter(userApiRoute);
        mainRouter.route("/api/user/manage/*").subRouter(adminRoute);
        mainRouter.route("/private/config/*").subRouter(privateApi);
        mainRouter.route("/api/*").subRouter(healthcheckApi);

        httpServer.requestHandler(filterRoute).listen(port, ar -> {
            if (ar.succeeded()) {
                startPromise.complete();
            } else {
                startPromise.fail(ar.cause());
            }
        });

        LOG.info("Open for requests on http://localhost:{}", port);
    }
}
