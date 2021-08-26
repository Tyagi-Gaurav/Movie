package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link TestMovieCreateRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTestMovieCreateRequestDTO.builder()}.
 */
@Generated(from = "TestMovieCreateRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableTestMovieCreateRequestDTO
    implements TestMovieCreateRequestDTO {
  private final String name;
  private final int yearProduced;
  private final BigDecimal rating;

  private ImmutableTestMovieCreateRequestDTO(String name, int yearProduced, BigDecimal rating) {
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
   * Copy the current immutable object by setting a value for the {@link TestMovieCreateRequestDTO#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestMovieCreateRequestDTO withName(String value) {
    String newValue = Objects.requireNonNull(value, "name");
    if (this.name.equals(newValue)) return this;
    return new ImmutableTestMovieCreateRequestDTO(newValue, this.yearProduced, this.rating);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestMovieCreateRequestDTO#yearProduced() yearProduced} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for yearProduced
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestMovieCreateRequestDTO withYearProduced(int value) {
    if (this.yearProduced == value) return this;
    return new ImmutableTestMovieCreateRequestDTO(this.name, value, this.rating);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestMovieCreateRequestDTO#rating() rating} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for rating
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestMovieCreateRequestDTO withRating(BigDecimal value) {
    BigDecimal newValue = Objects.requireNonNull(value, "rating");
    if (this.rating.equals(newValue)) return this;
    return new ImmutableTestMovieCreateRequestDTO(this.name, this.yearProduced, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTestMovieCreateRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTestMovieCreateRequestDTO
        && equalTo(0, (ImmutableTestMovieCreateRequestDTO) another);
  }

  private boolean equalTo(int synthetic, ImmutableTestMovieCreateRequestDTO another) {
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
    int h = 5381;
    h += (h << 5) + name.hashCode();
    h += (h << 5) + yearProduced;
    h += (h << 5) + rating.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code TestMovieCreateRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "TestMovieCreateRequestDTO{"
        + "name=" + name
        + ", yearProduced=" + yearProduced
        + ", rating=" + rating
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link TestMovieCreateRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TestMovieCreateRequestDTO instance
   */
  public static ImmutableTestMovieCreateRequestDTO copyOf(TestMovieCreateRequestDTO instance) {
    if (instance instanceof ImmutableTestMovieCreateRequestDTO) {
      return (ImmutableTestMovieCreateRequestDTO) instance;
    }
    return ImmutableTestMovieCreateRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTestMovieCreateRequestDTO ImmutableTestMovieCreateRequestDTO}.
   * <pre>
   * ImmutableTestMovieCreateRequestDTO.builder()
   *    .name(String) // required {@link TestMovieCreateRequestDTO#name() name}
   *    .yearProduced(int) // required {@link TestMovieCreateRequestDTO#yearProduced() yearProduced}
   *    .rating(java.math.BigDecimal) // required {@link TestMovieCreateRequestDTO#rating() rating}
   *    .build();
   * </pre>
   * @return A new ImmutableTestMovieCreateRequestDTO builder
   */
  public static ImmutableTestMovieCreateRequestDTO.Builder builder() {
    return new ImmutableTestMovieCreateRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTestMovieCreateRequestDTO ImmutableTestMovieCreateRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "TestMovieCreateRequestDTO", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_NAME = 0x1L;
    private static final long INIT_BIT_YEAR_PRODUCED = 0x2L;
    private static final long INIT_BIT_RATING = 0x4L;
    private long initBits = 0x7L;

    private String name;
    private int yearProduced;
    private BigDecimal rating;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TestMovieCreateRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(TestMovieCreateRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      name(instance.name());
      yearProduced(instance.yearProduced());
      rating(instance.rating());
      return this;
    }

    /**
     * Initializes the value for the {@link TestMovieCreateRequestDTO#name() name} attribute.
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
     * Initializes the value for the {@link TestMovieCreateRequestDTO#yearProduced() yearProduced} attribute.
     * @param yearProduced The value for yearProduced 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("yearProduced")
    public final Builder yearProduced(int yearProduced) {
      this.yearProduced = yearProduced;
      initBits &= ~INIT_BIT_YEAR_PRODUCED;
      return this;
    }

    /**
     * Initializes the value for the {@link TestMovieCreateRequestDTO#rating() rating} attribute.
     * @param rating The value for rating 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("rating")
    public final Builder rating(BigDecimal rating) {
      this.rating = Objects.requireNonNull(rating, "rating");
      initBits &= ~INIT_BIT_RATING;
      return this;
    }

    /**
     * Builds a new {@link ImmutableTestMovieCreateRequestDTO ImmutableTestMovieCreateRequestDTO}.
     * @return An immutable instance of TestMovieCreateRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTestMovieCreateRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableTestMovieCreateRequestDTO(name, yearProduced, rating);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_NAME) != 0) attributes.add("name");
      if ((initBits & INIT_BIT_YEAR_PRODUCED) != 0) attributes.add("yearProduced");
      if ((initBits & INIT_BIT_RATING) != 0) attributes.add("rating");
      return "Cannot build TestMovieCreateRequestDTO, some of required attributes are not set " + attributes;
    }
  }
}
