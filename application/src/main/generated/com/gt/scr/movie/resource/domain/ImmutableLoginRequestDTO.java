package com.gt.scr.movie.resource.domain;

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
 * Immutable implementation of {@link LoginRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableLoginRequestDTO.builder()}.
 */
@Generated(from = "LoginRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableLoginRequestDTO implements LoginRequestDTO {
  private final String userName;
  private final String password;

  private ImmutableLoginRequestDTO(String userName, String password) {
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
   * Copy the current immutable object by setting a value for the {@link LoginRequestDTO#userName() userName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for userName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableLoginRequestDTO withUserName(String value) {
    String newValue = Objects.requireNonNull(value, "userName");
    if (this.userName.equals(newValue)) return this;
    return new ImmutableLoginRequestDTO(newValue, this.password);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link LoginRequestDTO#password() password} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for password
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableLoginRequestDTO withPassword(String value) {
    String newValue = Objects.requireNonNull(value, "password");
    if (this.password.equals(newValue)) return this;
    return new ImmutableLoginRequestDTO(this.userName, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableLoginRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableLoginRequestDTO
        && equalTo((ImmutableLoginRequestDTO) another);
  }

  private boolean equalTo(ImmutableLoginRequestDTO another) {
    return userName.equals(another.userName)
        && password.equals(another.password);
  }

  /**
   * Computes a hash code from attributes: {@code userName}, {@code password}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + userName.hashCode();
    h += (h << 5) + password.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code LoginRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("LoginRequestDTO")
        .omitNullValues()
        .add("userName", userName)
        .add("password", password)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link LoginRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable LoginRequestDTO instance
   */
  public static ImmutableLoginRequestDTO copyOf(LoginRequestDTO instance) {
    if (instance instanceof ImmutableLoginRequestDTO) {
      return (ImmutableLoginRequestDTO) instance;
    }
    return ImmutableLoginRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableLoginRequestDTO ImmutableLoginRequestDTO}.
   * <pre>
   * ImmutableLoginRequestDTO.builder()
   *    .userName(String) // required {@link LoginRequestDTO#userName() userName}
   *    .password(String) // required {@link LoginRequestDTO#password() password}
   *    .build();
   * </pre>
   * @return A new ImmutableLoginRequestDTO builder
   */
  public static ImmutableLoginRequestDTO.Builder builder() {
    return new ImmutableLoginRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableLoginRequestDTO ImmutableLoginRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "LoginRequestDTO", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_USER_NAME = 0x1L;
    private static final long INIT_BIT_PASSWORD = 0x2L;
    private long initBits = 0x3L;

    private @Nullable String userName;
    private @Nullable String password;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code LoginRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(LoginRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      userName(instance.userName());
      password(instance.password());
      return this;
    }

    /**
     * Initializes the value for the {@link LoginRequestDTO#userName() userName} attribute.
     * @param userName The value for userName 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("userName")
    public final Builder userName(String userName) {
      this.userName = Objects.requireNonNull(userName, "userName");
      initBits &= ~INIT_BIT_USER_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link LoginRequestDTO#password() password} attribute.
     * @param password The value for password 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("password")
    public final Builder password(String password) {
      this.password = Objects.requireNonNull(password, "password");
      initBits &= ~INIT_BIT_PASSWORD;
      return this;
    }

    /**
     * Builds a new {@link ImmutableLoginRequestDTO ImmutableLoginRequestDTO}.
     * @return An immutable instance of LoginRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableLoginRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableLoginRequestDTO(userName, password);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_USER_NAME) != 0) attributes.add("userName");
      if ((initBits & INIT_BIT_PASSWORD) != 0) attributes.add("password");
      return "Cannot build LoginRequestDTO, some of required attributes are not set " + attributes;
    }
  }
}
