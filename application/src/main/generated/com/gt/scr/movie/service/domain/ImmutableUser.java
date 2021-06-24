package com.gt.scr.movie.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
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
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ImmutableUser implements User {
  private final Collection<? extends GrantedAuthority> authorities;
  private final String password;
  private final String username;
  private final UUID id;
  private final String firstName;
  private final String lastName;
  private final boolean isAccountNonExpired;
  private final boolean isAccountNonLocked;
  private final boolean isCredentialsNonExpired;
  private final boolean isEnabled;
  private final HashMap<UUID, UserTimezone> userTimeZones;

  private ImmutableUser(ImmutableUser.Builder builder) {
    this.authorities = builder.authorities;
    this.password = builder.password;
    this.username = builder.username;
    this.id = builder.id;
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    if (builder.isAccountNonExpiredIsSet()) {
      initShim.isAccountNonExpired(builder.isAccountNonExpired);
    }
    if (builder.isAccountNonLockedIsSet()) {
      initShim.isAccountNonLocked(builder.isAccountNonLocked);
    }
    if (builder.isCredentialsNonExpiredIsSet()) {
      initShim.isCredentialsNonExpired(builder.isCredentialsNonExpired);
    }
    if (builder.isEnabledIsSet()) {
      initShim.isEnabled(builder.isEnabled);
    }
    if (builder.userTimeZones != null) {
      initShim.userTimeZones(builder.userTimeZones);
    }
    this.isAccountNonExpired = initShim.isAccountNonExpired();
    this.isAccountNonLocked = initShim.isAccountNonLocked();
    this.isCredentialsNonExpired = initShim.isCredentialsNonExpired();
    this.isEnabled = initShim.isEnabled();
    this.userTimeZones = initShim.userTimeZones();
    this.initShim = null;
  }

  private ImmutableUser(
      Collection<? extends GrantedAuthority> authorities,
      String password,
      String username,
      UUID id,
      String firstName,
      String lastName,
      boolean isAccountNonExpired,
      boolean isAccountNonLocked,
      boolean isCredentialsNonExpired,
      boolean isEnabled,
      HashMap<UUID, UserTimezone> userTimeZones) {
    this.authorities = authorities;
    this.password = password;
    this.username = username;
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.isAccountNonExpired = isAccountNonExpired;
    this.isAccountNonLocked = isAccountNonLocked;
    this.isCredentialsNonExpired = isCredentialsNonExpired;
    this.isEnabled = isEnabled;
    this.userTimeZones = userTimeZones;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "User", generator = "Immutables")
  private final class InitShim {
    private byte isAccountNonExpiredBuildStage = STAGE_UNINITIALIZED;
    private boolean isAccountNonExpired;

    boolean isAccountNonExpired() {
      if (isAccountNonExpiredBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (isAccountNonExpiredBuildStage == STAGE_UNINITIALIZED) {
        isAccountNonExpiredBuildStage = STAGE_INITIALIZING;
        this.isAccountNonExpired = isAccountNonExpiredInitialize();
        isAccountNonExpiredBuildStage = STAGE_INITIALIZED;
      }
      return this.isAccountNonExpired;
    }

    void isAccountNonExpired(boolean isAccountNonExpired) {
      this.isAccountNonExpired = isAccountNonExpired;
      isAccountNonExpiredBuildStage = STAGE_INITIALIZED;
    }

    private byte isAccountNonLockedBuildStage = STAGE_UNINITIALIZED;
    private boolean isAccountNonLocked;

    boolean isAccountNonLocked() {
      if (isAccountNonLockedBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (isAccountNonLockedBuildStage == STAGE_UNINITIALIZED) {
        isAccountNonLockedBuildStage = STAGE_INITIALIZING;
        this.isAccountNonLocked = isAccountNonLockedInitialize();
        isAccountNonLockedBuildStage = STAGE_INITIALIZED;
      }
      return this.isAccountNonLocked;
    }

    void isAccountNonLocked(boolean isAccountNonLocked) {
      this.isAccountNonLocked = isAccountNonLocked;
      isAccountNonLockedBuildStage = STAGE_INITIALIZED;
    }

    private byte isCredentialsNonExpiredBuildStage = STAGE_UNINITIALIZED;
    private boolean isCredentialsNonExpired;

    boolean isCredentialsNonExpired() {
      if (isCredentialsNonExpiredBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (isCredentialsNonExpiredBuildStage == STAGE_UNINITIALIZED) {
        isCredentialsNonExpiredBuildStage = STAGE_INITIALIZING;
        this.isCredentialsNonExpired = isCredentialsNonExpiredInitialize();
        isCredentialsNonExpiredBuildStage = STAGE_INITIALIZED;
      }
      return this.isCredentialsNonExpired;
    }

    void isCredentialsNonExpired(boolean isCredentialsNonExpired) {
      this.isCredentialsNonExpired = isCredentialsNonExpired;
      isCredentialsNonExpiredBuildStage = STAGE_INITIALIZED;
    }

    private byte isEnabledBuildStage = STAGE_UNINITIALIZED;
    private boolean isEnabled;

    boolean isEnabled() {
      if (isEnabledBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (isEnabledBuildStage == STAGE_UNINITIALIZED) {
        isEnabledBuildStage = STAGE_INITIALIZING;
        this.isEnabled = isEnabledInitialize();
        isEnabledBuildStage = STAGE_INITIALIZED;
      }
      return this.isEnabled;
    }

    void isEnabled(boolean isEnabled) {
      this.isEnabled = isEnabled;
      isEnabledBuildStage = STAGE_INITIALIZED;
    }

    private byte userTimeZonesBuildStage = STAGE_UNINITIALIZED;
    private HashMap<UUID, UserTimezone> userTimeZones;

    HashMap<UUID, UserTimezone> userTimeZones() {
      if (userTimeZonesBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (userTimeZonesBuildStage == STAGE_UNINITIALIZED) {
        userTimeZonesBuildStage = STAGE_INITIALIZING;
        this.userTimeZones = Objects.requireNonNull(userTimeZonesInitialize(), "userTimeZones");
        userTimeZonesBuildStage = STAGE_INITIALIZED;
      }
      return this.userTimeZones;
    }

    void userTimeZones(HashMap<UUID, UserTimezone> userTimeZones) {
      this.userTimeZones = userTimeZones;
      userTimeZonesBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (isAccountNonExpiredBuildStage == STAGE_INITIALIZING) attributes.add("isAccountNonExpired");
      if (isAccountNonLockedBuildStage == STAGE_INITIALIZING) attributes.add("isAccountNonLocked");
      if (isCredentialsNonExpiredBuildStage == STAGE_INITIALIZING) attributes.add("isCredentialsNonExpired");
      if (isEnabledBuildStage == STAGE_INITIALIZING) attributes.add("isEnabled");
      if (userTimeZonesBuildStage == STAGE_INITIALIZING) attributes.add("userTimeZones");
      return "Cannot build User, attribute initializers form cycle " + attributes;
    }
  }

  private boolean isAccountNonExpiredInitialize() {
    return User.super.isAccountNonExpired();
  }

  private boolean isAccountNonLockedInitialize() {
    return User.super.isAccountNonLocked();
  }

  private boolean isCredentialsNonExpiredInitialize() {
    return User.super.isCredentialsNonExpired();
  }

  private boolean isEnabledInitialize() {
    return User.super.isEnabled();
  }

  private HashMap<UUID, UserTimezone> userTimeZonesInitialize() {
    return User.super.userTimeZones();
  }

  /**
   * @return The value of the {@code authorities} attribute
   */
  @JsonProperty("authorities")
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  /**
   * @return The value of the {@code password} attribute
   */
  @JsonProperty("password")
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * @return The value of the {@code username} attribute
   */
  @JsonProperty("username")
  @Override
  public String getUsername() {
    return username;
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
   * @return The value of the {@code isAccountNonExpired} attribute
   */
  @JsonProperty("isAccountNonExpired")
  @Override
  public boolean isAccountNonExpired() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.isAccountNonExpired()
        : this.isAccountNonExpired;
  }

  /**
   * @return The value of the {@code isAccountNonLocked} attribute
   */
  @JsonProperty("isAccountNonLocked")
  @Override
  public boolean isAccountNonLocked() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.isAccountNonLocked()
        : this.isAccountNonLocked;
  }

  /**
   * @return The value of the {@code isCredentialsNonExpired} attribute
   */
  @JsonProperty("isCredentialsNonExpired")
  @Override
  public boolean isCredentialsNonExpired() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.isCredentialsNonExpired()
        : this.isCredentialsNonExpired;
  }

  /**
   * @return The value of the {@code isEnabled} attribute
   */
  @JsonProperty("isEnabled")
  @Override
  public boolean isEnabled() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.isEnabled()
        : this.isEnabled;
  }

  /**
   * @return The value of the {@code userTimeZones} attribute
   */
  @JsonProperty("userTimeZones")
  @Override
  public HashMap<UUID, UserTimezone> userTimeZones() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.userTimeZones()
        : this.userTimeZones;
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
        this.id,
        this.firstName,
        this.lastName,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.userTimeZones);
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
        this.id,
        this.firstName,
        this.lastName,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.userTimeZones);
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
        this.id,
        this.firstName,
        this.lastName,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.userTimeZones);
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
        newValue,
        this.firstName,
        this.lastName,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.userTimeZones);
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
        this.id,
        newValue,
        this.lastName,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.userTimeZones);
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
        this.id,
        this.firstName,
        newValue,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.userTimeZones);
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
        this.id,
        this.firstName,
        this.lastName,
        value,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.userTimeZones);
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
        this.id,
        this.firstName,
        this.lastName,
        this.isAccountNonExpired,
        value,
        this.isCredentialsNonExpired,
        this.isEnabled,
        this.userTimeZones);
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
        this.id,
        this.firstName,
        this.lastName,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        value,
        this.isEnabled,
        this.userTimeZones);
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
        this.id,
        this.firstName,
        this.lastName,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        value,
        this.userTimeZones);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link User#userTimeZones() userTimeZones} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for userTimeZones
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUser withUserTimeZones(HashMap<UUID, UserTimezone> value) {
    if (this.userTimeZones == value) return this;
    HashMap<UUID, UserTimezone> newValue = Objects.requireNonNull(value, "userTimeZones");
    return new ImmutableUser(
        this.authorities,
        this.password,
        this.username,
        this.id,
        this.firstName,
        this.lastName,
        this.isAccountNonExpired,
        this.isAccountNonLocked,
        this.isCredentialsNonExpired,
        this.isEnabled,
        newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableUser} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableUser
        && equalTo((ImmutableUser) another);
  }

  private boolean equalTo(ImmutableUser another) {
    return authorities.equals(another.authorities)
        && password.equals(another.password)
        && username.equals(another.username)
        && id.equals(another.id)
        && firstName.equals(another.firstName)
        && lastName.equals(another.lastName)
        && isAccountNonExpired == another.isAccountNonExpired
        && isAccountNonLocked == another.isAccountNonLocked
        && isCredentialsNonExpired == another.isCredentialsNonExpired
        && isEnabled == another.isEnabled
        && userTimeZones.equals(another.userTimeZones);
  }

  /**
   * Computes a hash code from attributes: {@code authorities}, {@code password}, {@code username}, {@code id}, {@code firstName}, {@code lastName}, {@code isAccountNonExpired}, {@code isAccountNonLocked}, {@code isCredentialsNonExpired}, {@code isEnabled}, {@code userTimeZones}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + authorities.hashCode();
    h += (h << 5) + password.hashCode();
    h += (h << 5) + username.hashCode();
    h += (h << 5) + id.hashCode();
    h += (h << 5) + firstName.hashCode();
    h += (h << 5) + lastName.hashCode();
    h += (h << 5) + Booleans.hashCode(isAccountNonExpired);
    h += (h << 5) + Booleans.hashCode(isAccountNonLocked);
    h += (h << 5) + Booleans.hashCode(isCredentialsNonExpired);
    h += (h << 5) + Booleans.hashCode(isEnabled);
    h += (h << 5) + userTimeZones.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code User} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("User")
        .omitNullValues()
        .add("authorities", authorities)
        .add("password", password)
        .add("username", username)
        .add("id", id)
        .add("firstName", firstName)
        .add("lastName", lastName)
        .add("isAccountNonExpired", isAccountNonExpired)
        .add("isAccountNonLocked", isAccountNonLocked)
        .add("isCredentialsNonExpired", isCredentialsNonExpired)
        .add("isEnabled", isEnabled)
        .add("userTimeZones", userTimeZones)
        .toString();
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
   * The serialized form captures the structural content of the value object,
   * providing the ability to reconstruct values with the capability to migrate
   * data. Uses optional, nullable, and provides flexible handling of
   * collection attributes.
   */
  @Generated(from = "User", generator = "Immutables")
  private static class SerialForm implements Serializable {
    private static final long serialVersionUID = 0L;
    private final String[] names;
    private final Object[] values;
    SerialForm(ImmutableUser instance) {
      List<String> names = new ArrayList<>(11);
      List<Object> values = new ArrayList<>(11);
      names.add("authorities");
      values.add(instance.getAuthorities());
      names.add("password");
      values.add(instance.getPassword());
      names.add("username");
      values.add(instance.getUsername());
      names.add("id");
      values.add(instance.id());
      names.add("firstName");
      values.add(instance.firstName());
      names.add("lastName");
      values.add(instance.lastName());
      names.add("isAccountNonExpired");
      values.add(instance.isAccountNonExpired());
      names.add("isAccountNonLocked");
      values.add(instance.isAccountNonLocked());
      names.add("isCredentialsNonExpired");
      values.add(instance.isCredentialsNonExpired());
      names.add("isEnabled");
      values.add(instance.isEnabled());
      names.add("userTimeZones");
      values.add(instance.userTimeZones());
      this.names = names.toArray(new String[names.size()]);
      this.values = values.toArray();
    }

    Object readResolve() {
      ImmutableUser.Builder builder = ImmutableUser.builder();

      for (int i = 0; i < names.length; i++) {
        String name = names[i];
        if ("authorities".equals(name)) {
          builder.authorities((Collection<? extends GrantedAuthority>) toSingle("authorities", values[i]));
          continue;
        }
        if ("password".equals(name)) {
          builder.password((String) toSingle("password", values[i]));
          continue;
        }
        if ("username".equals(name)) {
          builder.username((String) toSingle("username", values[i]));
          continue;
        }
        if ("id".equals(name)) {
          builder.id((UUID) toSingle("id", values[i]));
          continue;
        }
        if ("firstName".equals(name)) {
          builder.firstName((String) toSingle("firstName", values[i]));
          continue;
        }
        if ("lastName".equals(name)) {
          builder.lastName((String) toSingle("lastName", values[i]));
          continue;
        }
        if ("isAccountNonExpired".equals(name)) {
          builder.isAccountNonExpired((Boolean) toSingle("isAccountNonExpired", values[i]));
          continue;
        }
        if ("isAccountNonLocked".equals(name)) {
          builder.isAccountNonLocked((Boolean) toSingle("isAccountNonLocked", values[i]));
          continue;
        }
        if ("isCredentialsNonExpired".equals(name)) {
          builder.isCredentialsNonExpired((Boolean) toSingle("isCredentialsNonExpired", values[i]));
          continue;
        }
        if ("isEnabled".equals(name)) {
          builder.isEnabled((Boolean) toSingle("isEnabled", values[i]));
          continue;
        }
        if ("userTimeZones".equals(name)) {
          builder.userTimeZones((HashMap<UUID, UserTimezone>) toSingle("userTimeZones", values[i]));
          continue;
        }
      }
      return builder.build();
    }

    private static Object toSingle(String attribute, Object value) {
      if (value instanceof Object[]) {
        Object[] elements = (Object[]) value;
        if (elements.length == 1) {
          return elements[0];
        }
        throw new IllegalStateException("Cannot extract scalar value for attribute '"
            + attribute + "' from array of length " + elements.length);
      }
      return value;
    }
  }

  private Object writeReplace() {
    return new SerialForm(this);
  }

  /**
   * Creates a builder for {@link ImmutableUser ImmutableUser}.
   * <pre>
   * ImmutableUser.builder()
   *    .authorities(Collection&amp;lt;? extends org.springframework.security.core.GrantedAuthority&amp;gt;) // required {@link User#getAuthorities() authorities}
   *    .password(String) // required {@link User#getPassword() password}
   *    .username(String) // required {@link User#getUsername() username}
   *    .id(UUID) // required {@link User#id() id}
   *    .firstName(String) // required {@link User#firstName() firstName}
   *    .lastName(String) // required {@link User#lastName() lastName}
   *    .isAccountNonExpired(boolean) // optional {@link User#isAccountNonExpired() isAccountNonExpired}
   *    .isAccountNonLocked(boolean) // optional {@link User#isAccountNonLocked() isAccountNonLocked}
   *    .isCredentialsNonExpired(boolean) // optional {@link User#isCredentialsNonExpired() isCredentialsNonExpired}
   *    .isEnabled(boolean) // optional {@link User#isEnabled() isEnabled}
   *    .userTimeZones(HashMap&amp;lt;UUID, com.gt.scr.movie.service.domain.UserTimezone&amp;gt;) // optional {@link User#userTimeZones() userTimeZones}
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
  @NotThreadSafe
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {
    private static final long INIT_BIT_AUTHORITIES = 0x1L;
    private static final long INIT_BIT_PASSWORD = 0x2L;
    private static final long INIT_BIT_USERNAME = 0x4L;
    private static final long INIT_BIT_ID = 0x8L;
    private static final long INIT_BIT_FIRST_NAME = 0x10L;
    private static final long INIT_BIT_LAST_NAME = 0x20L;
    private static final long OPT_BIT_IS_ACCOUNT_NON_EXPIRED = 0x1L;
    private static final long OPT_BIT_IS_ACCOUNT_NON_LOCKED = 0x2L;
    private static final long OPT_BIT_IS_CREDENTIALS_NON_EXPIRED = 0x4L;
    private static final long OPT_BIT_IS_ENABLED = 0x8L;
    private long initBits = 0x3fL;
    private long optBits;

    private @Nullable Collection<? extends GrantedAuthority> authorities;
    private @Nullable String password;
    private @Nullable String username;
    private @Nullable UUID id;
    private @Nullable String firstName;
    private @Nullable String lastName;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private @Nullable HashMap<UUID, UserTimezone> userTimeZones;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.springframework.security.core.userdetails.UserDetails} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(UserDetails instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code com.gt.scr.movie.service.domain.User} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(User instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      @Var long bits = 0;
      if (object instanceof UserDetails) {
        UserDetails instance = (UserDetails) object;
        password(instance.getPassword());
        if ((bits & 0x1L) == 0) {
          isAccountNonExpired(instance.isAccountNonExpired());
          bits |= 0x1L;
        }
        if ((bits & 0x2L) == 0) {
          isCredentialsNonExpired(instance.isCredentialsNonExpired());
          bits |= 0x2L;
        }
        if ((bits & 0x4L) == 0) {
          isEnabled(instance.isEnabled());
          bits |= 0x4L;
        }
        if ((bits & 0x8L) == 0) {
          isAccountNonLocked(instance.isAccountNonLocked());
          bits |= 0x8L;
        }
        authorities(instance.getAuthorities());
        username(instance.getUsername());
      }
      if (object instanceof User) {
        User instance = (User) object;
        firstName(instance.firstName());
        lastName(instance.lastName());
        if ((bits & 0x1L) == 0) {
          isAccountNonExpired(instance.isAccountNonExpired());
          bits |= 0x1L;
        }
        if ((bits & 0x2L) == 0) {
          isCredentialsNonExpired(instance.isCredentialsNonExpired());
          bits |= 0x2L;
        }
        if ((bits & 0x4L) == 0) {
          isEnabled(instance.isEnabled());
          bits |= 0x4L;
        }
        userTimeZones(instance.userTimeZones());
        if ((bits & 0x8L) == 0) {
          isAccountNonLocked(instance.isAccountNonLocked());
          bits |= 0x8L;
        }
        id(instance.id());
      }
    }

    /**
     * Initializes the value for the {@link User#getAuthorities() authorities} attribute.
     * @param authorities The value for authorities 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("authorities")
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
    @CanIgnoreReturnValue 
    @JsonProperty("password")
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
    @CanIgnoreReturnValue 
    @JsonProperty("username")
    public final Builder username(String username) {
      this.username = Objects.requireNonNull(username, "username");
      initBits &= ~INIT_BIT_USERNAME;
      return this;
    }

    /**
     * Initializes the value for the {@link User#id() id} attribute.
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
     * Initializes the value for the {@link User#firstName() firstName} attribute.
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
     * Initializes the value for the {@link User#lastName() lastName} attribute.
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
     * Initializes the value for the {@link User#isAccountNonExpired() isAccountNonExpired} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link User#isAccountNonExpired() isAccountNonExpired}.</em>
     * @param isAccountNonExpired The value for isAccountNonExpired 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isAccountNonExpired")
    public final Builder isAccountNonExpired(boolean isAccountNonExpired) {
      this.isAccountNonExpired = isAccountNonExpired;
      optBits |= OPT_BIT_IS_ACCOUNT_NON_EXPIRED;
      return this;
    }

    /**
     * Initializes the value for the {@link User#isAccountNonLocked() isAccountNonLocked} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link User#isAccountNonLocked() isAccountNonLocked}.</em>
     * @param isAccountNonLocked The value for isAccountNonLocked 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isAccountNonLocked")
    public final Builder isAccountNonLocked(boolean isAccountNonLocked) {
      this.isAccountNonLocked = isAccountNonLocked;
      optBits |= OPT_BIT_IS_ACCOUNT_NON_LOCKED;
      return this;
    }

    /**
     * Initializes the value for the {@link User#isCredentialsNonExpired() isCredentialsNonExpired} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link User#isCredentialsNonExpired() isCredentialsNonExpired}.</em>
     * @param isCredentialsNonExpired The value for isCredentialsNonExpired 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isCredentialsNonExpired")
    public final Builder isCredentialsNonExpired(boolean isCredentialsNonExpired) {
      this.isCredentialsNonExpired = isCredentialsNonExpired;
      optBits |= OPT_BIT_IS_CREDENTIALS_NON_EXPIRED;
      return this;
    }

    /**
     * Initializes the value for the {@link User#isEnabled() isEnabled} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link User#isEnabled() isEnabled}.</em>
     * @param isEnabled The value for isEnabled 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isEnabled")
    public final Builder isEnabled(boolean isEnabled) {
      this.isEnabled = isEnabled;
      optBits |= OPT_BIT_IS_ENABLED;
      return this;
    }

    /**
     * Initializes the value for the {@link User#userTimeZones() userTimeZones} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link User#userTimeZones() userTimeZones}.</em>
     * @param userTimeZones The value for userTimeZones 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("userTimeZones")
    public final Builder userTimeZones(HashMap<UUID, UserTimezone> userTimeZones) {
      this.userTimeZones = Objects.requireNonNull(userTimeZones, "userTimeZones");
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
      return new ImmutableUser(this);
    }

    private boolean isAccountNonExpiredIsSet() {
      return (optBits & OPT_BIT_IS_ACCOUNT_NON_EXPIRED) != 0;
    }

    private boolean isAccountNonLockedIsSet() {
      return (optBits & OPT_BIT_IS_ACCOUNT_NON_LOCKED) != 0;
    }

    private boolean isCredentialsNonExpiredIsSet() {
      return (optBits & OPT_BIT_IS_CREDENTIALS_NON_EXPIRED) != 0;
    }

    private boolean isEnabledIsSet() {
      return (optBits & OPT_BIT_IS_ENABLED) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_AUTHORITIES) != 0) attributes.add("authorities");
      if ((initBits & INIT_BIT_PASSWORD) != 0) attributes.add("password");
      if ((initBits & INIT_BIT_USERNAME) != 0) attributes.add("username");
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      if ((initBits & INIT_BIT_FIRST_NAME) != 0) attributes.add("firstName");
      if ((initBits & INIT_BIT_LAST_NAME) != 0) attributes.add("lastName");
      return "Cannot build User, some of required attributes are not set " + attributes;
    }
  }
}
