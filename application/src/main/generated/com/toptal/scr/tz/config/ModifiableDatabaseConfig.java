package com.toptal.scr.tz.config;

import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link DatabaseConfig DatabaseConfig} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * <p><em>ModifiableDatabaseConfig is not thread-safe</em>
 */
@Generated(from = "DatabaseConfig", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated({"Modifiables.generator", "DatabaseConfig"})
@NotThreadSafe
public final class ModifiableDatabaseConfig implements DatabaseConfig {
  private static final long INIT_BIT_HOST = 0x1L;
  private static final long INIT_BIT_PORT = 0x2L;
  private static final long INIT_BIT_DUPLICATE_INTERVAL = 0x4L;
  private static final long INIT_BIT_CONNECTION_TIMEOUT = 0x8L;
  private static final long INIT_BIT_COMMAND_TIMEOUT = 0x10L;
  private long initBits = 0x1fL;

  private String host;
  private int port;
  private Duration duplicateInterval;
  private Duration connectionTimeout;
  private Duration commandTimeout;

  private ModifiableDatabaseConfig() {}

  /**
   * Construct a modifiable instance of {@code DatabaseConfig}.
   * @return A new modifiable instance
   */
  public static ModifiableDatabaseConfig create() {
    return new ModifiableDatabaseConfig();
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
   * @return value of {@code duplicateInterval} attribute
   */
  @Override
  public final Duration duplicateInterval() {
    if (!duplicateIntervalIsSet()) {
      checkRequiredAttributes();
    }
    return duplicateInterval;
  }

  /**
   * @return value of {@code connectionTimeout} attribute
   */
  @Override
  public final Duration connectionTimeout() {
    if (!connectionTimeoutIsSet()) {
      checkRequiredAttributes();
    }
    return connectionTimeout;
  }

  /**
   * @return value of {@code commandTimeout} attribute
   */
  @Override
  public final Duration commandTimeout() {
    if (!commandTimeoutIsSet()) {
      checkRequiredAttributes();
    }
    return commandTimeout;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableDatabaseConfig clear() {
    initBits = 0x1fL;
    host = null;
    port = 0;
    duplicateInterval = null;
    connectionTimeout = null;
    commandTimeout = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link DatabaseConfig} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableDatabaseConfig from(DatabaseConfig instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof ModifiableDatabaseConfig) {
      from((ModifiableDatabaseConfig) instance);
      return this;
    }
    setHost(instance.host());
    setPort(instance.port());
    setDuplicateInterval(instance.duplicateInterval());
    setConnectionTimeout(instance.connectionTimeout());
    setCommandTimeout(instance.commandTimeout());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link DatabaseConfig} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableDatabaseConfig from(ModifiableDatabaseConfig instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.hostIsSet()) {
      setHost(instance.host());
    }
    if (instance.portIsSet()) {
      setPort(instance.port());
    }
    if (instance.duplicateIntervalIsSet()) {
      setDuplicateInterval(instance.duplicateInterval());
    }
    if (instance.connectionTimeoutIsSet()) {
      setConnectionTimeout(instance.connectionTimeout());
    }
    if (instance.commandTimeoutIsSet()) {
      setCommandTimeout(instance.commandTimeout());
    }
    return this;
  }

  /**
   * Assigns a value to the {@code host} attribute.
   * @param host The value for host
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableDatabaseConfig setHost(String host) {
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
  public ModifiableDatabaseConfig setPort(int port) {
    this.port = port;
    initBits &= ~INIT_BIT_PORT;
    return this;
  }

  /**
   * Assigns a value to the {@code duplicateInterval} attribute.
   * @param duplicateInterval The value for duplicateInterval
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableDatabaseConfig setDuplicateInterval(Duration duplicateInterval) {
    this.duplicateInterval = Objects.requireNonNull(duplicateInterval, "duplicateInterval");
    initBits &= ~INIT_BIT_DUPLICATE_INTERVAL;
    return this;
  }

  /**
   * Assigns a value to the {@code connectionTimeout} attribute.
   * @param connectionTimeout The value for connectionTimeout
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableDatabaseConfig setConnectionTimeout(Duration connectionTimeout) {
    this.connectionTimeout = Objects.requireNonNull(connectionTimeout, "connectionTimeout");
    initBits &= ~INIT_BIT_CONNECTION_TIMEOUT;
    return this;
  }

  /**
   * Assigns a value to the {@code commandTimeout} attribute.
   * @param commandTimeout The value for commandTimeout
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableDatabaseConfig setCommandTimeout(Duration commandTimeout) {
    this.commandTimeout = Objects.requireNonNull(commandTimeout, "commandTimeout");
    initBits &= ~INIT_BIT_COMMAND_TIMEOUT;
    return this;
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
   * Returns {@code true} if the required attribute {@code duplicateInterval} is set.
   * @return {@code true} if set
   */
  public final boolean duplicateIntervalIsSet() {
    return (initBits & INIT_BIT_DUPLICATE_INTERVAL) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@code connectionTimeout} is set.
   * @return {@code true} if set
   */
  public final boolean connectionTimeoutIsSet() {
    return (initBits & INIT_BIT_CONNECTION_TIMEOUT) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@code commandTimeout} is set.
   * @return {@code true} if set
   */
  public final boolean commandTimeoutIsSet() {
    return (initBits & INIT_BIT_COMMAND_TIMEOUT) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableDatabaseConfig unsetHost() {
    initBits |= INIT_BIT_HOST;
    host = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableDatabaseConfig unsetPort() {
    initBits |= INIT_BIT_PORT;
    port = 0;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableDatabaseConfig unsetDuplicateInterval() {
    initBits |= INIT_BIT_DUPLICATE_INTERVAL;
    duplicateInterval = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableDatabaseConfig unsetConnectionTimeout() {
    initBits |= INIT_BIT_CONNECTION_TIMEOUT;
    connectionTimeout = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableDatabaseConfig unsetCommandTimeout() {
    initBits |= INIT_BIT_COMMAND_TIMEOUT;
    commandTimeout = null;
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
    if (!hostIsSet()) attributes.add("host");
    if (!portIsSet()) attributes.add("port");
    if (!duplicateIntervalIsSet()) attributes.add("duplicateInterval");
    if (!connectionTimeoutIsSet()) attributes.add("connectionTimeout");
    if (!commandTimeoutIsSet()) attributes.add("commandTimeout");
    return "DatabaseConfig is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * This instance is equal to all instances of {@code ModifiableDatabaseConfig} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof ModifiableDatabaseConfig)) return false;
    ModifiableDatabaseConfig other = (ModifiableDatabaseConfig) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(ModifiableDatabaseConfig another) {
    return host.equals(another.host)
        && port == another.port
        && duplicateInterval.equals(another.duplicateInterval)
        && connectionTimeout.equals(another.connectionTimeout)
        && commandTimeout.equals(another.commandTimeout);
  }

  /**
   * Computes a hash code from attributes: {@code host}, {@code port}, {@code duplicateInterval}, {@code connectionTimeout}, {@code commandTimeout}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + host.hashCode();
    h += (h << 5) + port;
    h += (h << 5) + duplicateInterval.hashCode();
    h += (h << 5) + connectionTimeout.hashCode();
    h += (h << 5) + commandTimeout.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code DatabaseConfig}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ModifiableDatabaseConfig")
        .add("host", hostIsSet() ? host() : "?")
        .add("port", portIsSet() ? port() : "?")
        .add("duplicateInterval", duplicateIntervalIsSet() ? duplicateInterval() : "?")
        .add("connectionTimeout", connectionTimeoutIsSet() ? connectionTimeout() : "?")
        .add("commandTimeout", commandTimeoutIsSet() ? commandTimeout() : "?")
        .toString();
  }
}
