package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link MovieVideoUpdateRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableMovieVideoUpdateRequestDTO.builder()}.
 */
@Generated(from = "MovieVideoUpdateRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableMovieVideoUpdateRequestDTO
    implements MovieVideoUpdateRequestDTO {
  private final byte[] content;

  private ImmutableMovieVideoUpdateRequestDTO(byte[] content) {
    this.content = content;
  }

  /**
   * @return A cloned {@code content} array
   */
  @JsonProperty("content")
  @Override
  public byte[] content() {
    return content.clone();
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link MovieVideoUpdateRequestDTO#content() content}.
   * The array is cloned before being saved as attribute values.
   * @param elements The non-null elements for content
   * @return A modified copy of {@code this} object
   */
  public final ImmutableMovieVideoUpdateRequestDTO withContent(byte... elements) {
    byte[] newValue = elements.clone();
    return new ImmutableMovieVideoUpdateRequestDTO(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableMovieVideoUpdateRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableMovieVideoUpdateRequestDTO
        && equalTo((ImmutableMovieVideoUpdateRequestDTO) another);
  }

  private boolean equalTo(ImmutableMovieVideoUpdateRequestDTO another) {
    return Arrays.equals(content, another.content);
  }

  /**
   * Computes a hash code from attributes: {@code content}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Arrays.hashCode(content);
    return h;
  }

  /**
   * Prints the immutable value {@code MovieVideoUpdateRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MovieVideoUpdateRequestDTO")
        .omitNullValues()
        .add("content", Arrays.toString(content))
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link MovieVideoUpdateRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable MovieVideoUpdateRequestDTO instance
   */
  public static ImmutableMovieVideoUpdateRequestDTO copyOf(MovieVideoUpdateRequestDTO instance) {
    if (instance instanceof ImmutableMovieVideoUpdateRequestDTO) {
      return (ImmutableMovieVideoUpdateRequestDTO) instance;
    }
    return ImmutableMovieVideoUpdateRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableMovieVideoUpdateRequestDTO ImmutableMovieVideoUpdateRequestDTO}.
   * <pre>
   * ImmutableMovieVideoUpdateRequestDTO.builder()
   *    .content(byte) // required {@link MovieVideoUpdateRequestDTO#content() content}
   *    .build();
   * </pre>
   * @return A new ImmutableMovieVideoUpdateRequestDTO builder
   */
  public static ImmutableMovieVideoUpdateRequestDTO.Builder builder() {
    return new ImmutableMovieVideoUpdateRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableMovieVideoUpdateRequestDTO ImmutableMovieVideoUpdateRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "MovieVideoUpdateRequestDTO", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_CONTENT = 0x1L;
    private long initBits = 0x1L;

    private @Nullable byte[] content;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MovieVideoUpdateRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MovieVideoUpdateRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      content(instance.content());
      return this;
    }

    /**
     * Initializes the value for the {@link MovieVideoUpdateRequestDTO#content() content} attribute.
     * @param content The elements for content
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("content")
    public final Builder content(byte... content) {
      this.content = content.clone();
      initBits &= ~INIT_BIT_CONTENT;
      return this;
    }

    /**
     * Builds a new {@link ImmutableMovieVideoUpdateRequestDTO ImmutableMovieVideoUpdateRequestDTO}.
     * @return An immutable instance of MovieVideoUpdateRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableMovieVideoUpdateRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableMovieVideoUpdateRequestDTO(content);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_CONTENT) != 0) attributes.add("content");
      return "Cannot build MovieVideoUpdateRequestDTO, some of required attributes are not set " + attributes;
    }
  }
}
