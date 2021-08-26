package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link TestLoginResponseDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTestLoginResponseDTO.builder()}.
 */
@Generated(from = "TestLoginResponseDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableTestLoginResponseDTO implements TestLoginResponseDTO {
  private final String token;
  private final String id;

  private ImmutableTestLoginResponseDTO(String token, String id) {
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
  public String id() {
    return id;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestLoginResponseDTO#token() token} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for token
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestLoginResponseDTO withToken(String value) {
    String newValue = Objects.requireNonNull(value, "token");
    if (this.token.equals(newValue)) return this;
    return new ImmutableTestLoginResponseDTO(newValue, this.id);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestLoginResponseDTO#id() id} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestLoginResponseDTO withId(String value) {
    String newValue = Objects.requireNonNull(value, "id");
    if (this.id.equals(newValue)) return this;
    return new ImmutableTestLoginResponseDTO(this.token, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTestLoginResponseDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTestLoginResponseDTO
        && equalTo(0, (ImmutableTestLoginResponseDTO) another);
  }

  private boolean equalTo(int synthetic, ImmutableTestLoginResponseDTO another) {
    return token.equals(another.token)
        && id.equals(another.id);
  }

  /**
   * Computes a hash code from attributes: {@code token}, {@code id}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + token.hashCode();
    h += (h << 5) + id.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code TestLoginResponseDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "TestLoginResponseDTO{"
        + "token=" + token
        + ", id=" + id
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link TestLoginResponseDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TestLoginResponseDTO instance
   */
  public static ImmutableTestLoginResponseDTO copyOf(TestLoginResponseDTO instance) {
    if (instance instanceof ImmutableTestLoginResponseDTO) {
      return (ImmutableTestLoginResponseDTO) instance;
    }
    return ImmutableTestLoginResponseDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTestLoginResponseDTO ImmutableTestLoginResponseDTO}.
   * <pre>
   * ImmutableTestLoginResponseDTO.builder()
   *    .token(String) // required {@link TestLoginResponseDTO#token() token}
   *    .id(String) // required {@link TestLoginResponseDTO#id() id}
   *    .build();
   * </pre>
   * @return A new ImmutableTestLoginResponseDTO builder
   */
  public static ImmutableTestLoginResponseDTO.Builder builder() {
    return new ImmutableTestLoginResponseDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTestLoginResponseDTO ImmutableTestLoginResponseDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "TestLoginResponseDTO", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_TOKEN = 0x1L;
    private static final long INIT_BIT_ID = 0x2L;
    private long initBits = 0x3L;

    private String token;
    private String id;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TestLoginResponseDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(TestLoginResponseDTO instance) {
      Objects.requireNonNull(instance, "instance");
      token(instance.token());
      id(instance.id());
      return this;
    }

    /**
     * Initializes the value for the {@link TestLoginResponseDTO#token() token} attribute.
     * @param token The value for token 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("token")
    public final Builder token(String token) {
      this.token = Objects.requireNonNull(token, "token");
      initBits &= ~INIT_BIT_TOKEN;
      return this;
    }

    /**
     * Initializes the value for the {@link TestLoginResponseDTO#id() id} attribute.
     * @param id The value for id 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("id")
    public final Builder id(String id) {
      this.id = Objects.requireNonNull(id, "id");
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Builds a new {@link ImmutableTestLoginResponseDTO ImmutableTestLoginResponseDTO}.
     * @return An immutable instance of TestLoginResponseDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTestLoginResponseDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableTestLoginResponseDTO(token, id);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_TOKEN) != 0) attributes.add("token");
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      return "Cannot build TestLoginResponseDTO, some of required attributes are not set " + attributes;
    }
  }
}
