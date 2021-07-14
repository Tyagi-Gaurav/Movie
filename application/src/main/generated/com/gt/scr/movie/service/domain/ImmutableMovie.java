package com.gt.scr.movie.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link Movie}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableMovie.builder()}.
 */
@Generated(from = "Movie", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ImmutableMovie implements Movie {
  private final UUID id;
  private final String name;
  private final int yearProduced;
  private final BigDecimal rating;
  private final @Nullable MovieVideo movieVideo;

  private ImmutableMovie(
      UUID id,
      String name,
      int yearProduced,
      BigDecimal rating,
      @Nullable MovieVideo movieVideo) {
    this.id = id;
    this.name = name;
    this.yearProduced = yearProduced;
    this.rating = rating;
    this.movieVideo = movieVideo;
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
   * @return The value of the {@code movieVideo} attribute
   */
  @JsonProperty("movieVideo")
  @Override
  public Optional<MovieVideo> movieVideo() {
    return Optional.ofNullable(movieVideo);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Movie#id() id} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMovie withId(UUID value) {
    if (this.id == value) return this;
    UUID newValue = Objects.requireNonNull(value, "id");
    return new ImmutableMovie(newValue, this.name, this.yearProduced, this.rating, this.movieVideo);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Movie#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMovie withName(String value) {
    String newValue = Objects.requireNonNull(value, "name");
    if (this.name.equals(newValue)) return this;
    return new ImmutableMovie(this.id, newValue, this.yearProduced, this.rating, this.movieVideo);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Movie#yearProduced() yearProduced} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for yearProduced
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMovie withYearProduced(int value) {
    if (this.yearProduced == value) return this;
    return new ImmutableMovie(this.id, this.name, value, this.rating, this.movieVideo);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Movie#rating() rating} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for rating
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMovie withRating(BigDecimal value) {
    BigDecimal newValue = Objects.requireNonNull(value, "rating");
    if (this.rating.equals(newValue)) return this;
    return new ImmutableMovie(this.id, this.name, this.yearProduced, newValue, this.movieVideo);
  }

  /**
   * Copy the current immutable object by setting a <i>present</i> value for the optional {@link Movie#movieVideo() movieVideo} attribute.
   * @param value The value for movieVideo
   * @return A modified copy of {@code this} object
   */
  public final ImmutableMovie withMovieVideo(MovieVideo value) {
    @Nullable MovieVideo newValue = Objects.requireNonNull(value, "movieVideo");
    if (this.movieVideo == newValue) return this;
    return new ImmutableMovie(this.id, this.name, this.yearProduced, this.rating, newValue);
  }

  /**
   * Copy the current immutable object by setting an optional value for the {@link Movie#movieVideo() movieVideo} attribute.
   * A shallow reference equality check is used on unboxed optional value to prevent copying of the same value by returning {@code this}.
   * @param optional A value for movieVideo
   * @return A modified copy of {@code this} object
   */
  @SuppressWarnings("unchecked") // safe covariant cast
  public final ImmutableMovie withMovieVideo(Optional<? extends MovieVideo> optional) {
    @Nullable MovieVideo value = optional.orElse(null);
    if (this.movieVideo == value) return this;
    return new ImmutableMovie(this.id, this.name, this.yearProduced, this.rating, value);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableMovie} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableMovie
        && equalTo((ImmutableMovie) another);
  }

  private boolean equalTo(ImmutableMovie another) {
    return id.equals(another.id)
        && name.equals(another.name)
        && yearProduced == another.yearProduced
        && rating.equals(another.rating)
        && Objects.equals(movieVideo, another.movieVideo);
  }

  /**
   * Computes a hash code from attributes: {@code id}, {@code name}, {@code yearProduced}, {@code rating}, {@code movieVideo}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + id.hashCode();
    h += (h << 5) + name.hashCode();
    h += (h << 5) + yearProduced;
    h += (h << 5) + rating.hashCode();
    h += (h << 5) + Objects.hashCode(movieVideo);
    return h;
  }

  /**
   * Prints the immutable value {@code Movie} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("Movie")
        .omitNullValues()
        .add("id", id)
        .add("name", name)
        .add("yearProduced", yearProduced)
        .add("rating", rating)
        .add("movieVideo", movieVideo)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link Movie} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable Movie instance
   */
  public static ImmutableMovie copyOf(Movie instance) {
    if (instance instanceof ImmutableMovie) {
      return (ImmutableMovie) instance;
    }
    return ImmutableMovie.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableMovie ImmutableMovie}.
   * <pre>
   * ImmutableMovie.builder()
   *    .id(UUID) // required {@link Movie#id() id}
   *    .name(String) // required {@link Movie#name() name}
   *    .yearProduced(int) // required {@link Movie#yearProduced() yearProduced}
   *    .rating(java.math.BigDecimal) // required {@link Movie#rating() rating}
   *    .movieVideo(com.gt.scr.movie.service.domain.MovieVideo) // optional {@link Movie#movieVideo() movieVideo}
   *    .build();
   * </pre>
   * @return A new ImmutableMovie builder
   */
  public static ImmutableMovie.Builder builder() {
    return new ImmutableMovie.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableMovie ImmutableMovie}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "Movie", generator = "Immutables")
  @NotThreadSafe
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private static final long INIT_BIT_NAME = 0x2L;
    private static final long INIT_BIT_YEAR_PRODUCED = 0x4L;
    private static final long INIT_BIT_RATING = 0x8L;
    private long initBits = 0xfL;

    private @Nullable UUID id;
    private @Nullable String name;
    private int yearProduced;
    private @Nullable BigDecimal rating;
    private @Nullable MovieVideo movieVideo;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code Movie} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(Movie instance) {
      Objects.requireNonNull(instance, "instance");
      id(instance.id());
      name(instance.name());
      yearProduced(instance.yearProduced());
      rating(instance.rating());
      Optional<MovieVideo> movieVideoOptional = instance.movieVideo();
      if (movieVideoOptional.isPresent()) {
        movieVideo(movieVideoOptional);
      }
      return this;
    }

    /**
     * Initializes the value for the {@link Movie#id() id} attribute.
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
     * Initializes the value for the {@link Movie#name() name} attribute.
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
     * Initializes the value for the {@link Movie#yearProduced() yearProduced} attribute.
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
     * Initializes the value for the {@link Movie#rating() rating} attribute.
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
     * Initializes the optional value {@link Movie#movieVideo() movieVideo} to movieVideo.
     * @param movieVideo The value for movieVideo
     * @return {@code this} builder for chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder movieVideo(MovieVideo movieVideo) {
      this.movieVideo = Objects.requireNonNull(movieVideo, "movieVideo");
      return this;
    }

    /**
     * Initializes the optional value {@link Movie#movieVideo() movieVideo} to movieVideo.
     * @param movieVideo The value for movieVideo
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("movieVideo")
    public final Builder movieVideo(Optional<? extends MovieVideo> movieVideo) {
      this.movieVideo = movieVideo.orElse(null);
      return this;
    }

    /**
     * Builds a new {@link ImmutableMovie ImmutableMovie}.
     * @return An immutable instance of Movie
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableMovie build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableMovie(id, name, yearProduced, rating, movieVideo);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      if ((initBits & INIT_BIT_NAME) != 0) attributes.add("name");
      if ((initBits & INIT_BIT_YEAR_PRODUCED) != 0) attributes.add("yearProduced");
      if ((initBits & INIT_BIT_RATING) != 0) attributes.add("rating");
      return "Cannot build Movie, some of required attributes are not set " + attributes;
    }
  }
}
