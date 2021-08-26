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
  private final ImmutableList<MovieDTO> movies;

  private ImmutableMoviesDTO(ImmutableList<MovieDTO> movies) {
    this.movies = movies;
  }

  /**
   * @return The value of the {@code movies} attribute
   */
  @JsonProperty("movies")
  @Override
  public ImmutableList<MovieDTO> movies() {
    return movies;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link MoviesDTO#movies() movies}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableMoviesDTO withMovies(MovieDTO... elements) {
    ImmutableList<MovieDTO> newValue = ImmutableList.copyOf(elements);
    return new ImmutableMoviesDTO(newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link MoviesDTO#movies() movies}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of movies elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableMoviesDTO withMovies(Iterable<? extends MovieDTO> elements) {
    if (this.movies == elements) return this;
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
    return movies.equals(another.movies);
  }

  /**
   * Computes a hash code from attributes: {@code movies}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + movies.hashCode();
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
        .add("movies", movies)
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
   *    .addMovies|addAllMovies(com.gt.scr.movie.resource.domain.MovieDTO) // {@link MoviesDTO#movies() movies} elements
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
    private ImmutableList.Builder<MovieDTO> movies = ImmutableList.builder();

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
      addAllMovies(instance.movies());
      return this;
    }

    /**
     * Adds one element to {@link MoviesDTO#movies() movies} list.
     * @param element A movies element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMovies(MovieDTO element) {
      this.movies.add(element);
      return this;
    }

    /**
     * Adds elements to {@link MoviesDTO#movies() movies} list.
     * @param elements An array of movies elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMovies(MovieDTO... elements) {
      this.movies.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link MoviesDTO#movies() movies} list.
     * @param elements An iterable of movies elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("movies")
    public final Builder movies(Iterable<? extends MovieDTO> elements) {
      this.movies = ImmutableList.builder();
      return addAllMovies(elements);
    }

    /**
     * Adds elements to {@link MoviesDTO#movies() movies} list.
     * @param elements An iterable of movies elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllMovies(Iterable<? extends MovieDTO> elements) {
      this.movies.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link ImmutableMoviesDTO ImmutableMoviesDTO}.
     * @return An immutable instance of MoviesDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableMoviesDTO build() {
      return new ImmutableMoviesDTO(movies.build());
    }
  }
}