package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link TestMovieDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTestMovieDTO.builder()}.
 */
@Generated(from = "TestMovieDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableTestMovieDTO implements TestMovieDTO {
  private final UUID id;
  private final String name;
  private final BigDecimal rating;
  private final int yearProduced;

  private ImmutableTestMovieDTO(UUID id, String name, BigDecimal rating, int yearProduced) {
    this.id = id;
    this.name = name;
    this.rating = rating;
    this.yearProduced = yearProduced;
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
   * @return The value of the {@code rating} attribute
   */
  @JsonProperty("rating")
  @Override
  public BigDecimal rating() {
    return rating;
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
   * Copy the current immutable object by setting a value for the {@link TestMovieDTO#id() id} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestMovieDTO withId(UUID value) {
    if (this.id == value) return this;
    UUID newValue = Objects.requireNonNull(value, "id");
    return new ImmutableTestMovieDTO(newValue, this.name, this.rating, this.yearProduced);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestMovieDTO#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestMovieDTO withName(String value) {
    String newValue = Objects.requireNonNull(value, "name");
    if (this.name.equals(newValue)) return this;
    return new ImmutableTestMovieDTO(this.id, newValue, this.rating, this.yearProduced);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestMovieDTO#rating() rating} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for rating
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestMovieDTO withRating(BigDecimal value) {
    BigDecimal newValue = Objects.requireNonNull(value, "rating");
    if (this.rating.equals(newValue)) return this;
    return new ImmutableTestMovieDTO(this.id, this.name, newValue, this.yearProduced);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestMovieDTO#yearProduced() yearProduced} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for yearProduced
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestMovieDTO withYearProduced(int value) {
    if (this.yearProduced == value) return this;
    return new ImmutableTestMovieDTO(this.id, this.name, this.rating, value);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTestMovieDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTestMovieDTO
        && equalTo(0, (ImmutableTestMovieDTO) another);
  }

  private boolean equalTo(int synthetic, ImmutableTestMovieDTO another) {
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
    int h = 5381;
    h += (h << 5) + id.hashCode();
    h += (h << 5) + name.hashCode();
    h += (h << 5) + rating.hashCode();
    h += (h << 5) + yearProduced;
    return h;
  }

  /**
   * Prints the immutable value {@code TestMovieDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "TestMovieDTO{"
        + "id=" + id
        + ", name=" + name
        + ", rating=" + rating
        + ", yearProduced=" + yearProduced
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link TestMovieDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TestMovieDTO instance
   */
  public static ImmutableTestMovieDTO copyOf(TestMovieDTO instance) {
    if (instance instanceof ImmutableTestMovieDTO) {
      return (ImmutableTestMovieDTO) instance;
    }
    return ImmutableTestMovieDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTestMovieDTO ImmutableTestMovieDTO}.
   * <pre>
   * ImmutableTestMovieDTO.builder()
   *    .id(UUID) // required {@link TestMovieDTO#id() id}
   *    .name(String) // required {@link TestMovieDTO#name() name}
   *    .rating(java.math.BigDecimal) // required {@link TestMovieDTO#rating() rating}
   *    .yearProduced(int) // required {@link TestMovieDTO#yearProduced() yearProduced}
   *    .build();
   * </pre>
   * @return A new ImmutableTestMovieDTO builder
   */
  public static ImmutableTestMovieDTO.Builder builder() {
    return new ImmutableTestMovieDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTestMovieDTO ImmutableTestMovieDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "TestMovieDTO", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private static final long INIT_BIT_NAME = 0x2L;
    private static final long INIT_BIT_RATING = 0x4L;
    private static final long INIT_BIT_YEAR_PRODUCED = 0x8L;
    private long initBits = 0xfL;

    private UUID id;
    private String name;
    private BigDecimal rating;
    private int yearProduced;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TestMovieDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(TestMovieDTO instance) {
      Objects.requireNonNull(instance, "instance");
      id(instance.id());
      name(instance.name());
      rating(instance.rating());
      yearProduced(instance.yearProduced());
      return this;
    }

    /**
     * Initializes the value for the {@link TestMovieDTO#id() id} attribute.
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
     * Initializes the value for the {@link TestMovieDTO#name() name} attribute.
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
     * Initializes the value for the {@link TestMovieDTO#rating() rating} attribute.
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
     * Initializes the value for the {@link TestMovieDTO#yearProduced() yearProduced} attribute.
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
     * Builds a new {@link ImmutableTestMovieDTO ImmutableTestMovieDTO}.
     * @return An immutable instance of TestMovieDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTestMovieDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableTestMovieDTO(id, name, rating, yearProduced);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      if ((initBits & INIT_BIT_NAME) != 0) attributes.add("name");
      if ((initBits & INIT_BIT_RATING) != 0) attributes.add("rating");
      if ((initBits & INIT_BIT_YEAR_PRODUCED) != 0) attributes.add("yearProduced");
      return "Cannot build TestMovieDTO, some of required attributes are not set " + attributes;
    }
  }
}
