package com.gt.scr.movie.grpc_resource;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

public class ExceptionHandlerInterceptor implements ServerInterceptor {

    @Override
    public <R, T> ServerCall.Listener<R> interceptCall(ServerCall<R, T> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<R, T> serverCallHandler) {
        ServerCall.Listener<R> listener = serverCallHandler.startCall(serverCall, metadata);
        return new ExceptionHandlingServerCallListener<>(listener, serverCall, metadata);
    }


}
