package com.gt.scr.movie.functions;

@FunctionalInterface
public interface Interaction<X, Y, Z, R> {
    R apply(X x, Y y, Z z);
}
