package com.gt.scr.movie.test.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link TimeZoneAppConfig TimeZoneAppConfig} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * <p><em>ModifiableTimeZoneAppConfig is not thread-safe</em>
 */
@Generated(from = "TimeZoneAppConfig", generator = "Modifiables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated({"Modifiables.generator", "TimeZoneAppConfig"})
public final class ModifiableTimeZoneAppConfig implements TimeZoneAppConfig {
  private static final long INIT_BIT_HOST = 0x1L;
  private static final long INIT_BIT_PORT = 0x2L;
  private long initBits = 0x3L;

  private String host;
  private int port;

  private ModifiableTimeZoneAppConfig() {}

  /**
   * Construct a modifiable instance of {@code TimeZoneAppConfig}.
   * @return A new modifiable instance
   */
  public static ModifiableTimeZoneAppConfig create() {
    return new ModifiableTimeZoneAppConfig();
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
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableTimeZoneAppConfig clear() {
    initBits = 0x3L;
    host = null;
    port = 0;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link TimeZoneAppConfig} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableTimeZoneAppConfig from(TimeZoneAppConfig instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof ModifiableTimeZoneAppConfig) {
      from((ModifiableTimeZoneAppConfig) instance);
      return this;
    }
    setHost(instance.host());
    setPort(instance.port());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link TimeZoneAppConfig} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableTimeZoneAppConfig from(ModifiableTimeZoneAppConfig instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.hostIsSet()) {
      setHost(instance.host());
    }
    if (instance.portIsSet()) {
      setPort(instance.port());
    }
    return this;
  }

  /**
   * Assigns a value to the {@code host} attribute.
   * @param host The value for host
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableTimeZoneAppConfig setHost(String host) {
    this.host = Objects.requireNonNull(host, "host");
    initBits &= ~INIT_BIT_HOST;
    return this;
  }

  /**
   * Assigns a value to the {@code port} attribute.
   * @param port The value for port
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableTimeZoneAppConfig setPort(int port) {
    this.port = port;
    initBits &= ~INIT_BIT_PORT;
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
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  public final ModifiableTimeZoneAppConfig unsetHost() {
    initBits |= INIT_BIT_HOST;
    host = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  public final ModifiableTimeZoneAppConfig unsetPort() {
    initBits |= INIT_BIT_PORT;
    port = 0;
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
    return "TimeZoneAppConfig is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * This instance is equal to all instances of {@code ModifiableTimeZoneAppConfig} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    if (!(another instanceof ModifiableTimeZoneAppConfig)) return false;
    ModifiableTimeZoneAppConfig other = (ModifiableTimeZoneAppConfig) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(ModifiableTimeZoneAppConfig another) {
    return host.equals(another.host)
        && port == another.port;
  }

  /**
   * Computes a hash code from attributes: {@code host}, {@code port}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + host.hashCode();
    h += (h << 5) + port;
    return h;
  }

  /**
   * Generates a string representation of this {@code TimeZoneAppConfig}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return "ModifiableTimeZoneAppConfig{"
        + "host="  + (hostIsSet() ? host() : "?")
        + ", port="  + (portIsSet() ? port() : "?")
        + "}";
  }
}
