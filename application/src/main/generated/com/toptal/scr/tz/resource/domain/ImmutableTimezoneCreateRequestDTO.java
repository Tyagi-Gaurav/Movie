package com.toptal.scr.tz.resource.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link TimezoneCreateRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTimezoneCreateRequestDTO.builder()}.
 */
@Generated(from = "TimezoneCreateRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableTimezoneCreateRequestDTO
    implements TimezoneCreateRequestDTO {
  private final String name;
  private final String city;
  private final int gmtOffset;

  private ImmutableTimezoneCreateRequestDTO(String name, String city, int gmtOffset) {
    this.name = name;
    this.city = city;
    this.gmtOffset = gmtOffset;
  }

  /**
   * @return The value of the {@code name} attribute
   */
  @JsonProperty("name")
  @Override
  public String name() {
    return name;
  }

  /**
   * @return The value of the {@code city} attribute
   */
  @JsonProperty("city")
  @Override
  public String city() {
    return city;
  }

  /**
   * @return The value of the {@code gmtOffset} attribute
   */
  @JsonProperty("gmtOffset")
  @Override
  public int gmtOffset() {
    return gmtOffset;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TimezoneCreateRequestDTO#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTimezoneCreateRequestDTO withName(String value) {
    String newValue = Objects.requireNonNull(value, "name");
    if (this.name.equals(newValue)) return this;
    return validate(new ImmutableTimezoneCreateRequestDTO(newValue, this.city, this.gmtOffset));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TimezoneCreateRequestDTO#city() city} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for city
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTimezoneCreateRequestDTO withCity(String value) {
    String newValue = Objects.requireNonNull(value, "city");
    if (this.city.equals(newValue)) return this;
    return validate(new ImmutableTimezoneCreateRequestDTO(this.name, newValue, this.gmtOffset));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TimezoneCreateRequestDTO#gmtOffset() gmtOffset} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for gmtOffset
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTimezoneCreateRequestDTO withGmtOffset(int value) {
    if (this.gmtOffset == value) return this;
    return validate(new ImmutableTimezoneCreateRequestDTO(this.name, this.city, value));
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTimezoneCreateRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTimezoneCreateRequestDTO
        && equalTo((ImmutableTimezoneCreateRequestDTO) another);
  }

  private boolean equalTo(ImmutableTimezoneCreateRequestDTO another) {
    return name.equals(another.name)
        && city.equals(another.city)
        && gmtOffset == another.gmtOffset;
  }

  /**
   * Computes a hash code from attributes: {@code name}, {@code city}, {@code gmtOffset}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + name.hashCode();
    h += (h << 5) + city.hashCode();
    h += (h << 5) + gmtOffset;
    return h;
  }

  /**
   * Prints the immutable value {@code TimezoneCreateRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("TimezoneCreateRequestDTO")
        .omitNullValues()
        .add("name", name)
        .add("city", city)
        .add("gmtOffset", gmtOffset)
        .toString();
  }

  private static ImmutableTimezoneCreateRequestDTO validate(ImmutableTimezoneCreateRequestDTO instance) {
    instance.check();
    return instance;
  }

  /**
   * Creates an immutable copy of a {@link TimezoneCreateRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TimezoneCreateRequestDTO instance
   */
  public static ImmutableTimezoneCreateRequestDTO copyOf(TimezoneCreateRequestDTO instance) {
    if (instance instanceof ImmutableTimezoneCreateRequestDTO) {
      return (ImmutableTimezoneCreateRequestDTO) instance;
    }
    return ImmutableTimezoneCreateRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTimezoneCreateRequestDTO ImmutableTimezoneCreateRequestDTO}.
   * <pre>
   * ImmutableTimezoneCreateRequestDTO.builder()
   *    .name(String) // required {@link TimezoneCreateRequestDTO#name() name}
   *    .city(String) // required {@link TimezoneCreateRequestDTO#city() city}
   *    .gmtOffset(int) // required {@link TimezoneCreateRequestDTO#gmtOffset() gmtOffset}
   *    .build();
   * </pre>
   * @return A new ImmutableTimezoneCreateRequestDTO builder
   */
  public static ImmutableTimezoneCreateRequestDTO.Builder builder() {
    return new ImmutableTimezoneCreateRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTimezoneCreateRequestDTO ImmutableTimezoneCreateRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "TimezoneCreateRequestDTO", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_NAME = 0x1L;
    private static final long INIT_BIT_CITY = 0x2L;
    private static final long INIT_BIT_GMT_OFFSET = 0x4L;
    private long initBits = 0x7L;

    private @Nullable String name;
    private @Nullable String city;
    private int gmtOffset;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TimezoneCreateRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(TimezoneCreateRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      name(instance.name());
      city(instance.city());
      gmtOffset(instance.gmtOffset());
      return this;
    }

    /**
     * Initializes the value for the {@link TimezoneCreateRequestDTO#name() name} attribute.
     * @param name The value for name 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("name")
    public final Builder name(String name) {
      this.name = Objects.requireNonNull(name, "name");
      initBits &= ~INIT_BIT_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link TimezoneCreateRequestDTO#city() city} attribute.
     * @param city The value for city 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("city")
    public final Builder city(String city) {
      this.city = Objects.requireNonNull(city, "city");
      initBits &= ~INIT_BIT_CITY;
      return this;
    }

    /**
     * Initializes the value for the {@link TimezoneCreateRequestDTO#gmtOffset() gmtOffset} attribute.
     * @param gmtOffset The value for gmtOffset 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("gmtOffset")
    public final Builder gmtOffset(int gmtOffset) {
      this.gmtOffset = gmtOffset;
      initBits &= ~INIT_BIT_GMT_OFFSET;
      return this;
    }

    /**
     * Builds a new {@link ImmutableTimezoneCreateRequestDTO ImmutableTimezoneCreateRequestDTO}.
     * @return An immutable instance of TimezoneCreateRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTimezoneCreateRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return ImmutableTimezoneCreateRequestDTO.validate(new ImmutableTimezoneCreateRequestDTO(name, city, gmtOffset));
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_NAME) != 0) attributes.add("name");
      if ((initBits & INIT_BIT_CITY) != 0) attributes.add("city");
      if ((initBits & INIT_BIT_GMT_OFFSET) != 0) attributes.add("gmtOffset");
      return "Cannot build TimezoneCreateRequestDTO, some of required attributes are not set " + attributes;
    }
  }
}
