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
 * Immutable implementation of {@link UserDetailsResponse}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableUserDetailsResponse.builder()}.
 */
@Generated(from = "UserDetailsResponse", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableUserDetailsResponse
    implements UserDetailsResponse {
  private final String userName;
  private final String firstName;
  private final String lastName;
  private final String role;
  private final UUID id;

  private ImmutableUserDetailsResponse(
      String userName,
      String firstName,
      String lastName,
      String role,
      UUID id) {
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.id = id;
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
   * @return The value of the {@code id} attribute
   */
  @JsonProperty("id")
  @Override
  public UUID id() {
    return id;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link UserDetailsResponse#userName() userName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for userName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUserDetailsResponse withUserName(String value) {
    String newValue = Objects.requireNonNull(value, "userName");
    if (this.userName.equals(newValue)) return this;
    return new ImmutableUserDetailsResponse(newValue, this.firstName, this.lastName, this.role, this.id);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link UserDetailsResponse#firstName() firstName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for firstName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUserDetailsResponse withFirstName(String value) {
    String newValue = Objects.requireNonNull(value, "firstName");
    if (this.firstName.equals(newValue)) return this;
    return new ImmutableUserDetailsResponse(this.userName, newValue, this.lastName, this.role, this.id);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link UserDetailsResponse#lastName() lastName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for lastName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUserDetailsResponse withLastName(String value) {
    String newValue = Objects.requireNonNull(value, "lastName");
    if (this.lastName.equals(newValue)) return this;
    return new ImmutableUserDetailsResponse(this.userName, this.firstName, newValue, this.role, this.id);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link UserDetailsResponse#role() role} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for role
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUserDetailsResponse withRole(String value) {
    String newValue = Objects.requireNonNull(value, "role");
    if (this.role.equals(newValue)) return this;
    return new ImmutableUserDetailsResponse(this.userName, this.firstName, this.lastName, newValue, this.id);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link UserDetailsResponse#id() id} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUserDetailsResponse withId(UUID value) {
    if (this.id == value) return this;
    UUID newValue = Objects.requireNonNull(value, "id");
    return new ImmutableUserDetailsResponse(this.userName, this.firstName, this.lastName, this.role, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableUserDetailsResponse} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableUserDetailsResponse
        && equalTo((ImmutableUserDetailsResponse) another);
  }

  private boolean equalTo(ImmutableUserDetailsResponse another) {
    return userName.equals(another.userName)
        && firstName.equals(another.firstName)
        && lastName.equals(another.lastName)
        && role.equals(another.role)
        && id.equals(another.id);
  }

  /**
   * Computes a hash code from attributes: {@code userName}, {@code firstName}, {@code lastName}, {@code role}, {@code id}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + userName.hashCode();
    h += (h << 5) + firstName.hashCode();
    h += (h << 5) + lastName.hashCode();
    h += (h << 5) + role.hashCode();
    h += (h << 5) + id.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code UserDetailsResponse} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("UserDetailsResponse")
        .omitNullValues()
        .add("userName", userName)
        .add("firstName", firstName)
        .add("lastName", lastName)
        .add("role", role)
        .add("id", id)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link UserDetailsResponse} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable UserDetailsResponse instance
   */
  public static ImmutableUserDetailsResponse copyOf(UserDetailsResponse instance) {
    if (instance instanceof ImmutableUserDetailsResponse) {
      return (ImmutableUserDetailsResponse) instance;
    }
    return ImmutableUserDetailsResponse.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableUserDetailsResponse ImmutableUserDetailsResponse}.
   * <pre>
   * ImmutableUserDetailsResponse.builder()
   *    .userName(String) // required {@link UserDetailsResponse#userName() userName}
   *    .firstName(String) // required {@link UserDetailsResponse#firstName() firstName}
   *    .lastName(String) // required {@link UserDetailsResponse#lastName() lastName}
   *    .role(String) // required {@link UserDetailsResponse#role() role}
   *    .id(UUID) // required {@link UserDetailsResponse#id() id}
   *    .build();
   * </pre>
   * @return A new ImmutableUserDetailsResponse builder
   */
  public static ImmutableUserDetailsResponse.Builder builder() {
    return new ImmutableUserDetailsResponse.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableUserDetailsResponse ImmutableUserDetailsResponse}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "UserDetailsResponse", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_USER_NAME = 0x1L;
    private static final long INIT_BIT_FIRST_NAME = 0x2L;
    private static final long INIT_BIT_LAST_NAME = 0x4L;
    private static final long INIT_BIT_ROLE = 0x8L;
    private static final long INIT_BIT_ID = 0x10L;
    private long initBits = 0x1fL;

    private @Nullable String userName;
    private @Nullable String firstName;
    private @Nullable String lastName;
    private @Nullable String role;
    private @Nullable UUID id;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code UserDetailsResponse} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(UserDetailsResponse instance) {
      Objects.requireNonNull(instance, "instance");
      userName(instance.userName());
      firstName(instance.firstName());
      lastName(instance.lastName());
      role(instance.role());
      id(instance.id());
      return this;
    }

    /**
     * Initializes the value for the {@link UserDetailsResponse#userName() userName} attribute.
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
     * Initializes the value for the {@link UserDetailsResponse#firstName() firstName} attribute.
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
     * Initializes the value for the {@link UserDetailsResponse#lastName() lastName} attribute.
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
     * Initializes the value for the {@link UserDetailsResponse#role() role} attribute.
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
     * Initializes the value for the {@link UserDetailsResponse#id() id} attribute.
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
     * Builds a new {@link ImmutableUserDetailsResponse ImmutableUserDetailsResponse}.
     * @return An immutable instance of UserDetailsResponse
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableUserDetailsResponse build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableUserDetailsResponse(userName, firstName, lastName, role, id);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_USER_NAME) != 0) attributes.add("userName");
      if ((initBits & INIT_BIT_FIRST_NAME) != 0) attributes.add("firstName");
      if ((initBits & INIT_BIT_LAST_NAME) != 0) attributes.add("lastName");
      if ((initBits & INIT_BIT_ROLE) != 0) attributes.add("role");
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      return "Cannot build UserDetailsResponse, some of required attributes are not set " + attributes;
    }
  }
}
