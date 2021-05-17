package com.toptal.scr.tz.config;

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
 * A modifiable implementation of the {@link ValidationConfig ValidationConfig} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * <p><em>ModifiableValidationConfig is not thread-safe</em>
 */
@Generated(from = "ValidationConfig", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated({"Modifiables.generator", "ValidationConfig"})
@NotThreadSafe
public final class ModifiableValidationConfig implements ValidationConfig {
  private static final long INIT_BIT_MAX_USER_NAME_LENGTH = 0x1L;
  private static final long INIT_BIT_MIN_USER_NAME_LENGTH = 0x2L;
  private static final long INIT_BIT_MAX_PASSWORD_LENGTH = 0x4L;
  private static final long INIT_BIT_MIN_PASSWORD_LENGTH = 0x8L;
  private long initBits = 0xfL;

  private int maxUserNameLength;
  private int minUserNameLength;
  private int maxPasswordLength;
  private int minPasswordLength;

  private ModifiableValidationConfig() {}

  /**
   * Construct a modifiable instance of {@code ValidationConfig}.
   * @return A new modifiable instance
   */
  public static ModifiableValidationConfig create() {
    return new ModifiableValidationConfig();
  }

  /**
   * @return value of {@code maxUserNameLength} attribute
   */
  @Override
  public final int maxUserNameLength() {
    if (!maxUserNameLengthIsSet()) {
      checkRequiredAttributes();
    }
    return maxUserNameLength;
  }

  /**
   * @return value of {@code minUserNameLength} attribute
   */
  @Override
  public final int minUserNameLength() {
    if (!minUserNameLengthIsSet()) {
      checkRequiredAttributes();
    }
    return minUserNameLength;
  }

  /**
   * @return value of {@code maxPasswordLength} attribute
   */
  @Override
  public final int maxPasswordLength() {
    if (!maxPasswordLengthIsSet()) {
      checkRequiredAttributes();
    }
    return maxPasswordLength;
  }

  /**
   * @return value of {@code minPasswordLength} attribute
   */
  @Override
  public final int minPasswordLength() {
    if (!minPasswordLengthIsSet()) {
      checkRequiredAttributes();
    }
    return minPasswordLength;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableValidationConfig clear() {
    initBits = 0xfL;
    maxUserNameLength = 0;
    minUserNameLength = 0;
    maxPasswordLength = 0;
    minPasswordLength = 0;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ValidationConfig} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableValidationConfig from(ValidationConfig instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof ModifiableValidationConfig) {
      from((ModifiableValidationConfig) instance);
      return this;
    }
    setMaxUserNameLength(instance.maxUserNameLength());
    setMinUserNameLength(instance.minUserNameLength());
    setMaxPasswordLength(instance.maxPasswordLength());
    setMinPasswordLength(instance.minPasswordLength());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ValidationConfig} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableValidationConfig from(ModifiableValidationConfig instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance.maxUserNameLengthIsSet()) {
      setMaxUserNameLength(instance.maxUserNameLength());
    }
    if (instance.minUserNameLengthIsSet()) {
      setMinUserNameLength(instance.minUserNameLength());
    }
    if (instance.maxPasswordLengthIsSet()) {
      setMaxPasswordLength(instance.maxPasswordLength());
    }
    if (instance.minPasswordLengthIsSet()) {
      setMinPasswordLength(instance.minPasswordLength());
    }
    return this;
  }

  /**
   * Assigns a value to the {@code maxUserNameLength} attribute.
   * @param maxUserNameLength The value for maxUserNameLength
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableValidationConfig setMaxUserNameLength(int maxUserNameLength) {
    this.maxUserNameLength = maxUserNameLength;
    initBits &= ~INIT_BIT_MAX_USER_NAME_LENGTH;
    return this;
  }

  /**
   * Assigns a value to the {@code minUserNameLength} attribute.
   * @param minUserNameLength The value for minUserNameLength
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableValidationConfig setMinUserNameLength(int minUserNameLength) {
    this.minUserNameLength = minUserNameLength;
    initBits &= ~INIT_BIT_MIN_USER_NAME_LENGTH;
    return this;
  }

  /**
   * Assigns a value to the {@code maxPasswordLength} attribute.
   * @param maxPasswordLength The value for maxPasswordLength
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableValidationConfig setMaxPasswordLength(int maxPasswordLength) {
    this.maxPasswordLength = maxPasswordLength;
    initBits &= ~INIT_BIT_MAX_PASSWORD_LENGTH;
    return this;
  }

  /**
   * Assigns a value to the {@code minPasswordLength} attribute.
   * @param minPasswordLength The value for minPasswordLength
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableValidationConfig setMinPasswordLength(int minPasswordLength) {
    this.minPasswordLength = minPasswordLength;
    initBits &= ~INIT_BIT_MIN_PASSWORD_LENGTH;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@code maxUserNameLength} is set.
   * @return {@code true} if set
   */
  public final boolean maxUserNameLengthIsSet() {
    return (initBits & INIT_BIT_MAX_USER_NAME_LENGTH) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@code minUserNameLength} is set.
   * @return {@code true} if set
   */
  public final boolean minUserNameLengthIsSet() {
    return (initBits & INIT_BIT_MIN_USER_NAME_LENGTH) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@code maxPasswordLength} is set.
   * @return {@code true} if set
   */
  public final boolean maxPasswordLengthIsSet() {
    return (initBits & INIT_BIT_MAX_PASSWORD_LENGTH) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@code minPasswordLength} is set.
   * @return {@code true} if set
   */
  public final boolean minPasswordLengthIsSet() {
    return (initBits & INIT_BIT_MIN_PASSWORD_LENGTH) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableValidationConfig unsetMaxUserNameLength() {
    initBits |= INIT_BIT_MAX_USER_NAME_LENGTH;
    maxUserNameLength = 0;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableValidationConfig unsetMinUserNameLength() {
    initBits |= INIT_BIT_MIN_USER_NAME_LENGTH;
    minUserNameLength = 0;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableValidationConfig unsetMaxPasswordLength() {
    initBits |= INIT_BIT_MAX_PASSWORD_LENGTH;
    maxPasswordLength = 0;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableValidationConfig unsetMinPasswordLength() {
    initBits |= INIT_BIT_MIN_PASSWORD_LENGTH;
    minPasswordLength = 0;
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
    if (!maxUserNameLengthIsSet()) attributes.add("maxUserNameLength");
    if (!minUserNameLengthIsSet()) attributes.add("minUserNameLength");
    if (!maxPasswordLengthIsSet()) attributes.add("maxPasswordLength");
    if (!minPasswordLengthIsSet()) attributes.add("minPasswordLength");
    return "ValidationConfig is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * This instance is equal to all instances of {@code ModifiableValidationConfig} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof ModifiableValidationConfig)) return false;
    ModifiableValidationConfig other = (ModifiableValidationConfig) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(ModifiableValidationConfig another) {
    return maxUserNameLength == another.maxUserNameLength
        && minUserNameLength == another.minUserNameLength
        && maxPasswordLength == another.maxPasswordLength
        && minPasswordLength == another.minPasswordLength;
  }

  /**
   * Computes a hash code from attributes: {@code maxUserNameLength}, {@code minUserNameLength}, {@code maxPasswordLength}, {@code minPasswordLength}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + maxUserNameLength;
    h += (h << 5) + minUserNameLength;
    h += (h << 5) + maxPasswordLength;
    h += (h << 5) + minPasswordLength;
    return h;
  }

  /**
   * Generates a string representation of this {@code ValidationConfig}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ModifiableValidationConfig")
        .add("maxUserNameLength", maxUserNameLengthIsSet() ? maxUserNameLength() : "?")
        .add("minUserNameLength", minUserNameLengthIsSet() ? minUserNameLength() : "?")
        .add("maxPasswordLength", maxPasswordLengthIsSet() ? maxPasswordLength() : "?")
        .add("minPasswordLength", minPasswordLengthIsSet() ? minPasswordLength() : "?")
        .toString();
  }
}
