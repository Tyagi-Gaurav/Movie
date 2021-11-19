package com.gt.scr.movie.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.deployVerticle(new MovieVerticle(), ar -> {
            if (ar.succeeded()) {
                String result = ar.result();
                LOG.info("Successfully deployed {}", result);
            } else {
                LOG.error("Error while deploying", ar.cause());
            }
        });
    }

//    public static void main(String[] args) {
//        Vertx vertx = Vertx.vertx();
//        DeploymentOptions deploymentOptions = new DeploymentOptions();
//                //.setConfig();
//        vertx.deployVerticle(new Server(), deploymentOptions);
//    }
}
