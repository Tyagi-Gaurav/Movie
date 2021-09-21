package com.gt.scr.movie.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.1)",
    comments = "Source: Login.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LoginServiceGrpc {

  private LoginServiceGrpc() {}

  public static final String SERVICE_NAME = "com.gt.scr.movie.grpc.LoginService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.gt.scr.movie.grpc.LoginGrpcRequestDTO,
      com.gt.scr.movie.grpc.LoginGrpcResponseDTO> getLoginMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "login",
      requestType = com.gt.scr.movie.grpc.LoginGrpcRequestDTO.class,
      responseType = com.gt.scr.movie.grpc.LoginGrpcResponseDTO.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.gt.scr.movie.grpc.LoginGrpcRequestDTO,
      com.gt.scr.movie.grpc.LoginGrpcResponseDTO> getLoginMethod() {
    io.grpc.MethodDescriptor<com.gt.scr.movie.grpc.LoginGrpcRequestDTO, com.gt.scr.movie.grpc.LoginGrpcResponseDTO> getLoginMethod;
    if ((getLoginMethod = LoginServiceGrpc.getLoginMethod) == null) {
      synchronized (LoginServiceGrpc.class) {
        if ((getLoginMethod = LoginServiceGrpc.getLoginMethod) == null) {
          LoginServiceGrpc.getLoginMethod = getLoginMethod =
              io.grpc.MethodDescriptor.<com.gt.scr.movie.grpc.LoginGrpcRequestDTO, com.gt.scr.movie.grpc.LoginGrpcResponseDTO>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "login"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gt.scr.movie.grpc.LoginGrpcRequestDTO.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gt.scr.movie.grpc.LoginGrpcResponseDTO.getDefaultInstance()))
              .setSchemaDescriptor(new LoginServiceMethodDescriptorSupplier("login"))
              .build();
        }
      }
    }
    return getLoginMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LoginServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoginServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LoginServiceStub>() {
        @java.lang.Override
        public LoginServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LoginServiceStub(channel, callOptions);
        }
      };
    return LoginServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LoginServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoginServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LoginServiceBlockingStub>() {
        @java.lang.Override
        public LoginServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LoginServiceBlockingStub(channel, callOptions);
        }
      };
    return LoginServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LoginServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoginServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LoginServiceFutureStub>() {
        @java.lang.Override
        public LoginServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LoginServiceFutureStub(channel, callOptions);
        }
      };
    return LoginServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class LoginServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void login(com.gt.scr.movie.grpc.LoginGrpcRequestDTO request,
        io.grpc.stub.StreamObserver<com.gt.scr.movie.grpc.LoginGrpcResponseDTO> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoginMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getLoginMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.gt.scr.movie.grpc.LoginGrpcRequestDTO,
                com.gt.scr.movie.grpc.LoginGrpcResponseDTO>(
                  this, METHODID_LOGIN)))
          .build();
    }
  }

  /**
   */
  public static final class LoginServiceStub extends io.grpc.stub.AbstractAsyncStub<LoginServiceStub> {
    private LoginServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoginServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoginServiceStub(channel, callOptions);
    }

    /**
     */
    public void login(com.gt.scr.movie.grpc.LoginGrpcRequestDTO request,
        io.grpc.stub.StreamObserver<com.gt.scr.movie.grpc.LoginGrpcResponseDTO> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class LoginServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<LoginServiceBlockingStub> {
    private LoginServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoginServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoginServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.gt.scr.movie.grpc.LoginGrpcResponseDTO login(com.gt.scr.movie.grpc.LoginGrpcRequestDTO request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoginMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class LoginServiceFutureStub extends io.grpc.stub.AbstractFutureStub<LoginServiceFutureStub> {
    private LoginServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoginServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoginServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.gt.scr.movie.grpc.LoginGrpcResponseDTO> login(
        com.gt.scr.movie.grpc.LoginGrpcRequestDTO request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOGIN = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LoginServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LoginServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOGIN:
          serviceImpl.login((com.gt.scr.movie.grpc.LoginGrpcRequestDTO) request,
              (io.grpc.stub.StreamObserver<com.gt.scr.movie.grpc.LoginGrpcResponseDTO>) responseObserver);
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

  private static abstract class LoginServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LoginServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.gt.scr.movie.grpc.LoginGrpc.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LoginService");
    }
  }

  private static final class LoginServiceFileDescriptorSupplier
      extends LoginServiceBaseDescriptorSupplier {
    LoginServiceFileDescriptorSupplier() {}
  }

  private static final class LoginServiceMethodDescriptorSupplier
      extends LoginServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LoginServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (LoginServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LoginServiceFileDescriptorSupplier())
              .addMethod(getLoginMethod())
              .build();
        }
      }
    }
    return result;
  }
}
