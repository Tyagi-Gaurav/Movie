package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.math.BigDecimal;
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
 * Immutable implementation of {@link MovieCreateRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableMovieCreateRequestDTO.builder()}.
 */
@Generated(from = "MovieCreateRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableMovieCreateRequestDTO
    implements MovieCreateRequestDTO {
  private final String name;
  private final int yearProduced;
  private final BigDecimal rating;

  private ImmutableMovieCreateRequestDTO(String name, int yearProduced, BigDecimal rating) {
    this.name = name;
    this.yearProduced = yearProduced;
    this.rating = rating;
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
   * @return The value of the {@code yearProduced} attribute
   */
  @JsonProperty("yearProduced")
  @Override
  public int yearProduced() {
    return yearProduced;
  }

  /**
   * @return The value of the {@code rating} attribute
   */
  @JsonProperty("rating")
  @Override
  public BigDecimal rating() {
    return rating;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MovieCreateRequestDTO#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMovieCreateRequestDTO withName(String value) {
    String newValue = Objects.requireNonNull(value, "name");
    if (this.name.equals(newValue)) return this;
    return validate(new ImmutableMovieCreateRequestDTO(newValue, this.yearProduced, this.rating));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MovieCreateRequestDTO#yearProduced() yearProduced} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for yearProduced
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMovieCreateRequestDTO withYearProduced(int value) {
    if (this.yearProduced == value) return this;
    return validate(new ImmutableMovieCreateRequestDTO(this.name, value, this.rating));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MovieCreateRequestDTO#rating() rating} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for rating
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMovieCreateRequestDTO withRating(BigDecimal value) {
    BigDecimal newValue = Objects.requireNonNull(value, "rating");
    if (this.rating.equals(newValue)) return this;
    return validate(new ImmutableMovieCreateRequestDTO(this.name, this.yearProduced, newValue));
  }

  /**
   * This instance is equal to all instances of {@code ImmutableMovieCreateRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableMovieCreateRequestDTO
        && equalTo((ImmutableMovieCreateRequestDTO) another);
  }

  private boolean equalTo(ImmutableMovieCreateRequestDTO another) {
    return name.equals(another.name)
        && yearProduced == another.yearProduced
        && rating.equals(another.rating);
  }

  /**
   * Computes a hash code from attributes: {@code name}, {@code yearProduced}, {@code rating}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + name.hashCode();
    h += (h << 5) + yearProduced;
    h += (h << 5) + rating.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code MovieCreateRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MovieCreateRequestDTO")
        .omitNullValues()
        .add("name", name)
        .add("yearProduced", yearProduced)
        .add("rating", rating)
        .toString();
  }

  private static ImmutableMovieCreateRequestDTO validate(ImmutableMovieCreateRequestDTO instance) {
    instance.check();
    return instance;
  }

  /**
   * Creates an immutable copy of a {@link MovieCreateRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable MovieCreateRequestDTO instance
   */
  public static ImmutableMovieCreateRequestDTO copyOf(MovieCreateRequestDTO instance) {
    if (instance instanceof ImmutableMovieCreateRequestDTO) {
      return (ImmutableMovieCreateRequestDTO) instance;
    }
    return ImmutableMovieCreateRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableMovieCreateRequestDTO ImmutableMovieCreateRequestDTO}.
   * <pre>
   * ImmutableMovieCreateRequestDTO.builder()
   *    .name(String) // required {@link MovieCreateRequestDTO#name() name}
   *    .yearProduced(int) // required {@link MovieCreateRequestDTO#yearProduced() yearProduced}
   *    .rating(java.math.BigDecimal) // required {@link MovieCreateRequestDTO#rating() rating}
   *    .build();
   * </pre>
   * @return A new ImmutableMovieCreateRequestDTO builder
   */
  public static ImmutableMovieCreateRequestDTO.Builder builder() {
    return new ImmutableMovieCreateRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableMovieCreateRequestDTO ImmutableMovieCreateRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "MovieCreateRequestDTO", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_NAME = 0x1L;
    private static final long INIT_BIT_YEAR_PRODUCED = 0x2L;
    private static final long INIT_BIT_RATING = 0x4L;
    private long initBits = 0x7L;

    private @Nullable String name;
    private int yearProduced;
    private @Nullable BigDecimal rating;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MovieCreateRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MovieCreateRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      name(instance.name());
      yearProduced(instance.yearProduced());
      rating(instance.rating());
      return this;
    }

    /**
     * Initializes the value for the {@link MovieCreateRequestDTO#name() name} attribute.
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
     * Initializes the value for the {@link MovieCreateRequestDTO#yearProduced() yearProduced} attribute.
     * @param yearProduced The value for yearProduced 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("yearProduced")
    public final Builder yearProduced(int yearProduced) {
      this.yearProduced = yearProduced;
      initBits &= ~INIT_BIT_YEAR_PRODUCED;
      return this;
    }

    /**
     * Initializes the value for the {@link MovieCreateRequestDTO#rating() rating} attribute.
     * @param rating The value for rating 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("rating")
    public final Builder rating(BigDecimal rating) {
      this.rating = Objects.requireNonNull(rating, "rating");
      initBits &= ~INIT_BIT_RATING;
      return this;
    }

    /**
     * Builds a new {@link ImmutableMovieCreateRequestDTO ImmutableMovieCreateRequestDTO}.
     * @return An immutable instance of MovieCreateRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableMovieCreateRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return ImmutableMovieCreateRequestDTO.validate(new ImmutableMovieCreateRequestDTO(name, yearProduced, rating));
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_NAME) != 0) attributes.add("name");
      if ((initBits & INIT_BIT_YEAR_PRODUCED) != 0) attributes.add("yearProduced");
      if ((initBits & INIT_BIT_RATING) != 0) attributes.add("rating");
      return "Cannot build MovieCreateRequestDTO, some of required attributes are not set " + attributes;
    }
  }
}
