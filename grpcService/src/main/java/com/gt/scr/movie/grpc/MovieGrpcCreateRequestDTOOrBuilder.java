// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Movie.proto

package com.gt.scr.movie.grpc;

public interface MovieGrpcCreateRequestDTOOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.gt.scr.movie.grpc.MovieGrpcCreateRequestDTO)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string name = 1;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <code>string name = 1;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>int32 yearProduced = 2;</code>
   * @return The yearProduced.
   */
  int getYearProduced();

  /**
   * <code>double rating = 3;</code>
   * @return The rating.
   */
  double getRating();
}
