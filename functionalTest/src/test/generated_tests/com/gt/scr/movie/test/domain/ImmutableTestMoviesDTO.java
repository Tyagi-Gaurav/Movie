package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link TestMoviesDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTestMoviesDTO.builder()}.
 */
@Generated(from = "TestMoviesDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableTestMoviesDTO implements TestMoviesDTO {
  private final List<TestMovieDTO> movies;

  private ImmutableTestMoviesDTO(List<TestMovieDTO> movies) {
    this.movies = movies;
  }

  /**
   * @return The value of the {@code movies} attribute
   */
  @JsonProperty("movies")
  @Override
  public List<TestMovieDTO> movies() {
    return movies;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link TestMoviesDTO#movies() movies}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableTestMoviesDTO withMovies(TestMovieDTO... elements) {
    List<TestMovieDTO> newValue = List.of(elements);
    return new ImmutableTestMoviesDTO(newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link TestMoviesDTO#movies() movies}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of movies elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableTestMoviesDTO withMovies(Iterable<? extends TestMovieDTO> elements) {
    if (this.movies == elements) return this;
    List<TestMovieDTO> newValue = elements instanceof Collection<?>
        ? List.copyOf((Collection<? extends TestMovieDTO>) elements)
        : StreamSupport.stream(elements.spliterator(), false)
            .collect(Collectors.toUnmodifiableList());
    return new ImmutableTestMoviesDTO(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTestMoviesDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTestMoviesDTO
        && equalTo(0, (ImmutableTestMoviesDTO) another);
  }

  private boolean equalTo(int synthetic, ImmutableTestMoviesDTO another) {
    return movies.equals(another.movies);
  }

  /**
   * Computes a hash code from attributes: {@code movies}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + movies.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code TestMoviesDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "TestMoviesDTO{"
        + "movies=" + movies
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link TestMoviesDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TestMoviesDTO instance
   */
  public static ImmutableTestMoviesDTO copyOf(TestMoviesDTO instance) {
    if (instance instanceof ImmutableTestMoviesDTO) {
      return (ImmutableTestMoviesDTO) instance;
    }
    return ImmutableTestMoviesDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTestMoviesDTO ImmutableTestMoviesDTO}.
   * <pre>
   * ImmutableTestMoviesDTO.builder()
   *    .addMovies|addAllMovies(com.gt.scr.movie.test.domain.TestMovieDTO) // {@link TestMoviesDTO#movies() movies} elements
   *    .build();
   * </pre>
   * @return A new ImmutableTestMoviesDTO builder
   */
  public static ImmutableTestMoviesDTO.Builder builder() {
    return new ImmutableTestMoviesDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTestMoviesDTO ImmutableTestMoviesDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "TestMoviesDTO", generator = "Immutables")
  public static final class Builder {
    private List<TestMovieDTO> movies = new ArrayList<TestMovieDTO>();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TestMoviesDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(TestMoviesDTO instance) {
      Objects.requireNonNull(instance, "instance");
      addAllMovies(instance.movies());
      return this;
    }

    /**
     * Adds one element to {@link TestMoviesDTO#movies() movies} list.
     * @param element A movies element
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addMovies(TestMovieDTO element) {
      this.movies.add(Objects.requireNonNull(element, "movies element"));
      return this;
    }

    /**
     * Adds elements to {@link TestMoviesDTO#movies() movies} list.
     * @param elements An array of movies elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addMovies(TestMovieDTO... elements) {
      for (TestMovieDTO element : elements) {
        this.movies.add(Objects.requireNonNull(element, "movies element"));
      }
      return this;
    }


    /**
     * Sets or replaces all elements for {@link TestMoviesDTO#movies() movies} list.
     * @param elements An iterable of movies elements
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("movies")
    public final Builder movies(Iterable<? extends TestMovieDTO> elements) {
      this.movies.clear();
      return addAllMovies(elements);
    }

    /**
     * Adds elements to {@link TestMoviesDTO#movies() movies} list.
     * @param elements An iterable of movies elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addAllMovies(Iterable<? extends TestMovieDTO> elements) {
      for (TestMovieDTO element : elements) {
        this.movies.add(Objects.requireNonNull(element, "movies element"));
      }
      return this;
    }

    /**
     * Builds a new {@link ImmutableTestMoviesDTO ImmutableTestMoviesDTO}.
     * @return An immutable instance of TestMoviesDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTestMoviesDTO build() {
      return new ImmutableTestMoviesDTO(List.copyOf(movies));
    }
  }
}
