package com.toptal.scr.tz.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link TestTimezoneUpdateRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTestTimezoneUpdateRequestDTO.builder()}.
 */
@Generated(from = "TestTimezoneUpdateRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableTestTimezoneUpdateRequestDTO
    implements TestTimezoneUpdateRequestDTO {
  private final UUID id;
  private final String name;
  private final String city;
  private final int gmtOffset;

  private ImmutableTestTimezoneUpdateRequestDTO(UUID id, String name, String city, int gmtOffset) {
    this.id = id;
    this.name = name;
    this.city = city;
    this.gmtOffset = gmtOffset;
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
   * Copy the current immutable object by setting a value for the {@link TestTimezoneUpdateRequestDTO#id() id} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestTimezoneUpdateRequestDTO withId(UUID value) {
    if (this.id == value) return this;
    UUID newValue = Objects.requireNonNull(value, "id");
    return new ImmutableTestTimezoneUpdateRequestDTO(newValue, this.name, this.city, this.gmtOffset);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestTimezoneUpdateRequestDTO#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestTimezoneUpdateRequestDTO withName(String value) {
    String newValue = Objects.requireNonNull(value, "name");
    if (this.name.equals(newValue)) return this;
    return new ImmutableTestTimezoneUpdateRequestDTO(this.id, newValue, this.city, this.gmtOffset);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestTimezoneUpdateRequestDTO#city() city} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for city
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestTimezoneUpdateRequestDTO withCity(String value) {
    String newValue = Objects.requireNonNull(value, "city");
    if (this.city.equals(newValue)) return this;
    return new ImmutableTestTimezoneUpdateRequestDTO(this.id, this.name, newValue, this.gmtOffset);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestTimezoneUpdateRequestDTO#gmtOffset() gmtOffset} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for gmtOffset
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestTimezoneUpdateRequestDTO withGmtOffset(int value) {
    if (this.gmtOffset == value) return this;
    return new ImmutableTestTimezoneUpdateRequestDTO(this.id, this.name, this.city, value);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTestTimezoneUpdateRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTestTimezoneUpdateRequestDTO
        && equalTo((ImmutableTestTimezoneUpdateRequestDTO) another);
  }

  private boolean equalTo(ImmutableTestTimezoneUpdateRequestDTO another) {
    return id.equals(another.id)
        && name.equals(another.name)
        && city.equals(another.city)
        && gmtOffset == another.gmtOffset;
  }

  /**
   * Computes a hash code from attributes: {@code id}, {@code name}, {@code city}, {@code gmtOffset}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + id.hashCode();
    h += (h << 5) + name.hashCode();
    h += (h << 5) + city.hashCode();
    h += (h << 5) + gmtOffset;
    return h;
  }

  /**
   * Prints the immutable value {@code TestTimezoneUpdateRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "TestTimezoneUpdateRequestDTO{"
        + "id=" + id
        + ", name=" + name
        + ", city=" + city
        + ", gmtOffset=" + gmtOffset
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link TestTimezoneUpdateRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TestTimezoneUpdateRequestDTO instance
   */
  public static ImmutableTestTimezoneUpdateRequestDTO copyOf(TestTimezoneUpdateRequestDTO instance) {
    if (instance instanceof ImmutableTestTimezoneUpdateRequestDTO) {
      return (ImmutableTestTimezoneUpdateRequestDTO) instance;
    }
    return ImmutableTestTimezoneUpdateRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTestTimezoneUpdateRequestDTO ImmutableTestTimezoneUpdateRequestDTO}.
   * <pre>
   * ImmutableTestTimezoneUpdateRequestDTO.builder()
   *    .id(UUID) // required {@link TestTimezoneUpdateRequestDTO#id() id}
   *    .name(String) // required {@link TestTimezoneUpdateRequestDTO#name() name}
   *    .city(String) // required {@link TestTimezoneUpdateRequestDTO#city() city}
   *    .gmtOffset(int) // required {@link TestTimezoneUpdateRequestDTO#gmtOffset() gmtOffset}
   *    .build();
   * </pre>
   * @return A new ImmutableTestTimezoneUpdateRequestDTO builder
   */
  public static ImmutableTestTimezoneUpdateRequestDTO.Builder builder() {
    return new ImmutableTestTimezoneUpdateRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTestTimezoneUpdateRequestDTO ImmutableTestTimezoneUpdateRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "TestTimezoneUpdateRequestDTO", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private static final long INIT_BIT_NAME = 0x2L;
    private static final long INIT_BIT_CITY = 0x4L;
    private static final long INIT_BIT_GMT_OFFSET = 0x8L;
    private long initBits = 0xfL;

    private UUID id;
    private String name;
    private String city;
    private int gmtOffset;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TestTimezoneUpdateRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(TestTimezoneUpdateRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      id(instance.id());
      name(instance.name());
      city(instance.city());
      gmtOffset(instance.gmtOffset());
      return this;
    }

    /**
     * Initializes the value for the {@link TestTimezoneUpdateRequestDTO#id() id} attribute.
     * @param id The value for id 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("id")
    public final Builder id(UUID id) {
      this.id = Objects.requireNonNull(id, "id");
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Initializes the value for the {@link TestTimezoneUpdateRequestDTO#name() name} attribute.
     * @param name The value for name 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("name")
    public final Builder name(String name) {
      this.name = Objects.requireNonNull(name, "name");
      initBits &= ~INIT_BIT_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link TestTimezoneUpdateRequestDTO#city() city} attribute.
     * @param city The value for city 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("city")
    public final Builder city(String city) {
      this.city = Objects.requireNonNull(city, "city");
      initBits &= ~INIT_BIT_CITY;
      return this;
    }

    /**
     * Initializes the value for the {@link TestTimezoneUpdateRequestDTO#gmtOffset() gmtOffset} attribute.
     * @param gmtOffset The value for gmtOffset 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("gmtOffset")
    public final Builder gmtOffset(int gmtOffset) {
      this.gmtOffset = gmtOffset;
      initBits &= ~INIT_BIT_GMT_OFFSET;
      return this;
    }

    /**
     * Builds a new {@link ImmutableTestTimezoneUpdateRequestDTO ImmutableTestTimezoneUpdateRequestDTO}.
     * @return An immutable instance of TestTimezoneUpdateRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTestTimezoneUpdateRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableTestTimezoneUpdateRequestDTO(id, name, city, gmtOffset);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      if ((initBits & INIT_BIT_NAME) != 0) attributes.add("name");
      if ((initBits & INIT_BIT_CITY) != 0) attributes.add("city");
      if ((initBits & INIT_BIT_GMT_OFFSET) != 0) attributes.add("gmtOffset");
      return "Cannot build TestTimezoneUpdateRequestDTO, some of required attributes are not set " + attributes;
    }
  }
}
