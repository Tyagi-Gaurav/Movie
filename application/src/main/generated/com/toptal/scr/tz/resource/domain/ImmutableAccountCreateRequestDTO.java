package com.toptal.scr.tz.resource.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link AccountCreateRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableAccountCreateRequestDTO.builder()}.
 */
@Generated(from = "AccountCreateRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableAccountCreateRequestDTO
    implements AccountCreateRequestDTO {
  private final String userName;
  private final String password;
  private final String firstName;
  private final String lastName;
  private final List<String> roles;

  private ImmutableAccountCreateRequestDTO(
      String userName,
      String password,
      String firstName,
      String lastName,
      List<String> roles) {
    this.userName = userName;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.roles = roles;
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
   * @return The value of the {@code roles} attribute
   */
  @JsonProperty("roles")
  @Override
  public List<String> roles() {
    return roles;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link AccountCreateRequestDTO#userName() userName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for userName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableAccountCreateRequestDTO withUserName(String value) {
    String newValue = Objects.requireNonNull(value, "userName");
    if (this.userName.equals(newValue)) return this;
    return new ImmutableAccountCreateRequestDTO(newValue, this.password, this.firstName, this.lastName, this.roles);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link AccountCreateRequestDTO#password() password} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for password
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableAccountCreateRequestDTO withPassword(String value) {
    String newValue = Objects.requireNonNull(value, "password");
    if (this.password.equals(newValue)) return this;
    return new ImmutableAccountCreateRequestDTO(this.userName, newValue, this.firstName, this.lastName, this.roles);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link AccountCreateRequestDTO#firstName() firstName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for firstName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableAccountCreateRequestDTO withFirstName(String value) {
    String newValue = Objects.requireNonNull(value, "firstName");
    if (this.firstName.equals(newValue)) return this;
    return new ImmutableAccountCreateRequestDTO(this.userName, this.password, newValue, this.lastName, this.roles);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link AccountCreateRequestDTO#lastName() lastName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for lastName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableAccountCreateRequestDTO withLastName(String value) {
    String newValue = Objects.requireNonNull(value, "lastName");
    if (this.lastName.equals(newValue)) return this;
    return new ImmutableAccountCreateRequestDTO(this.userName, this.password, this.firstName, newValue, this.roles);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link AccountCreateRequestDTO#roles() roles}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableAccountCreateRequestDTO withRoles(String... elements) {
    List<String> newValue = createUnmodifiableList(false, createSafeList(Arrays.asList(elements), true, false));
    return new ImmutableAccountCreateRequestDTO(this.userName, this.password, this.firstName, this.lastName, newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link AccountCreateRequestDTO#roles() roles}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of roles elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableAccountCreateRequestDTO withRoles(Iterable<String> elements) {
    if (this.roles == elements) return this;
    List<String> newValue = createUnmodifiableList(false, createSafeList(elements, true, false));
    return new ImmutableAccountCreateRequestDTO(this.userName, this.password, this.firstName, this.lastName, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableAccountCreateRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableAccountCreateRequestDTO
        && equalTo((ImmutableAccountCreateRequestDTO) another);
  }

  private boolean equalTo(ImmutableAccountCreateRequestDTO another) {
    return userName.equals(another.userName)
        && password.equals(another.password)
        && firstName.equals(another.firstName)
        && lastName.equals(another.lastName)
        && roles.equals(another.roles);
  }

  /**
   * Computes a hash code from attributes: {@code userName}, {@code password}, {@code firstName}, {@code lastName}, {@code roles}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + userName.hashCode();
    h += (h << 5) + password.hashCode();
    h += (h << 5) + firstName.hashCode();
    h += (h << 5) + lastName.hashCode();
    h += (h << 5) + roles.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code AccountCreateRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "AccountCreateRequestDTO{"
        + "userName=" + userName
        + ", password=" + password
        + ", firstName=" + firstName
        + ", lastName=" + lastName
        + ", roles=" + roles
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link AccountCreateRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable AccountCreateRequestDTO instance
   */
  public static ImmutableAccountCreateRequestDTO copyOf(AccountCreateRequestDTO instance) {
    if (instance instanceof ImmutableAccountCreateRequestDTO) {
      return (ImmutableAccountCreateRequestDTO) instance;
    }
    return ImmutableAccountCreateRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableAccountCreateRequestDTO ImmutableAccountCreateRequestDTO}.
   * <pre>
   * ImmutableAccountCreateRequestDTO.builder()
   *    .userName(String) // required {@link AccountCreateRequestDTO#userName() userName}
   *    .password(String) // required {@link AccountCreateRequestDTO#password() password}
   *    .firstName(String) // required {@link AccountCreateRequestDTO#firstName() firstName}
   *    .lastName(String) // required {@link AccountCreateRequestDTO#lastName() lastName}
   *    .addRoles|addAllRoles(String) // {@link AccountCreateRequestDTO#roles() roles} elements
   *    .build();
   * </pre>
   * @return A new ImmutableAccountCreateRequestDTO builder
   */
  public static ImmutableAccountCreateRequestDTO.Builder builder() {
    return new ImmutableAccountCreateRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableAccountCreateRequestDTO ImmutableAccountCreateRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "AccountCreateRequestDTO", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_USER_NAME = 0x1L;
    private static final long INIT_BIT_PASSWORD = 0x2L;
    private static final long INIT_BIT_FIRST_NAME = 0x4L;
    private static final long INIT_BIT_LAST_NAME = 0x8L;
    private long initBits = 0xfL;

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private List<String> roles = new ArrayList<String>();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code AccountCreateRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(AccountCreateRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      userName(instance.userName());
      password(instance.password());
      firstName(instance.firstName());
      lastName(instance.lastName());
      addAllRoles(instance.roles());
      return this;
    }

    /**
     * Initializes the value for the {@link AccountCreateRequestDTO#userName() userName} attribute.
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
     * Initializes the value for the {@link AccountCreateRequestDTO#password() password} attribute.
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
     * Initializes the value for the {@link AccountCreateRequestDTO#firstName() firstName} attribute.
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
     * Initializes the value for the {@link AccountCreateRequestDTO#lastName() lastName} attribute.
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
     * Adds one element to {@link AccountCreateRequestDTO#roles() roles} list.
     * @param element A roles element
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addRoles(String element) {
      this.roles.add(Objects.requireNonNull(element, "roles element"));
      return this;
    }

    /**
     * Adds elements to {@link AccountCreateRequestDTO#roles() roles} list.
     * @param elements An array of roles elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addRoles(String... elements) {
      for (String element : elements) {
        this.roles.add(Objects.requireNonNull(element, "roles element"));
      }
      return this;
    }


    /**
     * Sets or replaces all elements for {@link AccountCreateRequestDTO#roles() roles} list.
     * @param elements An iterable of roles elements
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("roles")
    public final Builder roles(Iterable<String> elements) {
      this.roles.clear();
      return addAllRoles(elements);
    }

    /**
     * Adds elements to {@link AccountCreateRequestDTO#roles() roles} list.
     * @param elements An iterable of roles elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addAllRoles(Iterable<String> elements) {
      for (String element : elements) {
        this.roles.add(Objects.requireNonNull(element, "roles element"));
      }
      return this;
    }

    /**
     * Builds a new {@link ImmutableAccountCreateRequestDTO ImmutableAccountCreateRequestDTO}.
     * @return An immutable instance of AccountCreateRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableAccountCreateRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableAccountCreateRequestDTO(userName, password, firstName, lastName, createUnmodifiableList(true, roles));
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_USER_NAME) != 0) attributes.add("userName");
      if ((initBits & INIT_BIT_PASSWORD) != 0) attributes.add("password");
      if ((initBits & INIT_BIT_FIRST_NAME) != 0) attributes.add("firstName");
      if ((initBits & INIT_BIT_LAST_NAME) != 0) attributes.add("lastName");
      return "Cannot build AccountCreateRequestDTO, some of required attributes are not set " + attributes;
    }
  }

  private static <T> List<T> createSafeList(Iterable<? extends T> iterable, boolean checkNulls, boolean skipNulls) {
    ArrayList<T> list;
    if (iterable instanceof Collection<?>) {
      int size = ((Collection<?>) iterable).size();
      if (size == 0) return Collections.emptyList();
      list = new ArrayList<>();
    } else {
      list = new ArrayList<>();
    }
    for (T element : iterable) {
      if (skipNulls && element == null) continue;
      if (checkNulls) Objects.requireNonNull(element, "element");
      list.add(element);
    }
    return list;
  }

  private static <T> List<T> createUnmodifiableList(boolean clone, List<T> list) {
    switch(list.size()) {
    case 0: return Collections.emptyList();
    case 1: return Collections.singletonList(list.get(0));
    default:
      if (clone) {
        return Collections.unmodifiableList(new ArrayList<>(list));
      } else {
        if (list instanceof ArrayList<?>) {
          ((ArrayList<?>) list).trimToSize();
        }
        return Collections.unmodifiableList(list);
      }
    }
  }
}
