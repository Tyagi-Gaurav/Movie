package com.toptal.scr.tz.resource.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link TimezoneUpdateRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTimezoneUpdateRequestDTO.builder()}.
 */
@Generated(from = "TimezoneUpdateRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableTimezoneUpdateRequestDTO
    implements TimezoneUpdateRequestDTO {
  private final UUID id;
  private final String name;
  private final String city;
  private final int gmtOffset;

  private ImmutableTimezoneUpdateRequestDTO(ImmutableTimezoneUpdateRequestDTO.Builder builder) {
    this.id = builder.id;
    if (builder.name != null) {
      initShim.name(builder.name);
    }
    if (builder.city != null) {
      initShim.city(builder.city);
    }
    if (builder.gmtOffsetIsSet()) {
      initShim.gmtOffset(builder.gmtOffset);
    }
    this.name = initShim.name();
    this.city = initShim.city();
    this.gmtOffset = initShim.gmtOffset();
    this.initShim = null;
  }

  private ImmutableTimezoneUpdateRequestDTO(UUID id, String name, String city, int gmtOffset) {
    this.id = id;
    this.name = name;
    this.city = city;
    this.gmtOffset = gmtOffset;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "TimezoneUpdateRequestDTO", generator = "Immutables")
  private final class InitShim {
    private byte nameBuildStage = STAGE_UNINITIALIZED;
    private String name;

    String name() {
      if (nameBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (nameBuildStage == STAGE_UNINITIALIZED) {
        nameBuildStage = STAGE_INITIALIZING;
        this.name = Objects.requireNonNull(nameInitialize(), "name");
        nameBuildStage = STAGE_INITIALIZED;
      }
      return this.name;
    }

    void name(String name) {
      this.name = name;
      nameBuildStage = STAGE_INITIALIZED;
    }

    private byte cityBuildStage = STAGE_UNINITIALIZED;
    private String city;

    String city() {
      if (cityBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (cityBuildStage == STAGE_UNINITIALIZED) {
        cityBuildStage = STAGE_INITIALIZING;
        this.city = Objects.requireNonNull(cityInitialize(), "city");
        cityBuildStage = STAGE_INITIALIZED;
      }
      return this.city;
    }

    void city(String city) {
      this.city = city;
      cityBuildStage = STAGE_INITIALIZED;
    }

    private byte gmtOffsetBuildStage = STAGE_UNINITIALIZED;
    private int gmtOffset;

    int gmtOffset() {
      if (gmtOffsetBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (gmtOffsetBuildStage == STAGE_UNINITIALIZED) {
        gmtOffsetBuildStage = STAGE_INITIALIZING;
        this.gmtOffset = gmtOffsetInitialize();
        gmtOffsetBuildStage = STAGE_INITIALIZED;
      }
      return this.gmtOffset;
    }

    void gmtOffset(int gmtOffset) {
      this.gmtOffset = gmtOffset;
      gmtOffsetBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (nameBuildStage == STAGE_INITIALIZING) attributes.add("name");
      if (cityBuildStage == STAGE_INITIALIZING) attributes.add("city");
      if (gmtOffsetBuildStage == STAGE_INITIALIZING) attributes.add("gmtOffset");
      return "Cannot build TimezoneUpdateRequestDTO, attribute initializers form cycle " + attributes;
    }
  }

  private String nameInitialize() {
    return TimezoneUpdateRequestDTO.super.name();
  }

  private String cityInitialize() {
    return TimezoneUpdateRequestDTO.super.city();
  }

  private int gmtOffsetInitialize() {
    return TimezoneUpdateRequestDTO.super.gmtOffset();
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
    InitShim shim = this.initShim;
    return shim != null
        ? shim.name()
        : this.name;
  }

  /**
   * @return The value of the {@code city} attribute
   */
  @JsonProperty("city")
  @Override
  public String city() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.city()
        : this.city;
  }

  /**
   * @return The value of the {@code gmtOffset} attribute
   */
  @JsonProperty("gmtOffset")
  @Override
  public int gmtOffset() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.gmtOffset()
        : this.gmtOffset;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TimezoneUpdateRequestDTO#id() id} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTimezoneUpdateRequestDTO withId(UUID value) {
    if (this.id == value) return this;
    UUID newValue = Objects.requireNonNull(value, "id");
    return new ImmutableTimezoneUpdateRequestDTO(newValue, this.name, this.city, this.gmtOffset);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TimezoneUpdateRequestDTO#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTimezoneUpdateRequestDTO withName(String value) {
    String newValue = Objects.requireNonNull(value, "name");
    if (this.name.equals(newValue)) return this;
    return new ImmutableTimezoneUpdateRequestDTO(this.id, newValue, this.city, this.gmtOffset);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TimezoneUpdateRequestDTO#city() city} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for city
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTimezoneUpdateRequestDTO withCity(String value) {
    String newValue = Objects.requireNonNull(value, "city");
    if (this.city.equals(newValue)) return this;
    return new ImmutableTimezoneUpdateRequestDTO(this.id, this.name, newValue, this.gmtOffset);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TimezoneUpdateRequestDTO#gmtOffset() gmtOffset} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for gmtOffset
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTimezoneUpdateRequestDTO withGmtOffset(int value) {
    if (this.gmtOffset == value) return this;
    return new ImmutableTimezoneUpdateRequestDTO(this.id, this.name, this.city, value);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTimezoneUpdateRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTimezoneUpdateRequestDTO
        && equalTo((ImmutableTimezoneUpdateRequestDTO) another);
  }

  private boolean equalTo(ImmutableTimezoneUpdateRequestDTO another) {
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
    @Var int h = 5381;
    h += (h << 5) + id.hashCode();
    h += (h << 5) + name.hashCode();
    h += (h << 5) + city.hashCode();
    h += (h << 5) + gmtOffset;
    return h;
  }

  /**
   * Prints the immutable value {@code TimezoneUpdateRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("TimezoneUpdateRequestDTO")
        .omitNullValues()
        .add("id", id)
        .add("name", name)
        .add("city", city)
        .add("gmtOffset", gmtOffset)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link TimezoneUpdateRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TimezoneUpdateRequestDTO instance
   */
  public static ImmutableTimezoneUpdateRequestDTO copyOf(TimezoneUpdateRequestDTO instance) {
    if (instance instanceof ImmutableTimezoneUpdateRequestDTO) {
      return (ImmutableTimezoneUpdateRequestDTO) instance;
    }
    return ImmutableTimezoneUpdateRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTimezoneUpdateRequestDTO ImmutableTimezoneUpdateRequestDTO}.
   * <pre>
   * ImmutableTimezoneUpdateRequestDTO.builder()
   *    .id(UUID) // required {@link TimezoneUpdateRequestDTO#id() id}
   *    .name(String) // optional {@link TimezoneUpdateRequestDTO#name() name}
   *    .city(String) // optional {@link TimezoneUpdateRequestDTO#city() city}
   *    .gmtOffset(int) // optional {@link TimezoneUpdateRequestDTO#gmtOffset() gmtOffset}
   *    .build();
   * </pre>
   * @return A new ImmutableTimezoneUpdateRequestDTO builder
   */
  public static ImmutableTimezoneUpdateRequestDTO.Builder builder() {
    return new ImmutableTimezoneUpdateRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTimezoneUpdateRequestDTO ImmutableTimezoneUpdateRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "TimezoneUpdateRequestDTO", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private static final long OPT_BIT_GMT_OFFSET = 0x1L;
    private long initBits = 0x1L;
    private long optBits;

    private @Nullable UUID id;
    private @Nullable String name;
    private @Nullable String city;
    private int gmtOffset;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TimezoneUpdateRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(TimezoneUpdateRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      id(instance.id());
      name(instance.name());
      city(instance.city());
      gmtOffset(instance.gmtOffset());
      return this;
    }

    /**
     * Initializes the value for the {@link TimezoneUpdateRequestDTO#id() id} attribute.
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
     * Initializes the value for the {@link TimezoneUpdateRequestDTO#name() name} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link TimezoneUpdateRequestDTO#name() name}.</em>
     * @param name The value for name 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("name")
    public final Builder name(String name) {
      this.name = Objects.requireNonNull(name, "name");
      return this;
    }

    /**
     * Initializes the value for the {@link TimezoneUpdateRequestDTO#city() city} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link TimezoneUpdateRequestDTO#city() city}.</em>
     * @param city The value for city 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("city")
    public final Builder city(String city) {
      this.city = Objects.requireNonNull(city, "city");
      return this;
    }

    /**
     * Initializes the value for the {@link TimezoneUpdateRequestDTO#gmtOffset() gmtOffset} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link TimezoneUpdateRequestDTO#gmtOffset() gmtOffset}.</em>
     * @param gmtOffset The value for gmtOffset 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("gmtOffset")
    public final Builder gmtOffset(int gmtOffset) {
      this.gmtOffset = gmtOffset;
      optBits |= OPT_BIT_GMT_OFFSET;
      return this;
    }

    /**
     * Builds a new {@link ImmutableTimezoneUpdateRequestDTO ImmutableTimezoneUpdateRequestDTO}.
     * @return An immutable instance of TimezoneUpdateRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTimezoneUpdateRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableTimezoneUpdateRequestDTO(this);
    }

    private boolean gmtOffsetIsSet() {
      return (optBits & OPT_BIT_GMT_OFFSET) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      return "Cannot build TimezoneUpdateRequestDTO, some of required attributes are not set " + attributes;
    }
  }
}
