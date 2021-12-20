package com.gt.scr.movie.grpc_resource;

import com.gt.scr.exception.DuplicateRecordException;
import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.Status;

public class ExceptionHandlingServerCallListener<R, T>
            extends ForwardingServerCallListener.SimpleForwardingServerCallListener<R> {
        private ServerCall<R, T> serverCall;
        private Metadata metadata;

        ExceptionHandlingServerCallListener(ServerCall.Listener<R> listener, ServerCall<R, T> serverCall,
                                            Metadata metadata) {
            super(listener);
            this.serverCall = serverCall;
            this.metadata = metadata;
        }

        @Override
        public void onHalfClose() {
            try {
                super.onHalfClose();
            } catch (Exception ex) {
                handleException(ex, serverCall, metadata);
                throw ex;
            }
        }

        @Override
        public void onReady() {
            try {
                super.onReady();
            } catch (Exception ex) {
                handleException(ex, serverCall, metadata);
                throw ex;
            }
        }

        private void handleException(Exception exception,
                                     ServerCall<R, T> serverCall,
                                     Metadata metadata) {
            if (exception instanceof IllegalArgumentException) {
                serverCall.close(Status.INVALID_ARGUMENT.withDescription(exception.getMessage()), metadata);
            } else if (exception instanceof DuplicateRecordException) {
                serverCall.close(Status.ALREADY_EXISTS.withDescription(exception.getMessage()), metadata);
            }
            else {
                serverCall.close(Status.UNKNOWN, metadata);
            }
        }
    }