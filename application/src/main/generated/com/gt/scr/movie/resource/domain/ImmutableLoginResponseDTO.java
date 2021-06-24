package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link LoginResponseDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableLoginResponseDTO.builder()}.
 */
@Generated(from = "LoginResponseDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableLoginResponseDTO implements LoginResponseDTO {
  private final String token;
  private final UUID id;

  private ImmutableLoginResponseDTO(String token, UUID id) {
    this.token = token;
    this.id = id;
  }

  /**
   * @return The value of the {@code token} attribute
   */
  @JsonProperty("token")
  @Override
  public String token() {
    return token;
  }

  /**
   * @return The value of the {@code id} attribute
   */
  @JsonProperty("id")
  @Override
  public UUID id() {
    return id;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link LoginResponseDTO#token() token} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for token
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableLoginResponseDTO withToken(String value) {
    String newValue = Objects.requireNonNull(value, "token");
    if (this.token.equals(newValue)) return this;
    return new ImmutableLoginResponseDTO(newValue, this.id);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link LoginResponseDTO#id() id} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableLoginResponseDTO withId(UUID value) {
    if (this.id == value) return this;
    UUID newValue = Objects.requireNonNull(value, "id");
    return new ImmutableLoginResponseDTO(this.token, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableLoginResponseDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableLoginResponseDTO
        && equalTo((ImmutableLoginResponseDTO) another);
  }

  private boolean equalTo(ImmutableLoginResponseDTO another) {
    return token.equals(another.token)
        && id.equals(another.id);
  }

  /**
   * Computes a hash code from attributes: {@code token}, {@code id}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + token.hashCode();
    h += (h << 5) + id.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code LoginResponseDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("LoginResponseDTO")
        .omitNullValues()
        .add("token", token)
        .add("id", id)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link LoginResponseDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable LoginResponseDTO instance
   */
  public static ImmutableLoginResponseDTO copyOf(LoginResponseDTO instance) {
    if (instance instanceof ImmutableLoginResponseDTO) {
      return (ImmutableLoginResponseDTO) instance;
    }
    return ImmutableLoginResponseDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableLoginResponseDTO ImmutableLoginResponseDTO}.
   * <pre>
   * ImmutableLoginResponseDTO.builder()
   *    .token(String) // required {@link LoginResponseDTO#token() token}
   *    .id(UUID) // required {@link LoginResponseDTO#id() id}
   *    .build();
   * </pre>
   * @return A new ImmutableLoginResponseDTO builder
   */
  public static ImmutableLoginResponseDTO.Builder builder() {
    return new ImmutableLoginResponseDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableLoginResponseDTO ImmutableLoginResponseDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "LoginResponseDTO", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_TOKEN = 0x1L;
    private static final long INIT_BIT_ID = 0x2L;
    private long initBits = 0x3L;

    private @Nullable String token;
    private @Nullable UUID id;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code LoginResponseDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(LoginResponseDTO instance) {
      Objects.requireNonNull(instance, "instance");
      token(instance.token());
      id(instance.id());
      return this;
    }

    /**
     * Initializes the value for the {@link LoginResponseDTO#token() token} attribute.
     * @param token The value for token 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("token")
    public final Builder token(String token) {
      this.token = Objects.requireNonNull(token, "token");
      initBits &= ~INIT_BIT_TOKEN;
      return this;
    }

    /**
     * Initializes the value for the {@link LoginResponseDTO#id() id} attribute.
     * @param id The value for id 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("id")
    public final Builder id(UUID id) {
      this.id = Objects.requireNonNull(id, "id");
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Builds a new {@link ImmutableLoginResponseDTO ImmutableLoginResponseDTO}.
     * @return An immutable instance of LoginResponseDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableLoginResponseDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableLoginResponseDTO(token, id);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_TOKEN) != 0) attributes.add("token");
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      return "Cannot build LoginResponseDTO, some of required attributes are not set " + attributes;
    }
  }
}
