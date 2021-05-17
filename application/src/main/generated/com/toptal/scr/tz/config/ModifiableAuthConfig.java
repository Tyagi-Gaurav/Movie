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
 * A modifiable implementation of the {@link AuthConfig AuthConfig} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * <p><em>ModifiableAuthConfig is not thread-safe</em>
 */
@Generated(from = "AuthConfig", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated({"Modifiables.generator", "AuthConfig"})
@NotThreadSafe
public final class ModifiableAuthConfig implements AuthConfig {
  private static final long INIT_BIT_TOKEN_DURATION = 0x1L;
  private static final long INIT_BIT_SECRET = 0x2L;
  private long initBits = 0x3L;

  private Duration tokenDuration;
  private String secret;

  private ModifiableAuthConfig() {}

  /**
   * Construct a modifiable instance of {@code AuthConfig}.
   * @return A new modifiable instance
   */
  public static ModifiableAuthConfig create() {
    return new ModifiableAuthConfig();
  }

  /**
   * @return value of {@code tokenDuration} attribute
   */
  @Override
  public final Duration tokenDuration() {
    if (!tokenDurationIsSet()) {
      checkRequiredAttributes();
    }
    return tokenDuration;
  }

  /**
   * @return value of {@code secret} attribute
   */
  @Override
  public final String secret() {
    if (!secretIsSet()) {
      checkRequiredAttributes();
    }
    return secret;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableAuthConfig clear() {
    initBits = 0x3L;
    tokenDuration = null;
    secret = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link AuthConfig} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableAuthConfig from(AuthConfig instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof ModifiableAuthConfig) {
      from((ModifiableAuthConfig) instance);
      return this;
    }
    setTokenDuration(instance.tokenDuration());
    setSecret(instance.secret());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link AuthConfig} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableAuthConfig from(ModifiableAuthConfig instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.tokenDurationIsSet()) {
      setTokenDuration(instance.tokenDuration());
    }
    if (instance.secretIsSet()) {
      setSecret(instance.secret());
    }
    return this;
  }

  /**
   * Assigns a value to the {@code tokenDuration} attribute.
   * @param tokenDuration The value for tokenDuration
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableAuthConfig setTokenDuration(Duration tokenDuration) {
    this.tokenDuration = Objects.requireNonNull(tokenDuration, "tokenDuration");
    initBits &= ~INIT_BIT_TOKEN_DURATION;
    return this;
  }

  /**
   * Assigns a value to the {@code secret} attribute.
   * @param secret The value for secret
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableAuthConfig setSecret(String secret) {
    this.secret = Objects.requireNonNull(secret, "secret");
    initBits &= ~INIT_BIT_SECRET;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@code tokenDuration} is set.
   * @return {@code true} if set
   */
  public final boolean tokenDurationIsSet() {
    return (initBits & INIT_BIT_TOKEN_DURATION) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@code secret} is set.
   * @return {@code true} if set
   */
  public final boolean secretIsSet() {
    return (initBits & INIT_BIT_SECRET) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableAuthConfig unsetTokenDuration() {
    initBits |= INIT_BIT_TOKEN_DURATION;
    tokenDuration = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableAuthConfig unsetSecret() {
    initBits |= INIT_BIT_SECRET;
    secret = null;
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
    if (!tokenDurationIsSet()) attributes.add("tokenDuration");
    if (!secretIsSet()) attributes.add("secret");
    return "AuthConfig is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * This instance is equal to all instances of {@code ModifiableAuthConfig} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof ModifiableAuthConfig)) return false;
    ModifiableAuthConfig other = (ModifiableAuthConfig) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(ModifiableAuthConfig another) {
    return tokenDuration.equals(another.tokenDuration)
        && secret.equals(another.secret);
  }

  /**
   * Computes a hash code from attributes: {@code tokenDuration}, {@code secret}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + tokenDuration.hashCode();
    h += (h << 5) + secret.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code AuthConfig}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ModifiableAuthConfig")
        .add("tokenDuration", tokenDurationIsSet() ? tokenDuration() : "?")
        .add("secret", secretIsSet() ? secret() : "?")
        .toString();
  }
}
