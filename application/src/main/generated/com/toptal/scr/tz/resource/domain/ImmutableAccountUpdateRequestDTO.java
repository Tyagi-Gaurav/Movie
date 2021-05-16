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
 * Immutable implementation of {@link AccountUpdateRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableAccountUpdateRequestDTO.builder()}.
 */
@Generated(from = "AccountUpdateRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableAccountUpdateRequestDTO
    implements AccountUpdateRequestDTO {
  private final String userName;
  private final String password;
  private final String firstName;
  private final String lastName;
  private final String role;

  private ImmutableAccountUpdateRequestDTO(
      String userName,
      String password,
      String firstName,
      String lastName,
      String role) {
    this.userName = userName;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
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
   * @return The value of the {@code firstName} attribute
   */
  @JsonProperty("firstName")
  @Override
  public String firstName() {
    return firstName;
  }

  /**
   * @return The value of the {@code lastName} attribute
   */
  @JsonProperty("lastName")
  @Override
  public String lastName() {
    return lastName;
  }

  /**
   * @return The value of the {@code role} attribute
   */
  @JsonProperty("role")
  @Override
  public String role() {
    return role;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link AccountUpdateRequestDTO#userName() userName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for userName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableAccountUpdateRequestDTO withUserName(String value) {
    String newValue = Objects.requireNonNull(value, "userName");
    if (this.userName.equals(newValue)) return this;
    return new ImmutableAccountUpdateRequestDTO(newValue, this.password, this.firstName, this.lastName, this.role);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link AccountUpdateRequestDTO#password() password} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for password
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableAccountUpdateRequestDTO withPassword(String value) {
    String newValue = Objects.requireNonNull(value, "password");
    if (this.password.equals(newValue)) return this;
    return new ImmutableAccountUpdateRequestDTO(this.userName, newValue, this.firstName, this.lastName, this.role);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link AccountUpdateRequestDTO#firstName() firstName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for firstName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableAccountUpdateRequestDTO withFirstName(String value) {
    String newValue = Objects.requireNonNull(value, "firstName");
    if (this.firstName.equals(newValue)) return this;
    return new ImmutableAccountUpdateRequestDTO(this.userName, this.password, newValue, this.lastName, this.role);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link AccountUpdateRequestDTO#lastName() lastName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for lastName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableAccountUpdateRequestDTO withLastName(String value) {
    String newValue = Objects.requireNonNull(value, "lastName");
    if (this.lastName.equals(newValue)) return this;
    return new ImmutableAccountUpdateRequestDTO(this.userName, this.password, this.firstName, newValue, this.role);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link AccountUpdateRequestDTO#role() role} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for role
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableAccountUpdateRequestDTO withRole(String value) {
    String newValue = Objects.requireNonNull(value, "role");
    if (this.role.equals(newValue)) return this;
    return new ImmutableAccountUpdateRequestDTO(this.userName, this.password, this.firstName, this.lastName, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableAccountUpdateRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableAccountUpdateRequestDTO
        && equalTo((ImmutableAccountUpdateRequestDTO) another);
  }

  private boolean equalTo(ImmutableAccountUpdateRequestDTO another) {
    return userName.equals(another.userName)
        && password.equals(another.password)
        && firstName.equals(another.firstName)
        && lastName.equals(another.lastName)
        && role.equals(another.role);
  }

  /**
   * Computes a hash code from attributes: {@code userName}, {@code password}, {@code firstName}, {@code lastName}, {@code role}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + userName.hashCode();
    h += (h << 5) + password.hashCode();
    h += (h << 5) + firstName.hashCode();
    h += (h << 5) + lastName.hashCode();
    h += (h << 5) + role.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code AccountUpdateRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("AccountUpdateRequestDTO")
        .omitNullValues()
        .add("userName", userName)
        .add("password", password)
        .add("firstName", firstName)
        .add("lastName", lastName)
        .add("role", role)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link AccountUpdateRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable AccountUpdateRequestDTO instance
   */
  public static ImmutableAccountUpdateRequestDTO copyOf(AccountUpdateRequestDTO instance) {
    if (instance instanceof ImmutableAccountUpdateRequestDTO) {
      return (ImmutableAccountUpdateRequestDTO) instance;
    }
    return ImmutableAccountUpdateRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableAccountUpdateRequestDTO ImmutableAccountUpdateRequestDTO}.
   * <pre>
   * ImmutableAccountUpdateRequestDTO.builder()
   *    .userName(String) // required {@link AccountUpdateRequestDTO#userName() userName}
   *    .password(String) // required {@link AccountUpdateRequestDTO#password() password}
   *    .firstName(String) // required {@link AccountUpdateRequestDTO#firstName() firstName}
   *    .lastName(String) // required {@link AccountUpdateRequestDTO#lastName() lastName}
   *    .role(String) // required {@link AccountUpdateRequestDTO#role() role}
   *    .build();
   * </pre>
   * @return A new ImmutableAccountUpdateRequestDTO builder
   */
  public static ImmutableAccountUpdateRequestDTO.Builder builder() {
    return new ImmutableAccountUpdateRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableAccountUpdateRequestDTO ImmutableAccountUpdateRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "AccountUpdateRequestDTO", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_USER_NAME = 0x1L;
    private static final long INIT_BIT_PASSWORD = 0x2L;
    private static final long INIT_BIT_FIRST_NAME = 0x4L;
    private static final long INIT_BIT_LAST_NAME = 0x8L;
    private static final long INIT_BIT_ROLE = 0x10L;
    private long initBits = 0x1fL;

    private @Nullable String userName;
    private @Nullable String password;
    private @Nullable String firstName;
    private @Nullable String lastName;
    private @Nullable String role;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code AccountUpdateRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(AccountUpdateRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      userName(instance.userName());
      password(instance.password());
      firstName(instance.firstName());
      lastName(instance.lastName());
      role(instance.role());
      return this;
    }

    /**
     * Initializes the value for the {@link AccountUpdateRequestDTO#userName() userName} attribute.
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
     * Initializes the value for the {@link AccountUpdateRequestDTO#password() password} attribute.
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
     * Initializes the value for the {@link AccountUpdateRequestDTO#firstName() firstName} attribute.
     * @param firstName The value for firstName 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("firstName")
    public final Builder firstName(String firstName) {
      this.firstName = Objects.requireNonNull(firstName, "firstName");
      initBits &= ~INIT_BIT_FIRST_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link AccountUpdateRequestDTO#lastName() lastName} attribute.
     * @param lastName The value for lastName 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("lastName")
    public final Builder lastName(String lastName) {
      this.lastName = Objects.requireNonNull(lastName, "lastName");
      initBits &= ~INIT_BIT_LAST_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link AccountUpdateRequestDTO#role() role} attribute.
     * @param role The value for role 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("role")
    public final Builder role(String role) {
      this.role = Objects.requireNonNull(role, "role");
      initBits &= ~INIT_BIT_ROLE;
      return this;
    }

    /**
     * Builds a new {@link ImmutableAccountUpdateRequestDTO ImmutableAccountUpdateRequestDTO}.
     * @return An immutable instance of AccountUpdateRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableAccountUpdateRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableAccountUpdateRequestDTO(userName, password, firstName, lastName, role);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_USER_NAME) != 0) attributes.add("userName");
      if ((initBits & INIT_BIT_PASSWORD) != 0) attributes.add("password");
      if ((initBits & INIT_BIT_FIRST_NAME) != 0) attributes.add("firstName");
      if ((initBits & INIT_BIT_LAST_NAME) != 0) attributes.add("lastName");
      if ((initBits & INIT_BIT_ROLE) != 0) attributes.add("role");
      return "Cannot build AccountUpdateRequestDTO, some of required attributes are not set " + attributes;
    }
  }
}
