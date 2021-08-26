package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link TestAccountCreateRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTestAccountCreateRequestDTO.builder()}.
 */
@Generated(from = "TestAccountCreateRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableTestAccountCreateRequestDTO
    implements TestAccountCreateRequestDTO {
  private final String userName;
  private final String password;
  private final String firstName;
  private final String lastName;
  private final String role;

  private ImmutableTestAccountCreateRequestDTO(ImmutableTestAccountCreateRequestDTO.Builder builder) {
    this.userName = builder.userName;
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.role = builder.role;
    this.password = builder.password != null
        ? builder.password
        : Objects.requireNonNull(TestAccountCreateRequestDTO.super.password(), "password");
  }

  private ImmutableTestAccountCreateRequestDTO(
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
   * Copy the current immutable object by setting a value for the {@link TestAccountCreateRequestDTO#userName() userName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for userName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestAccountCreateRequestDTO withUserName(String value) {
    String newValue = Objects.requireNonNull(value, "userName");
    if (this.userName.equals(newValue)) return this;
    return new ImmutableTestAccountCreateRequestDTO(newValue, this.password, this.firstName, this.lastName, this.role);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestAccountCreateRequestDTO#password() password} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for password
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestAccountCreateRequestDTO withPassword(String value) {
    String newValue = Objects.requireNonNull(value, "password");
    if (this.password.equals(newValue)) return this;
    return new ImmutableTestAccountCreateRequestDTO(this.userName, newValue, this.firstName, this.lastName, this.role);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestAccountCreateRequestDTO#firstName() firstName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for firstName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestAccountCreateRequestDTO withFirstName(String value) {
    String newValue = Objects.requireNonNull(value, "firstName");
    if (this.firstName.equals(newValue)) return this;
    return new ImmutableTestAccountCreateRequestDTO(this.userName, this.password, newValue, this.lastName, this.role);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestAccountCreateRequestDTO#lastName() lastName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for lastName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestAccountCreateRequestDTO withLastName(String value) {
    String newValue = Objects.requireNonNull(value, "lastName");
    if (this.lastName.equals(newValue)) return this;
    return new ImmutableTestAccountCreateRequestDTO(this.userName, this.password, this.firstName, newValue, this.role);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestAccountCreateRequestDTO#role() role} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for role
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestAccountCreateRequestDTO withRole(String value) {
    String newValue = Objects.requireNonNull(value, "role");
    if (this.role.equals(newValue)) return this;
    return new ImmutableTestAccountCreateRequestDTO(this.userName, this.password, this.firstName, this.lastName, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTestAccountCreateRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTestAccountCreateRequestDTO
        && equalTo(0, (ImmutableTestAccountCreateRequestDTO) another);
  }

  private boolean equalTo(int synthetic, ImmutableTestAccountCreateRequestDTO another) {
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
    int h = 5381;
    h += (h << 5) + userName.hashCode();
    h += (h << 5) + password.hashCode();
    h += (h << 5) + firstName.hashCode();
    h += (h << 5) + lastName.hashCode();
    h += (h << 5) + role.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code TestAccountCreateRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "TestAccountCreateRequestDTO{"
        + "userName=" + userName
        + ", password=" + password
        + ", firstName=" + firstName
        + ", lastName=" + lastName
        + ", role=" + role
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link TestAccountCreateRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TestAccountCreateRequestDTO instance
   */
  public static ImmutableTestAccountCreateRequestDTO copyOf(TestAccountCreateRequestDTO instance) {
    if (instance instanceof ImmutableTestAccountCreateRequestDTO) {
      return (ImmutableTestAccountCreateRequestDTO) instance;
    }
    return ImmutableTestAccountCreateRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTestAccountCreateRequestDTO ImmutableTestAccountCreateRequestDTO}.
   * <pre>
   * ImmutableTestAccountCreateRequestDTO.builder()
   *    .userName(String) // required {@link TestAccountCreateRequestDTO#userName() userName}
   *    .password(String) // optional {@link TestAccountCreateRequestDTO#password() password}
   *    .firstName(String) // required {@link TestAccountCreateRequestDTO#firstName() firstName}
   *    .lastName(String) // required {@link TestAccountCreateRequestDTO#lastName() lastName}
   *    .role(String) // required {@link TestAccountCreateRequestDTO#role() role}
   *    .build();
   * </pre>
   * @return A new ImmutableTestAccountCreateRequestDTO builder
   */
  public static ImmutableTestAccountCreateRequestDTO.Builder builder() {
    return new ImmutableTestAccountCreateRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTestAccountCreateRequestDTO ImmutableTestAccountCreateRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "TestAccountCreateRequestDTO", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_USER_NAME = 0x1L;
    private static final long INIT_BIT_FIRST_NAME = 0x2L;
    private static final long INIT_BIT_LAST_NAME = 0x4L;
    private static final long INIT_BIT_ROLE = 0x8L;
    private long initBits = 0xfL;

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String role;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TestAccountCreateRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(TestAccountCreateRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      userName(instance.userName());
      password(instance.password());
      firstName(instance.firstName());
      lastName(instance.lastName());
      role(instance.role());
      return this;
    }

    /**
     * Initializes the value for the {@link TestAccountCreateRequestDTO#userName() userName} attribute.
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
     * Initializes the value for the {@link TestAccountCreateRequestDTO#password() password} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link TestAccountCreateRequestDTO#password() password}.</em>
     * @param password The value for password 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("password")
    public final Builder password(String password) {
      this.password = Objects.requireNonNull(password, "password");
      return this;
    }

    /**
     * Initializes the value for the {@link TestAccountCreateRequestDTO#firstName() firstName} attribute.
     * @param firstName The value for firstName 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("firstName")
    public final Builder firstName(String firstName) {
      this.firstName = Objects.requireNonNull(firstName, "firstName");
      initBits &= ~INIT_BIT_FIRST_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link TestAccountCreateRequestDTO#lastName() lastName} attribute.
     * @param lastName The value for lastName 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("lastName")
    public final Builder lastName(String lastName) {
      this.lastName = Objects.requireNonNull(lastName, "lastName");
      initBits &= ~INIT_BIT_LAST_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link TestAccountCreateRequestDTO#role() role} attribute.
     * @param role The value for role 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("role")
    public final Builder role(String role) {
      this.role = Objects.requireNonNull(role, "role");
      initBits &= ~INIT_BIT_ROLE;
      return this;
    }

    /**
     * Builds a new {@link ImmutableTestAccountCreateRequestDTO ImmutableTestAccountCreateRequestDTO}.
     * @return An immutable instance of TestAccountCreateRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTestAccountCreateRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableTestAccountCreateRequestDTO(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_USER_NAME) != 0) attributes.add("userName");
      if ((initBits & INIT_BIT_FIRST_NAME) != 0) attributes.add("firstName");
      if ((initBits & INIT_BIT_LAST_NAME) != 0) attributes.add("lastName");
      if ((initBits & INIT_BIT_ROLE) != 0) attributes.add("role");
      return "Cannot build TestAccountCreateRequestDTO, some of required attributes are not set " + attributes;
    }
  }
}
