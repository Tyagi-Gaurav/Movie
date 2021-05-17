package com.toptal.scr.tz.resource.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link ErrorResponse}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableErrorResponse.builder()}.
 */
@Generated(from = "ErrorResponse", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableErrorResponse implements ErrorResponse {
  private final String message;

  private ImmutableErrorResponse(String message) {
    this.message = message;
  }

  /**
   * @return The value of the {@code message} attribute
   */
  @JsonProperty("message")
  @Override
  public String message() {
    return message;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ErrorResponse#message() message} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for message
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableErrorResponse withMessage(String value) {
    String newValue = Objects.requireNonNull(value, "message");
    if (this.message.equals(newValue)) return this;
    return new ImmutableErrorResponse(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableErrorResponse} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableErrorResponse
        && equalTo((ImmutableErrorResponse) another);
  }

  private boolean equalTo(ImmutableErrorResponse another) {
    return message.equals(another.message);
  }

  /**
   * Computes a hash code from attributes: {@code message}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + message.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ErrorResponse} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ErrorResponse")
        .omitNullValues()
        .add("message", message)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link ErrorResponse} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ErrorResponse instance
   */
  public static ImmutableErrorResponse copyOf(ErrorResponse instance) {
    if (instance instanceof ImmutableErrorResponse) {
      return (ImmutableErrorResponse) instance;
    }
    return ImmutableErrorResponse.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableErrorResponse ImmutableErrorResponse}.
   * <pre>
   * ImmutableErrorResponse.builder()
   *    .message(String) // required {@link ErrorResponse#message() message}
   *    .build();
   * </pre>
   * @return A new ImmutableErrorResponse builder
   */
  public static ImmutableErrorResponse.Builder builder() {
    return new ImmutableErrorResponse.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableErrorResponse ImmutableErrorResponse}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "ErrorResponse", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_MESSAGE = 0x1L;
    private long initBits = 0x1L;

    private @Nullable String message;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code ErrorResponse} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ErrorResponse instance) {
      Objects.requireNonNull(instance, "instance");
      message(instance.message());
      return this;
    }

    /**
     * Initializes the value for the {@link ErrorResponse#message() message} attribute.
     * @param message The value for message 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("message")
    public final Builder message(String message) {
      this.message = Objects.requireNonNull(message, "message");
      initBits &= ~INIT_BIT_MESSAGE;
      return this;
    }

    /**
     * Builds a new {@link ImmutableErrorResponse ImmutableErrorResponse}.
     * @return An immutable instance of ErrorResponse
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableErrorResponse build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableErrorResponse(message);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_MESSAGE) != 0) attributes.add("message");
      return "Cannot build ErrorResponse, some of required attributes are not set " + attributes;
    }
  }
}
