package com.gt.scr.movie.config;

import com.gt.scr.movie.vertx.Server;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VertxConfig {
    @Bean
    public void initialiseVertx() {
        Vertx vertx = Vertx.vertx();
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        vertx.deployVerticle(new Server(), deploymentOptions);
    }
}
