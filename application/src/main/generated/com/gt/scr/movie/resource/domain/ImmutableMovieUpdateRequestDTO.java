package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.math.BigDecimal;
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
 * Immutable implementation of {@link MovieUpdateRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableMovieUpdateRequestDTO.builder()}.
 */
@Generated(from = "MovieUpdateRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableMovieUpdateRequestDTO
    implements MovieUpdateRequestDTO {
  private final UUID id;
  private final String name;
  private final BigDecimal rating;
  private final int yearProduced;

  private ImmutableMovieUpdateRequestDTO(ImmutableMovieUpdateRequestDTO.Builder builder) {
    this.id = builder.id;
    if (builder.name != null) {
      initShim.name(builder.name);
    }
    if (builder.rating != null) {
      initShim.rating(builder.rating);
    }
    if (builder.yearProducedIsSet()) {
      initShim.yearProduced(builder.yearProduced);
    }
    this.name = initShim.name();
    this.rating = initShim.rating();
    this.yearProduced = initShim.yearProduced();
    this.initShim = null;
  }

  private ImmutableMovieUpdateRequestDTO(UUID id, String name, BigDecimal rating, int yearProduced) {
    this.id = id;
    this.name = name;
    this.rating = rating;
    this.yearProduced = yearProduced;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "MovieUpdateRequestDTO", generator = "Immutables")
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

    private byte ratingBuildStage = STAGE_UNINITIALIZED;
    private BigDecimal rating;

    BigDecimal rating() {
      if (ratingBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (ratingBuildStage == STAGE_UNINITIALIZED) {
        ratingBuildStage = STAGE_INITIALIZING;
        this.rating = Objects.requireNonNull(ratingInitialize(), "rating");
        ratingBuildStage = STAGE_INITIALIZED;
      }
      return this.rating;
    }

    void rating(BigDecimal rating) {
      this.rating = rating;
      ratingBuildStage = STAGE_INITIALIZED;
    }

    private byte yearProducedBuildStage = STAGE_UNINITIALIZED;
    private int yearProduced;

    int yearProduced() {
      if (yearProducedBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (yearProducedBuildStage == STAGE_UNINITIALIZED) {
        yearProducedBuildStage = STAGE_INITIALIZING;
        this.yearProduced = yearProducedInitialize();
        yearProducedBuildStage = STAGE_INITIALIZED;
      }
      return this.yearProduced;
    }

    void yearProduced(int yearProduced) {
      this.yearProduced = yearProduced;
      yearProducedBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (nameBuildStage == STAGE_INITIALIZING) attributes.add("name");
      if (ratingBuildStage == STAGE_INITIALIZING) attributes.add("rating");
      if (yearProducedBuildStage == STAGE_INITIALIZING) attributes.add("yearProduced");
      return "Cannot build MovieUpdateRequestDTO, attribute initializers form cycle " + attributes;
    }
  }

  private String nameInitialize() {
    return MovieUpdateRequestDTO.super.name();
  }

  private BigDecimal ratingInitialize() {
    return MovieUpdateRequestDTO.super.rating();
  }

  private int yearProducedInitialize() {
    return MovieUpdateRequestDTO.super.yearProduced();
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
   * @return The value of the {@code rating} attribute
   */
  @JsonProperty("rating")
  @Override
  public BigDecimal rating() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.rating()
        : this.rating;
  }

  /**
   * @return The value of the {@code yearProduced} attribute
   */
  @JsonProperty("yearProduced")
  @Override
  public int yearProduced() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.yearProduced()
        : this.yearProduced;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MovieUpdateRequestDTO#id() id} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMovieUpdateRequestDTO withId(UUID value) {
    if (this.id == value) return this;
    UUID newValue = Objects.requireNonNull(value, "id");
    return new ImmutableMovieUpdateRequestDTO(newValue, this.name, this.rating, this.yearProduced);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MovieUpdateRequestDTO#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMovieUpdateRequestDTO withName(String value) {
    String newValue = Objects.requireNonNull(value, "name");
    if (this.name.equals(newValue)) return this;
    return new ImmutableMovieUpdateRequestDTO(this.id, newValue, this.rating, this.yearProduced);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MovieUpdateRequestDTO#rating() rating} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for rating
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMovieUpdateRequestDTO withRating(BigDecimal value) {
    BigDecimal newValue = Objects.requireNonNull(value, "rating");
    if (this.rating.equals(newValue)) return this;
    return new ImmutableMovieUpdateRequestDTO(this.id, this.name, newValue, this.yearProduced);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MovieUpdateRequestDTO#yearProduced() yearProduced} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for yearProduced
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMovieUpdateRequestDTO withYearProduced(int value) {
    if (this.yearProduced == value) return this;
    return new ImmutableMovieUpdateRequestDTO(this.id, this.name, this.rating, value);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableMovieUpdateRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableMovieUpdateRequestDTO
        && equalTo((ImmutableMovieUpdateRequestDTO) another);
  }

  private boolean equalTo(ImmutableMovieUpdateRequestDTO another) {
    return id.equals(another.id)
        && name.equals(another.name)
        && rating.equals(another.rating)
        && yearProduced == another.yearProduced;
  }

  /**
   * Computes a hash code from attributes: {@code id}, {@code name}, {@code rating}, {@code yearProduced}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + id.hashCode();
    h += (h << 5) + name.hashCode();
    h += (h << 5) + rating.hashCode();
    h += (h << 5) + yearProduced;
    return h;
  }

  /**
   * Prints the immutable value {@code MovieUpdateRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MovieUpdateRequestDTO")
        .omitNullValues()
        .add("id", id)
        .add("name", name)
        .add("rating", rating)
        .add("yearProduced", yearProduced)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link MovieUpdateRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable MovieUpdateRequestDTO instance
   */
  public static ImmutableMovieUpdateRequestDTO copyOf(MovieUpdateRequestDTO instance) {
    if (instance instanceof ImmutableMovieUpdateRequestDTO) {
      return (ImmutableMovieUpdateRequestDTO) instance;
    }
    return ImmutableMovieUpdateRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableMovieUpdateRequestDTO ImmutableMovieUpdateRequestDTO}.
   * <pre>
   * ImmutableMovieUpdateRequestDTO.builder()
   *    .id(UUID) // required {@link MovieUpdateRequestDTO#id() id}
   *    .name(String) // optional {@link MovieUpdateRequestDTO#name() name}
   *    .rating(java.math.BigDecimal) // optional {@link MovieUpdateRequestDTO#rating() rating}
   *    .yearProduced(int) // optional {@link MovieUpdateRequestDTO#yearProduced() yearProduced}
   *    .build();
   * </pre>
   * @return A new ImmutableMovieUpdateRequestDTO builder
   */
  public static ImmutableMovieUpdateRequestDTO.Builder builder() {
    return new ImmutableMovieUpdateRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableMovieUpdateRequestDTO ImmutableMovieUpdateRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "MovieUpdateRequestDTO", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private static final long OPT_BIT_YEAR_PRODUCED = 0x1L;
    private long initBits = 0x1L;
    private long optBits;

    private @Nullable UUID id;
    private @Nullable String name;
    private @Nullable BigDecimal rating;
    private int yearProduced;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MovieUpdateRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MovieUpdateRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      id(instance.id());
      name(instance.name());
      rating(instance.rating());
      yearProduced(instance.yearProduced());
      return this;
    }

    /**
     * Initializes the value for the {@link MovieUpdateRequestDTO#id() id} attribute.
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
     * Initializes the value for the {@link MovieUpdateRequestDTO#name() name} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link MovieUpdateRequestDTO#name() name}.</em>
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
     * Initializes the value for the {@link MovieUpdateRequestDTO#rating() rating} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link MovieUpdateRequestDTO#rating() rating}.</em>
     * @param rating The value for rating 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("rating")
    public final Builder rating(BigDecimal rating) {
      this.rating = Objects.requireNonNull(rating, "rating");
      return this;
    }

    /**
     * Initializes the value for the {@link MovieUpdateRequestDTO#yearProduced() yearProduced} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link MovieUpdateRequestDTO#yearProduced() yearProduced}.</em>
     * @param yearProduced The value for yearProduced 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("yearProduced")
    public final Builder yearProduced(int yearProduced) {
      this.yearProduced = yearProduced;
      optBits |= OPT_BIT_YEAR_PRODUCED;
      return this;
    }

    /**
     * Builds a new {@link ImmutableMovieUpdateRequestDTO ImmutableMovieUpdateRequestDTO}.
     * @return An immutable instance of MovieUpdateRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableMovieUpdateRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableMovieUpdateRequestDTO(this);
    }

    private boolean yearProducedIsSet() {
      return (optBits & OPT_BIT_YEAR_PRODUCED) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      return "Cannot build MovieUpdateRequestDTO, some of required attributes are not set " + attributes;
    }
  }
}
