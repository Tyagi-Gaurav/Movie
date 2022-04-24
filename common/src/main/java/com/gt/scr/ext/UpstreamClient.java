package com.gt.scr.ext;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpstreamClient<P, R> {
    Mono<R> execute(P p);
}
