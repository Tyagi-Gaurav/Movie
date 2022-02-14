package com.gt.scr.movie.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class NettyWebserverFactoryPortCustomizer
        implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

    @Value("${server.port}")
    private int serverPort;

    @Override
    public void customize(NettyReactiveWebServerFactory factory) {
        factory.setPort(serverPort);
    }
}
