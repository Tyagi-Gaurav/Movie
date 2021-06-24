package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link MoviesDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableMoviesDTO.builder()}.
 */
@Generated(from = "MoviesDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableMoviesDTO implements MoviesDTO {
  private final ImmutableList<MovieDTO> timezones;

  private ImmutableMoviesDTO(ImmutableList<MovieDTO> timezones) {
    this.timezones = timezones;
  }

  /**
   * @return The value of the {@code timezones} attribute
   */
  @JsonProperty("timezones")
  @Override
  public ImmutableList<MovieDTO> timezones() {
    return timezones;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link MoviesDTO#timezones() timezones}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableMoviesDTO withTimezones(MovieDTO... elements) {
    ImmutableList<MovieDTO> newValue = ImmutableList.copyOf(elements);
    return new ImmutableMoviesDTO(newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link MoviesDTO#timezones() timezones}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of timezones elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableMoviesDTO withTimezones(Iterable<? extends MovieDTO> elements) {
    if (this.timezones == elements) return this;
    ImmutableList<MovieDTO> newValue = ImmutableList.copyOf(elements);
    return new ImmutableMoviesDTO(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableMoviesDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableMoviesDTO
        && equalTo((ImmutableMoviesDTO) another);
  }

  private boolean equalTo(ImmutableMoviesDTO another) {
    return timezones.equals(another.timezones);
  }

  /**
   * Computes a hash code from attributes: {@code timezones}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + timezones.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code MoviesDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MoviesDTO")
        .omitNullValues()
        .add("timezones", timezones)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link MoviesDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable MoviesDTO instance
   */
  public static ImmutableMoviesDTO copyOf(MoviesDTO instance) {
    if (instance instanceof ImmutableMoviesDTO) {
      return (ImmutableMoviesDTO) instance;
    }
    return ImmutableMoviesDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableMoviesDTO ImmutableMoviesDTO}.
   * <pre>
   * ImmutableMoviesDTO.builder()
   *    .addTimezones|addAllTimezones(com.gt.scr.movie.resource.domain.MovieDTO) // {@link MoviesDTO#timezones() timezones} elements
   *    .build();
   * </pre>
   * @return A new ImmutableMoviesDTO builder
   */
  public static ImmutableMoviesDTO.Builder builder() {
    return new ImmutableMoviesDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableMoviesDTO ImmutableMoviesDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "MoviesDTO", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private ImmutableList.Builder<MovieDTO> timezones = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MoviesDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MoviesDTO instance) {
      Objects.requireNonNull(instance, "instance");
      addAllTimezones(instance.timezones());
      return this;
    }

    /**
     * Adds one element to {@link MoviesDTO#timezones() timezones} list.
     * @param element A timezones element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addTimezones(MovieDTO element) {
      this.timezones.add(element);
      return this;
    }

    /**
     * Adds elements to {@link MoviesDTO#timezones() timezones} list.
     * @param elements An array of timezones elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addTimezones(MovieDTO... elements) {
      this.timezones.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link MoviesDTO#timezones() timezones} list.
     * @param elements An iterable of timezones elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("timezones")
    public final Builder timezones(Iterable<? extends MovieDTO> elements) {
      this.timezones = ImmutableList.builder();
      return addAllTimezones(elements);
    }

    /**
     * Adds elements to {@link MoviesDTO#timezones() timezones} list.
     * @param elements An iterable of timezones elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllTimezones(Iterable<? extends MovieDTO> elements) {
      this.timezones.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link ImmutableMoviesDTO ImmutableMoviesDTO}.
     * @return An immutable instance of MoviesDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableMoviesDTO build() {
      return new ImmutableMoviesDTO(timezones.build());
    }
  }
}
