package com.gt.scr.movie.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.1)",
    comments = "Source: UserService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FetchUserServiceGrpc {

  private FetchUserServiceGrpc() {}

  public static final String SERVICE_NAME = "com.gt.scr.movie.grpc.FetchUserService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.gt.scr.movie.grpc.FetchUserDetailsByIdGrpcRequestDTO,
      com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO> getFetchUsersByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "fetchUsersById",
      requestType = com.gt.scr.movie.grpc.FetchUserDetailsByIdGrpcRequestDTO.class,
      responseType = com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.gt.scr.movie.grpc.FetchUserDetailsByIdGrpcRequestDTO,
      com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO> getFetchUsersByIdMethod() {
    io.grpc.MethodDescriptor<com.gt.scr.movie.grpc.FetchUserDetailsByIdGrpcRequestDTO, com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO> getFetchUsersByIdMethod;
    if ((getFetchUsersByIdMethod = FetchUserServiceGrpc.getFetchUsersByIdMethod) == null) {
      synchronized (FetchUserServiceGrpc.class) {
        if ((getFetchUsersByIdMethod = FetchUserServiceGrpc.getFetchUsersByIdMethod) == null) {
          FetchUserServiceGrpc.getFetchUsersByIdMethod = getFetchUsersByIdMethod =
              io.grpc.MethodDescriptor.<com.gt.scr.movie.grpc.FetchUserDetailsByIdGrpcRequestDTO, com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "fetchUsersById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gt.scr.movie.grpc.FetchUserDetailsByIdGrpcRequestDTO.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO.getDefaultInstance()))
              .setSchemaDescriptor(new FetchUserServiceMethodDescriptorSupplier("fetchUsersById"))
              .build();
        }
      }
    }
    return getFetchUsersByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.gt.scr.movie.grpc.FetchUserDetailsByNameGrpcRequestDTO,
      com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO> getFetchUsersByNameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "fetchUsersByName",
      requestType = com.gt.scr.movie.grpc.FetchUserDetailsByNameGrpcRequestDTO.class,
      responseType = com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.gt.scr.movie.grpc.FetchUserDetailsByNameGrpcRequestDTO,
      com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO> getFetchUsersByNameMethod() {
    io.grpc.MethodDescriptor<com.gt.scr.movie.grpc.FetchUserDetailsByNameGrpcRequestDTO, com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO> getFetchUsersByNameMethod;
    if ((getFetchUsersByNameMethod = FetchUserServiceGrpc.getFetchUsersByNameMethod) == null) {
      synchronized (FetchUserServiceGrpc.class) {
        if ((getFetchUsersByNameMethod = FetchUserServiceGrpc.getFetchUsersByNameMethod) == null) {
          FetchUserServiceGrpc.getFetchUsersByNameMethod = getFetchUsersByNameMethod =
              io.grpc.MethodDescriptor.<com.gt.scr.movie.grpc.FetchUserDetailsByNameGrpcRequestDTO, com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "fetchUsersByName"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gt.scr.movie.grpc.FetchUserDetailsByNameGrpcRequestDTO.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO.getDefaultInstance()))
              .setSchemaDescriptor(new FetchUserServiceMethodDescriptorSupplier("fetchUsersByName"))
              .build();
        }
      }
    }
    return getFetchUsersByNameMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FetchUserServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FetchUserServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FetchUserServiceStub>() {
        @java.lang.Override
        public FetchUserServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FetchUserServiceStub(channel, callOptions);
        }
      };
    return FetchUserServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FetchUserServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FetchUserServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FetchUserServiceBlockingStub>() {
        @java.lang.Override
        public FetchUserServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FetchUserServiceBlockingStub(channel, callOptions);
        }
      };
    return FetchUserServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FetchUserServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FetchUserServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FetchUserServiceFutureStub>() {
        @java.lang.Override
        public FetchUserServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FetchUserServiceFutureStub(channel, callOptions);
        }
      };
    return FetchUserServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class FetchUserServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void fetchUsersById(com.gt.scr.movie.grpc.FetchUserDetailsByIdGrpcRequestDTO request,
        io.grpc.stub.StreamObserver<com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFetchUsersByIdMethod(), responseObserver);
    }

    /**
     */
    public void fetchUsersByName(com.gt.scr.movie.grpc.FetchUserDetailsByNameGrpcRequestDTO request,
        io.grpc.stub.StreamObserver<com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFetchUsersByNameMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getFetchUsersByIdMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.gt.scr.movie.grpc.FetchUserDetailsByIdGrpcRequestDTO,
                com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO>(
                  this, METHODID_FETCH_USERS_BY_ID)))
          .addMethod(
            getFetchUsersByNameMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.gt.scr.movie.grpc.FetchUserDetailsByNameGrpcRequestDTO,
                com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO>(
                  this, METHODID_FETCH_USERS_BY_NAME)))
          .build();
    }
  }

  /**
   */
  public static final class FetchUserServiceStub extends io.grpc.stub.AbstractAsyncStub<FetchUserServiceStub> {
    private FetchUserServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FetchUserServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FetchUserServiceStub(channel, callOptions);
    }

    /**
     */
    public void fetchUsersById(com.gt.scr.movie.grpc.FetchUserDetailsByIdGrpcRequestDTO request,
        io.grpc.stub.StreamObserver<com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFetchUsersByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void fetchUsersByName(com.gt.scr.movie.grpc.FetchUserDetailsByNameGrpcRequestDTO request,
        io.grpc.stub.StreamObserver<com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFetchUsersByNameMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class FetchUserServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<FetchUserServiceBlockingStub> {
    private FetchUserServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FetchUserServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FetchUserServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO fetchUsersById(com.gt.scr.movie.grpc.FetchUserDetailsByIdGrpcRequestDTO request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFetchUsersByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO fetchUsersByName(com.gt.scr.movie.grpc.FetchUserDetailsByNameGrpcRequestDTO request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFetchUsersByNameMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FetchUserServiceFutureStub extends io.grpc.stub.AbstractFutureStub<FetchUserServiceFutureStub> {
    private FetchUserServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FetchUserServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FetchUserServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO> fetchUsersById(
        com.gt.scr.movie.grpc.FetchUserDetailsByIdGrpcRequestDTO request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFetchUsersByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO> fetchUsersByName(
        com.gt.scr.movie.grpc.FetchUserDetailsByNameGrpcRequestDTO request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFetchUsersByNameMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FETCH_USERS_BY_ID = 0;
  private static final int METHODID_FETCH_USERS_BY_NAME = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FetchUserServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FetchUserServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FETCH_USERS_BY_ID:
          serviceImpl.fetchUsersById((com.gt.scr.movie.grpc.FetchUserDetailsByIdGrpcRequestDTO) request,
              (io.grpc.stub.StreamObserver<com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO>) responseObserver);
          break;
        case METHODID_FETCH_USERS_BY_NAME:
          serviceImpl.fetchUsersByName((com.gt.scr.movie.grpc.FetchUserDetailsByNameGrpcRequestDTO) request,
              (io.grpc.stub.StreamObserver<com.gt.scr.movie.grpc.UserDetailsGrpcResponseDTO>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class FetchUserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FetchUserServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.gt.scr.movie.grpc.AccountCreateGrpc.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FetchUserService");
    }
  }

  private static final class FetchUserServiceFileDescriptorSupplier
      extends FetchUserServiceBaseDescriptorSupplier {
    FetchUserServiceFileDescriptorSupplier() {}
  }

  private static final class FetchUserServiceMethodDescriptorSupplier
      extends FetchUserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FetchUserServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FetchUserServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FetchUserServiceFileDescriptorSupplier())
              .addMethod(getFetchUsersByIdMethod())
              .addMethod(getFetchUsersByNameMethod())
              .build();
        }
      }
    }
    return result;
  }
}
