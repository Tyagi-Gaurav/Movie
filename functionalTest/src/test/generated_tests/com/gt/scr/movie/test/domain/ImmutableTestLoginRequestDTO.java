package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link TestLoginRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTestLoginRequestDTO.builder()}.
 */
@Generated(from = "TestLoginRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableTestLoginRequestDTO implements TestLoginRequestDTO {
  private final String userName;
  private final String password;

  private ImmutableTestLoginRequestDTO(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  /**
   * @return The value of the {@code userName} attribute
   */
  @JsonProperty("userName")
  @Override
  public String userName() {
    return userName;
  }

  /**
   * @return The value of the {@code password} attribute
   */
  @JsonProperty("password")
  @Override
  public String password() {
    return password;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestLoginRequestDTO#userName() userName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for userName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestLoginRequestDTO withUserName(String value) {
    String newValue = Objects.requireNonNull(value, "userName");
    if (this.userName.equals(newValue)) return this;
    return new ImmutableTestLoginRequestDTO(newValue, this.password);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestLoginRequestDTO#password() password} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for password
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestLoginRequestDTO withPassword(String value) {
    String newValue = Objects.requireNonNull(value, "password");
    if (this.password.equals(newValue)) return this;
    return new ImmutableTestLoginRequestDTO(this.userName, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTestLoginRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTestLoginRequestDTO
        && equalTo((ImmutableTestLoginRequestDTO) another);
  }

  private boolean equalTo(ImmutableTestLoginRequestDTO another) {
    return userName.equals(another.userName)
        && password.equals(another.password);
  }

  /**
   * Computes a hash code from attributes: {@code userName}, {@code password}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + userName.hashCode();
    h += (h << 5) + password.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code TestLoginRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "TestLoginRequestDTO{"
        + "userName=" + userName
        + ", password=" + password
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link TestLoginRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TestLoginRequestDTO instance
   */
  public static ImmutableTestLoginRequestDTO copyOf(TestLoginRequestDTO instance) {
    if (instance instanceof ImmutableTestLoginRequestDTO) {
      return (ImmutableTestLoginRequestDTO) instance;
    }
    return ImmutableTestLoginRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTestLoginRequestDTO ImmutableTestLoginRequestDTO}.
   * <pre>
   * ImmutableTestLoginRequestDTO.builder()
   *    .userName(String) // required {@link TestLoginRequestDTO#userName() userName}
   *    .password(String) // required {@link TestLoginRequestDTO#password() password}
   *    .build();
   * </pre>
   * @return A new ImmutableTestLoginRequestDTO builder
   */
  public static ImmutableTestLoginRequestDTO.Builder builder() {
    return new ImmutableTestLoginRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTestLoginRequestDTO ImmutableTestLoginRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "TestLoginRequestDTO", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_USER_NAME = 0x1L;
    private static final long INIT_BIT_PASSWORD = 0x2L;
    private long initBits = 0x3L;

    private String userName;
    private String password;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TestLoginRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(TestLoginRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      userName(instance.userName());
      password(instance.password());
      return this;
    }

    /**
     * Initializes the value for the {@link TestLoginRequestDTO#userName() userName} attribute.
     * @param userName The value for userName 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("userName")
    public final Builder userName(String userName) {
      this.userName = Objects.requireNonNull(userName, "userName");
      initBits &= ~INIT_BIT_USER_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link TestLoginRequestDTO#password() password} attribute.
     * @param password The value for password 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("password")
    public final Builder password(String password) {
      this.password = Objects.requireNonNull(password, "password");
      initBits &= ~INIT_BIT_PASSWORD;
      return this;
    }

    /**
     * Builds a new {@link ImmutableTestLoginRequestDTO ImmutableTestLoginRequestDTO}.
     * @return An immutable instance of TestLoginRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTestLoginRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableTestLoginRequestDTO(userName, password);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_USER_NAME) != 0) attributes.add("userName");
      if ((initBits & INIT_BIT_PASSWORD) != 0) attributes.add("password");
      return "Cannot build TestLoginRequestDTO, some of required attributes are not set " + attributes;
    }
  }
}
