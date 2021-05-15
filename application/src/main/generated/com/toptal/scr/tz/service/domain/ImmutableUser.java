package com.toptal.scr.tz.service.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.immutables.value.Generated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Immutable implementation of {@link User}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableUser.builder()}.
 */
@Generated(from = "User", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableUser implements User {
  private final Collection<? extends GrantedAuthority> authorities;
  private final String password;
  private final String username;
  private final boolean isAccountNonExpired;
  private final boolean isAccountNonLocked;
  private final boolean isCredentialsNonExpired;
  private final boolean isEnabled;
  private final UUID id;
  private final String firstName;
  private final String lastName;
  private final List<String> authorityNames;

  private ImmutableUser(
      Collection<? extends GrantedAuthority> authorities,
      String password,
      String username,
      boolean isAccountNonExpired,
      boolean isAccountNonLocked,
      boolean isCredentialsNonExpired,
      boolean isEnabled,
      UUID id,
      String firstName,
      String lastName,
      List<String> authorityNames) {
    this.authorities = authorities;
    this.password = password;
    this.username = username;
    this.isAccountNonExpired = isAccountNonExpired;
    this.isAccountNonLocked = isAccountNonLocked;
    this.isCredentialsNonExpired = isCredentialsNonExpired;
    this.isEnabled = isEnabled;
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.authorityNames = authorityNames;
  }

  /**
   * @return The value of the {@code authorities} attribute
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  /**
   * @return The value of the {@code password} attribute
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * @return The value of the {@code username} attribute
   */
  @Override
  public String getUsername() {
    return username;
  }

  /**
   * @return The value of the {@code isAccountNonExpired} attribute
   */
  @Override
  public boolean isAccountNonExpired() {
    return isAccountNonExpired;
  }

  /**
   * @return The value of the {@code isAccountNonLocked} attribute
   */
  @Override
  public boolean isAccountNonLocked() {
    return isAccountNonLocked;
  }

  /**
   * @return The value of the {@code isCredentialsNonExpired} attribute
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return isCredentialsNonExpired;
  }

  /**
   * @return The value of the {@code isEnabled} attribute
   */
  @Override
  public boolean isEnabled() {
    return isEnabled;
  }

  /**
   * @return The value of the {@code id} attribute
   */
  @Override
  public UUID id() {
    return id;
  }

  /**
   * @return The value of the {@code firstName} attribute
   */
  @Override
  public String firstName() {
    return firstName;
  }

  /**
   * @return The value of the {@code lastName} attribute
   */
  @Override
  public String lastName() {
    return lastName;
  }

  /**
   * @return The value of the {@code authorityNames} attribute
   */
  @Override
  public List<String> authorityNames() {
    return authorityNames;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link User#getAuthorities() authorities} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for authorities
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUser withAuthorities(Collection<? extends GrantedAuthority> value) {
    if (this.authorities == value) return this;
    Collection<? extends GrantedAuthority> newValue = Objects.requireNonNull(value, "authorities");
    return new ImmutableUser(
        newValue,
        this.password,
        this.username,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.id,
        this.firstName,
        this.lastName,
        this.authorityNames);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link User#getPassword() password} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for password
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUser withPassword(String value) {
    String newValue = Objects.requireNonNull(value, "password");
    if (this.password.equals(newValue)) return this;
    return new ImmutableUser(
        this.authorities,
        newValue,
        this.username,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.id,
        this.firstName,
        this.lastName,
        this.authorityNames);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link User#getUsername() username} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for username
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUser withUsername(String value) {
    String newValue = Objects.requireNonNull(value, "username");
    if (this.username.equals(newValue)) return this;
    return new ImmutableUser(
        this.authorities,
        this.password,
        newValue,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.id,
        this.firstName,
        this.lastName,
        this.authorityNames);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link User#isAccountNonExpired() isAccountNonExpired} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isAccountNonExpired
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUser withIsAccountNonExpired(boolean value) {
    if (this.isAccountNonExpired == value) return this;
    return new ImmutableUser(
        this.authorities,
        this.password,
        this.username,
        value,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.id,
        this.firstName,
        this.lastName,
        this.authorityNames);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link User#isAccountNonLocked() isAccountNonLocked} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isAccountNonLocked
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUser withIsAccountNonLocked(boolean value) {
    if (this.isAccountNonLocked == value) return this;
    return new ImmutableUser(
        this.authorities,
        this.password,
        this.username,
        this.isAccountNonExpired,
        value,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.id,
        this.firstName,
        this.lastName,
        this.authorityNames);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link User#isCredentialsNonExpired() isCredentialsNonExpired} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isCredentialsNonExpired
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUser withIsCredentialsNonExpired(boolean value) {
    if (this.isCredentialsNonExpired == value) return this;
    return new ImmutableUser(
        this.authorities,
        this.password,
        this.username,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        value,
        this.isEnabled,
        this.id,
        this.firstName,
        this.lastName,
        this.authorityNames);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link User#isEnabled() isEnabled} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isEnabled
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUser withIsEnabled(boolean value) {
    if (this.isEnabled == value) return this;
    return new ImmutableUser(
        this.authorities,
        this.password,
        this.username,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        value,
        this.id,
        this.firstName,
        this.lastName,
        this.authorityNames);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link User#id() id} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUser withId(UUID value) {
    if (this.id == value) return this;
    UUID newValue = Objects.requireNonNull(value, "id");
    return new ImmutableUser(
        this.authorities,
        this.password,
        this.username,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        newValue,
        this.firstName,
        this.lastName,
        this.authorityNames);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link User#firstName() firstName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for firstName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUser withFirstName(String value) {
    String newValue = Objects.requireNonNull(value, "firstName");
    if (this.firstName.equals(newValue)) return this;
    return new ImmutableUser(
        this.authorities,
        this.password,
        this.username,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.id,
        newValue,
        this.lastName,
        this.authorityNames);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link User#lastName() lastName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for lastName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUser withLastName(String value) {
    String newValue = Objects.requireNonNull(value, "lastName");
    if (this.lastName.equals(newValue)) return this;
    return new ImmutableUser(
        this.authorities,
        this.password,
        this.username,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.id,
        this.firstName,
        newValue,
        this.authorityNames);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link User#authorityNames() authorityNames}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableUser withAuthorityNames(String... elements) {
    List<String> newValue = createUnmodifiableList(false, createSafeList(Arrays.asList(elements), true, false));
    return new ImmutableUser(
        this.authorities,
        this.password,
        this.username,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.id,
        this.firstName,
        this.lastName,
        newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link User#authorityNames() authorityNames}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of authorityNames elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableUser withAuthorityNames(Iterable<String> elements) {
    if (this.authorityNames == elements) return this;
    List<String> newValue = createUnmodifiableList(false, createSafeList(elements, true, false));
    return new ImmutableUser(
        this.authorities,
        this.password,
        this.username,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.id,
        this.firstName,
        this.lastName,
        newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableUser} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableUser
        && equalTo((ImmutableUser) another);
  }

  private boolean equalTo(ImmutableUser another) {
    return authorities.equals(another.authorities)
        && password.equals(another.password)
        && username.equals(another.username)
        && isAccountNonExpired == another.isAccountNonExpired
        && isAccountNonLocked == another.isAccountNonLocked
        && isCredentialsNonExpired == another.isCredentialsNonExpired
        && isEnabled == another.isEnabled
        && id.equals(another.id)
        && firstName.equals(another.firstName)
        && lastName.equals(another.lastName)
        && authorityNames.equals(another.authorityNames);
  }

  /**
   * Computes a hash code from attributes: {@code authorities}, {@code password}, {@code username}, {@code isAccountNonExpired}, {@code isAccountNonLocked}, {@code isCredentialsNonExpired}, {@code isEnabled}, {@code id}, {@code firstName}, {@code lastName}, {@code authorityNames}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + authorities.hashCode();
    h += (h << 5) + password.hashCode();
    h += (h << 5) + username.hashCode();
    h += (h << 5) + Boolean.hashCode(isAccountNonExpired);
    h += (h << 5) + Boolean.hashCode(isAccountNonLocked);
    h += (h << 5) + Boolean.hashCode(isCredentialsNonExpired);
    h += (h << 5) + Boolean.hashCode(isEnabled);
    h += (h << 5) + id.hashCode();
    h += (h << 5) + firstName.hashCode();
    h += (h << 5) + lastName.hashCode();
    h += (h << 5) + authorityNames.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code User} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "User{"
        + "authorities=" + authorities
        + ", password=" + password
        + ", username=" + username
        + ", isAccountNonExpired=" + isAccountNonExpired
        + ", isAccountNonLocked=" + isAccountNonLocked
        + ", isCredentialsNonExpired=" + isCredentialsNonExpired
        + ", isEnabled=" + isEnabled
        + ", id=" + id
        + ", firstName=" + firstName
        + ", lastName=" + lastName
        + ", authorityNames=" + authorityNames
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link User} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable User instance
   */
  public static ImmutableUser copyOf(User instance) {
    if (instance instanceof ImmutableUser) {
      return (ImmutableUser) instance;
    }
    return ImmutableUser.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableUser ImmutableUser}.
   * <pre>
   * ImmutableUser.builder()
   *    .authorities(Collection&amp;lt;? extends org.springframework.security.core.GrantedAuthority&amp;gt;) // required {@link User#getAuthorities() authorities}
   *    .password(String) // required {@link User#getPassword() password}
   *    .username(String) // required {@link User#getUsername() username}
   *    .isAccountNonExpired(boolean) // required {@link User#isAccountNonExpired() isAccountNonExpired}
   *    .isAccountNonLocked(boolean) // required {@link User#isAccountNonLocked() isAccountNonLocked}
   *    .isCredentialsNonExpired(boolean) // required {@link User#isCredentialsNonExpired() isCredentialsNonExpired}
   *    .isEnabled(boolean) // required {@link User#isEnabled() isEnabled}
   *    .id(UUID) // required {@link User#id() id}
   *    .firstName(String) // required {@link User#firstName() firstName}
   *    .lastName(String) // required {@link User#lastName() lastName}
   *    .addAuthorityNames|addAllAuthorityNames(String) // {@link User#authorityNames() authorityNames} elements
   *    .build();
   * </pre>
   * @return A new ImmutableUser builder
   */
  public static ImmutableUser.Builder builder() {
    return new ImmutableUser.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableUser ImmutableUser}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "User", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_AUTHORITIES = 0x1L;
    private static final long INIT_BIT_PASSWORD = 0x2L;
    private static final long INIT_BIT_USERNAME = 0x4L;
    private static final long INIT_BIT_IS_ACCOUNT_NON_EXPIRED = 0x8L;
    private static final long INIT_BIT_IS_ACCOUNT_NON_LOCKED = 0x10L;
    private static final long INIT_BIT_IS_CREDENTIALS_NON_EXPIRED = 0x20L;
    private static final long INIT_BIT_IS_ENABLED = 0x40L;
    private static final long INIT_BIT_ID = 0x80L;
    private static final long INIT_BIT_FIRST_NAME = 0x100L;
    private static final long INIT_BIT_LAST_NAME = 0x200L;
    private long initBits = 0x3ffL;

    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private UUID id;
    private String firstName;
    private String lastName;
    private List<String> authorityNames = new ArrayList<String>();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code com.toptal.scr.tz.service.domain.User} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(User instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.springframework.security.core.userdetails.UserDetails} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(UserDetails instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof User) {
        User instance = (User) object;
        addAllAuthorityNames(instance.authorityNames());
        firstName(instance.firstName());
        lastName(instance.lastName());
        id(instance.id());
      }
      if (object instanceof UserDetails) {
        UserDetails instance = (UserDetails) object;
        password(instance.getPassword());
        isAccountNonExpired(instance.isAccountNonExpired());
        isCredentialsNonExpired(instance.isCredentialsNonExpired());
        isEnabled(instance.isEnabled());
        isAccountNonLocked(instance.isAccountNonLocked());
        authorities(instance.getAuthorities());
        username(instance.getUsername());
      }
    }

    /**
     * Initializes the value for the {@link User#getAuthorities() authorities} attribute.
     * @param authorities The value for authorities 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder authorities(Collection<? extends GrantedAuthority> authorities) {
      this.authorities = Objects.requireNonNull(authorities, "authorities");
      initBits &= ~INIT_BIT_AUTHORITIES;
      return this;
    }

    /**
     * Initializes the value for the {@link User#getPassword() password} attribute.
     * @param password The value for password 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder password(String password) {
      this.password = Objects.requireNonNull(password, "password");
      initBits &= ~INIT_BIT_PASSWORD;
      return this;
    }

    /**
     * Initializes the value for the {@link User#getUsername() username} attribute.
     * @param username The value for username 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder username(String username) {
      this.username = Objects.requireNonNull(username, "username");
      initBits &= ~INIT_BIT_USERNAME;
      return this;
    }

    /**
     * Initializes the value for the {@link User#isAccountNonExpired() isAccountNonExpired} attribute.
     * @param isAccountNonExpired The value for isAccountNonExpired 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder isAccountNonExpired(boolean isAccountNonExpired) {
      this.isAccountNonExpired = isAccountNonExpired;
      initBits &= ~INIT_BIT_IS_ACCOUNT_NON_EXPIRED;
      return this;
    }

    /**
     * Initializes the value for the {@link User#isAccountNonLocked() isAccountNonLocked} attribute.
     * @param isAccountNonLocked The value for isAccountNonLocked 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder isAccountNonLocked(boolean isAccountNonLocked) {
      this.isAccountNonLocked = isAccountNonLocked;
      initBits &= ~INIT_BIT_IS_ACCOUNT_NON_LOCKED;
      return this;
    }

    /**
     * Initializes the value for the {@link User#isCredentialsNonExpired() isCredentialsNonExpired} attribute.
     * @param isCredentialsNonExpired The value for isCredentialsNonExpired 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder isCredentialsNonExpired(boolean isCredentialsNonExpired) {
      this.isCredentialsNonExpired = isCredentialsNonExpired;
      initBits &= ~INIT_BIT_IS_CREDENTIALS_NON_EXPIRED;
      return this;
    }

    /**
     * Initializes the value for the {@link User#isEnabled() isEnabled} attribute.
     * @param isEnabled The value for isEnabled 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder isEnabled(boolean isEnabled) {
      this.isEnabled = isEnabled;
      initBits &= ~INIT_BIT_IS_ENABLED;
      return this;
    }

    /**
     * Initializes the value for the {@link User#id() id} attribute.
     * @param id The value for id 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder id(UUID id) {
      this.id = Objects.requireNonNull(id, "id");
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Initializes the value for the {@link User#firstName() firstName} attribute.
     * @param firstName The value for firstName 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder firstName(String firstName) {
      this.firstName = Objects.requireNonNull(firstName, "firstName");
      initBits &= ~INIT_BIT_FIRST_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link User#lastName() lastName} attribute.
     * @param lastName The value for lastName 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder lastName(String lastName) {
      this.lastName = Objects.requireNonNull(lastName, "lastName");
      initBits &= ~INIT_BIT_LAST_NAME;
      return this;
    }

    /**
     * Adds one element to {@link User#authorityNames() authorityNames} list.
     * @param element A authorityNames element
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addAuthorityNames(String element) {
      this.authorityNames.add(Objects.requireNonNull(element, "authorityNames element"));
      return this;
    }

    /**
     * Adds elements to {@link User#authorityNames() authorityNames} list.
     * @param elements An array of authorityNames elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addAuthorityNames(String... elements) {
      for (String element : elements) {
        this.authorityNames.add(Objects.requireNonNull(element, "authorityNames element"));
      }
      return this;
    }


    /**
     * Sets or replaces all elements for {@link User#authorityNames() authorityNames} list.
     * @param elements An iterable of authorityNames elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder authorityNames(Iterable<String> elements) {
      this.authorityNames.clear();
      return addAllAuthorityNames(elements);
    }

    /**
     * Adds elements to {@link User#authorityNames() authorityNames} list.
     * @param elements An iterable of authorityNames elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addAllAuthorityNames(Iterable<String> elements) {
      for (String element : elements) {
        this.authorityNames.add(Objects.requireNonNull(element, "authorityNames element"));
      }
      return this;
    }

    /**
     * Builds a new {@link ImmutableUser ImmutableUser}.
     * @return An immutable instance of User
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableUser build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableUser(
          authorities,
          password,
          username,
          isAccountNonExpired,
          isAccountNonLocked,
          isCredentialsNonExpired,
          isEnabled,
          id,
          firstName,
          lastName,
          createUnmodifiableList(true, authorityNames));
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_AUTHORITIES) != 0) attributes.add("authorities");
      if ((initBits & INIT_BIT_PASSWORD) != 0) attributes.add("password");
      if ((initBits & INIT_BIT_USERNAME) != 0) attributes.add("username");
      if ((initBits & INIT_BIT_IS_ACCOUNT_NON_EXPIRED) != 0) attributes.add("isAccountNonExpired");
      if ((initBits & INIT_BIT_IS_ACCOUNT_NON_LOCKED) != 0) attributes.add("isAccountNonLocked");
      if ((initBits & INIT_BIT_IS_CREDENTIALS_NON_EXPIRED) != 0) attributes.add("isCredentialsNonExpired");
      if ((initBits & INIT_BIT_IS_ENABLED) != 0) attributes.add("isEnabled");
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      if ((initBits & INIT_BIT_FIRST_NAME) != 0) attributes.add("firstName");
      if ((initBits & INIT_BIT_LAST_NAME) != 0) attributes.add("lastName");
      return "Cannot build User, some of required attributes are not set " + attributes;
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
