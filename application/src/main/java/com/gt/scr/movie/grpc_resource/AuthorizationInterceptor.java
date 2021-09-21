package com.gt.scr.movie.grpc_resource;

import com.gt.scr.movie.filter.JwtTokenUtil;
import com.gt.scr.movie.service.UserService;
import com.gt.scr.movie.service.domain.User;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

import java.security.Key;
import java.util.UUID;

public class AuthorizationInterceptor implements ServerInterceptor {

    private final Key signingKey;
    private final UserService userService;
    public static final Context.Key USER_ID_KEY = Context.key("USER_ID");

    public AuthorizationInterceptor(Key signingKey, UserService userService) {
        this.signingKey = signingKey;
        this.userService = userService;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                 Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {
        String authToken = headers.get(Metadata.Key.of("authToken", Metadata.ASCII_STRING_MARSHALLER));

        if (authToken != null) {
            JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(authToken, signingKey);
            String userIdFromToken = jwtTokenUtil.getUserIdFromToken();
            User userDetails = userService.findUserBy(UUID.fromString(userIdFromToken)).block();

            if (userDetails != null && Boolean.TRUE.equals(jwtTokenUtil.validateToken(userDetails))) {
                Context context = Context.current().withValue(USER_ID_KEY, userDetails.id().toString());
                return Contexts.interceptCall(context, call, headers, next);
            }
        }

        throw new IllegalCallerException();
    }
}
