package com.gt.scr.movie.config;

import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link MySQLConfig MySQLConfig} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * <p><em>ModifiableMySQLConfig is not thread-safe</em>
 */
@Generated(from = "MySQLConfig", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated({"Modifiables.generator", "MySQLConfig"})
@NotThreadSafe
public final class ModifiableMySQLConfig implements MySQLConfig {
  private static final long INIT_BIT_DRIVER = 0x1L;
  private static final long INIT_BIT_HOST = 0x2L;
  private static final long INIT_BIT_PORT = 0x4L;
  private static final long INIT_BIT_DATABASE = 0x8L;
  private static final long INIT_BIT_USER = 0x10L;
  private static final long INIT_BIT_PASSWORD = 0x20L;
  private static final long INIT_BIT_MIN_POOL_SIZE = 0x40L;
  private static final long INIT_BIT_MAX_POOL_SIZE = 0x80L;
  private long initBits = 0xffL;

  private String driver;
  private String host;
  private int port;
  private String database;
  private String user;
  private String password;
  private int minPoolSize;
  private int maxPoolSize;

  private ModifiableMySQLConfig() {}

  /**
   * Construct a modifiable instance of {@code MySQLConfig}.
   * @return A new modifiable instance
   */
  public static ModifiableMySQLConfig create() {
    return new ModifiableMySQLConfig();
  }

  /**
   * @return value of {@code driver} attribute
   */
  @Override
  public final String driver() {
    if (!driverIsSet()) {
      checkRequiredAttributes();
    }
    return driver;
  }

  /**
   * @return value of {@code host} attribute
   */
  @Override
  public final String host() {
    if (!hostIsSet()) {
      checkRequiredAttributes();
    }
    return host;
  }

  /**
   * @return value of {@code port} attribute
   */
  @Override
  public final int port() {
    if (!portIsSet()) {
      checkRequiredAttributes();
    }
    return port;
  }

  /**
   * @return value of {@code database} attribute
   */
  @Override
  public final String database() {
    if (!databaseIsSet()) {
      checkRequiredAttributes();
    }
    return database;
  }

  /**
   * @return value of {@code user} attribute
   */
  @Override
  public final String user() {
    if (!userIsSet()) {
      checkRequiredAttributes();
    }
    return user;
  }

  /**
   * @return value of {@code password} attribute
   */
  @Override
  public final String password() {
    if (!passwordIsSet()) {
      checkRequiredAttributes();
    }
    return password;
  }

  /**
   * @return value of {@code minPoolSize} attribute
   */
  @Override
  public final int minPoolSize() {
    if (!minPoolSizeIsSet()) {
      checkRequiredAttributes();
    }
    return minPoolSize;
  }

  /**
   * @return value of {@code maxPoolSize} attribute
   */
  @Override
  public final int maxPoolSize() {
    if (!maxPoolSizeIsSet()) {
      checkRequiredAttributes();
    }
    return maxPoolSize;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableMySQLConfig clear() {
    initBits = 0xffL;
    driver = null;
    host = null;
    port = 0;
    database = null;
    user = null;
    password = null;
    minPoolSize = 0;
    maxPoolSize = 0;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link MySQLConfig} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableMySQLConfig from(MySQLConfig instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof ModifiableMySQLConfig) {
      from((ModifiableMySQLConfig) instance);
      return this;
    }
    setDriver(instance.driver());
    setHost(instance.host());
    setPort(instance.port());
    setDatabase(instance.database());
    setUser(instance.user());
    setPassword(instance.password());
    setMinPoolSize(instance.minPoolSize());
    setMaxPoolSize(instance.maxPoolSize());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link MySQLConfig} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableMySQLConfig from(ModifiableMySQLConfig instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.driverIsSet()) {
      setDriver(instance.driver());
    }
    if (instance.hostIsSet()) {
      setHost(instance.host());
    }
    if (instance.portIsSet()) {
      setPort(instance.port());
    }
    if (instance.databaseIsSet()) {
      setDatabase(instance.database());
    }
    if (instance.userIsSet()) {
      setUser(instance.user());
    }
    if (instance.passwordIsSet()) {
      setPassword(instance.password());
    }
    if (instance.minPoolSizeIsSet()) {
      setMinPoolSize(instance.minPoolSize());
    }
    if (instance.maxPoolSizeIsSet()) {
      setMaxPoolSize(instance.maxPoolSize());
    }
    return this;
  }

  /**
   * Assigns a value to the {@code driver} attribute.
   * @param driver The value for driver
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableMySQLConfig setDriver(String driver) {
    this.driver = Objects.requireNonNull(driver, "driver");
    initBits &= ~INIT_BIT_DRIVER;
    return this;
  }

  /**
   * Assigns a value to the {@code host} attribute.
   * @param host The value for host
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableMySQLConfig setHost(String host) {
    this.host = Objects.requireNonNull(host, "host");
    initBits &= ~INIT_BIT_HOST;
    return this;
  }

  /**
   * Assigns a value to the {@code port} attribute.
   * @param port The value for port
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableMySQLConfig setPort(int port) {
    this.port = port;
    initBits &= ~INIT_BIT_PORT;
    return this;
  }

  /**
   * Assigns a value to the {@code database} attribute.
   * @param database The value for database
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableMySQLConfig setDatabase(String database) {
    this.database = Objects.requireNonNull(database, "database");
    initBits &= ~INIT_BIT_DATABASE;
    return this;
  }

  /**
   * Assigns a value to the {@code user} attribute.
   * @param user The value for user
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableMySQLConfig setUser(String user) {
    this.user = Objects.requireNonNull(user, "user");
    initBits &= ~INIT_BIT_USER;
    return this;
  }

  /**
   * Assigns a value to the {@code password} attribute.
   * @param password The value for password
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableMySQLConfig setPassword(String password) {
    this.password = Objects.requireNonNull(password, "password");
    initBits &= ~INIT_BIT_PASSWORD;
    return this;
  }

  /**
   * Assigns a value to the {@code minPoolSize} attribute.
   * @param minPoolSize The value for minPoolSize
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableMySQLConfig setMinPoolSize(int minPoolSize) {
    this.minPoolSize = minPoolSize;
    initBits &= ~INIT_BIT_MIN_POOL_SIZE;
    return this;
  }

  /**
   * Assigns a value to the {@code maxPoolSize} attribute.
   * @param maxPoolSize The value for maxPoolSize
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableMySQLConfig setMaxPoolSize(int maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
    initBits &= ~INIT_BIT_MAX_POOL_SIZE;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@code driver} is set.
   * @return {@code true} if set
   */
  public final boolean driverIsSet() {
    return (initBits & INIT_BIT_DRIVER) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@code host} is set.
   * @return {@code true} if set
   */
  public final boolean hostIsSet() {
    return (initBits & INIT_BIT_HOST) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@code port} is set.
   * @return {@code true} if set
   */
  public final boolean portIsSet() {
    return (initBits & INIT_BIT_PORT) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@code database} is set.
   * @return {@code true} if set
   */
  public final boolean databaseIsSet() {
    return (initBits & INIT_BIT_DATABASE) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@code user} is set.
   * @return {@code true} if set
   */
  public final boolean userIsSet() {
    return (initBits & INIT_BIT_USER) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@code password} is set.
   * @return {@code true} if set
   */
  public final boolean passwordIsSet() {
    return (initBits & INIT_BIT_PASSWORD) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@code minPoolSize} is set.
   * @return {@code true} if set
   */
  public final boolean minPoolSizeIsSet() {
    return (initBits & INIT_BIT_MIN_POOL_SIZE) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@code maxPoolSize} is set.
   * @return {@code true} if set
   */
  public final boolean maxPoolSizeIsSet() {
    return (initBits & INIT_BIT_MAX_POOL_SIZE) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableMySQLConfig unsetDriver() {
    initBits |= INIT_BIT_DRIVER;
    driver = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableMySQLConfig unsetHost() {
    initBits |= INIT_BIT_HOST;
    host = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableMySQLConfig unsetPort() {
    initBits |= INIT_BIT_PORT;
    port = 0;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableMySQLConfig unsetDatabase() {
    initBits |= INIT_BIT_DATABASE;
    database = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableMySQLConfig unsetUser() {
    initBits |= INIT_BIT_USER;
    user = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableMySQLConfig unsetPassword() {
    initBits |= INIT_BIT_PASSWORD;
    password = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableMySQLConfig unsetMinPoolSize() {
    initBits |= INIT_BIT_MIN_POOL_SIZE;
    minPoolSize = 0;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableMySQLConfig unsetMaxPoolSize() {
    initBits |= INIT_BIT_MAX_POOL_SIZE;
    maxPoolSize = 0;
    return this;
  }

  /**
   * Returns {@code true} if all required attributes are set, indicating that the object is initialized.
   * @return {@code true} if set
   */
  public final boolean isInitialized() {
    return initBits == 0;
  }

  private void checkRequiredAttributes() {
    if (!isInitialized()) {
      throw new IllegalStateException(formatRequiredAttributesMessage());
    }
  }

  private String formatRequiredAttributesMessage() {
    List<String> attributes = new ArrayList<>();
    if (!driverIsSet()) attributes.add("driver");
    if (!hostIsSet()) attributes.add("host");
    if (!portIsSet()) attributes.add("port");
    if (!databaseIsSet()) attributes.add("database");
    if (!userIsSet()) attributes.add("user");
    if (!passwordIsSet()) attributes.add("password");
    if (!minPoolSizeIsSet()) attributes.add("minPoolSize");
    if (!maxPoolSizeIsSet()) attributes.add("maxPoolSize");
    return "MySQLConfig is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * This instance is equal to all instances of {@code ModifiableMySQLConfig} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof ModifiableMySQLConfig)) return false;
    ModifiableMySQLConfig other = (ModifiableMySQLConfig) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(ModifiableMySQLConfig another) {
    return driver.equals(another.driver)
        && host.equals(another.host)
        && port == another.port
        && database.equals(another.database)
        && user.equals(another.user)
        && password.equals(another.password)
        && minPoolSize == another.minPoolSize
        && maxPoolSize == another.maxPoolSize;
  }

  /**
   * Computes a hash code from attributes: {@code driver}, {@code host}, {@code port}, {@code database}, {@code user}, {@code password}, {@code minPoolSize}, {@code maxPoolSize}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + driver.hashCode();
    h += (h << 5) + host.hashCode();
    h += (h << 5) + port;
    h += (h << 5) + database.hashCode();
    h += (h << 5) + user.hashCode();
    h += (h << 5) + password.hashCode();
    h += (h << 5) + minPoolSize;
    h += (h << 5) + maxPoolSize;
    return h;
  }

  /**
   * Generates a string representation of this {@code MySQLConfig}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ModifiableMySQLConfig")
        .add("driver", driverIsSet() ? driver() : "?")
        .add("host", hostIsSet() ? host() : "?")
        .add("port", portIsSet() ? port() : "?")
        .add("database", databaseIsSet() ? database() : "?")
        .add("user", userIsSet() ? user() : "?")
        .add("password", passwordIsSet() ? password() : "?")
        .add("minPoolSize", minPoolSizeIsSet() ? minPoolSize() : "?")
        .add("maxPoolSize", maxPoolSizeIsSet() ? maxPoolSize() : "?")
        .toString();
  }
}
