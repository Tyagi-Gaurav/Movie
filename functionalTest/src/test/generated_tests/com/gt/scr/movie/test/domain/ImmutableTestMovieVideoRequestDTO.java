package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link TestMovieVideoRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTestMovieVideoRequestDTO.builder()}.
 */
@Generated(from = "TestMovieVideoRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableTestMovieVideoRequestDTO
    implements TestMovieVideoRequestDTO {
  private final byte[] content;

  private ImmutableTestMovieVideoRequestDTO(byte[] content) {
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
   * Copy the current immutable object with elements that replace the content of {@link TestMovieVideoRequestDTO#content() content}.
   * The array is cloned before being saved as attribute values.
   * @param elements The non-null elements for content
   * @return A modified copy of {@code this} object
   */
  public final ImmutableTestMovieVideoRequestDTO withContent(byte... elements) {
    byte[] newValue = elements.clone();
    return new ImmutableTestMovieVideoRequestDTO(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTestMovieVideoRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTestMovieVideoRequestDTO
        && equalTo((ImmutableTestMovieVideoRequestDTO) another);
  }

  private boolean equalTo(ImmutableTestMovieVideoRequestDTO another) {
    return Arrays.equals(content, another.content);
  }

  /**
   * Computes a hash code from attributes: {@code content}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Arrays.hashCode(content);
    return h;
  }

  /**
   * Prints the immutable value {@code TestMovieVideoRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "TestMovieVideoRequestDTO{"
        + "content=" + Arrays.toString(content)
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link TestMovieVideoRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TestMovieVideoRequestDTO instance
   */
  public static ImmutableTestMovieVideoRequestDTO copyOf(TestMovieVideoRequestDTO instance) {
    if (instance instanceof ImmutableTestMovieVideoRequestDTO) {
      return (ImmutableTestMovieVideoRequestDTO) instance;
    }
    return ImmutableTestMovieVideoRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTestMovieVideoRequestDTO ImmutableTestMovieVideoRequestDTO}.
   * <pre>
   * ImmutableTestMovieVideoRequestDTO.builder()
   *    .content(byte) // required {@link TestMovieVideoRequestDTO#content() content}
   *    .build();
   * </pre>
   * @return A new ImmutableTestMovieVideoRequestDTO builder
   */
  public static ImmutableTestMovieVideoRequestDTO.Builder builder() {
    return new ImmutableTestMovieVideoRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTestMovieVideoRequestDTO ImmutableTestMovieVideoRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "TestMovieVideoRequestDTO", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_CONTENT = 0x1L;
    private long initBits = 0x1L;

    private byte[] content;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TestMovieVideoRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(TestMovieVideoRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      content(instance.content());
      return this;
    }

    /**
     * Initializes the value for the {@link TestMovieVideoRequestDTO#content() content} attribute.
     * @param content The elements for content
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("content")
    public final Builder content(byte... content) {
      this.content = content.clone();
      initBits &= ~INIT_BIT_CONTENT;
      return this;
    }

    /**
     * Builds a new {@link ImmutableTestMovieVideoRequestDTO ImmutableTestMovieVideoRequestDTO}.
     * @return An immutable instance of TestMovieVideoRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTestMovieVideoRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableTestMovieVideoRequestDTO(content);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_CONTENT) != 0) attributes.add("content");
      return "Cannot build TestMovieVideoRequestDTO, some of required attributes are not set " + attributes;
    }
  }
}
