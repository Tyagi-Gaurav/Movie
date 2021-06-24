package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link TestTimezonesDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTestTimezonesDTO.builder()}.
 */
@Generated(from = "TestTimezonesDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableTestTimezonesDTO implements TestTimezonesDTO {
  private final List<TestTimezoneDTO> timezones;

  private ImmutableTestTimezonesDTO(List<TestTimezoneDTO> timezones) {
    this.timezones = timezones;
  }

  /**
   * @return The value of the {@code timezones} attribute
   */
  @JsonProperty("timezones")
  @Override
  public List<TestTimezoneDTO> timezones() {
    return timezones;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link TestTimezonesDTO#timezones() timezones}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableTestTimezonesDTO withTimezones(TestTimezoneDTO... elements) {
    List<TestTimezoneDTO> newValue = createUnmodifiableList(false, createSafeList(Arrays.asList(elements), true, false));
    return new ImmutableTestTimezonesDTO(newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link TestTimezonesDTO#timezones() timezones}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of timezones elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableTestTimezonesDTO withTimezones(Iterable<? extends TestTimezoneDTO> elements) {
    if (this.timezones == elements) return this;
    List<TestTimezoneDTO> newValue = createUnmodifiableList(false, createSafeList(elements, true, false));
    return new ImmutableTestTimezonesDTO(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTestTimezonesDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTestTimezonesDTO
        && equalTo((ImmutableTestTimezonesDTO) another);
  }

  private boolean equalTo(ImmutableTestTimezonesDTO another) {
    return timezones.equals(another.timezones);
  }

  /**
   * Computes a hash code from attributes: {@code timezones}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + timezones.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code TestTimezonesDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "TestTimezonesDTO{"
        + "timezones=" + timezones
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link TestTimezonesDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TestTimezonesDTO instance
   */
  public static ImmutableTestTimezonesDTO copyOf(TestTimezonesDTO instance) {
    if (instance instanceof ImmutableTestTimezonesDTO) {
      return (ImmutableTestTimezonesDTO) instance;
    }
    return ImmutableTestTimezonesDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTestTimezonesDTO ImmutableTestTimezonesDTO}.
   * <pre>
   * ImmutableTestTimezonesDTO.builder()
   *    .addTimezones|addAllTimezones(com.gt.scr.movie.test.domain.TestTimezoneDTO) // {@link TestTimezonesDTO#timezones() timezones} elements
   *    .build();
   * </pre>
   * @return A new ImmutableTestTimezonesDTO builder
   */
  public static ImmutableTestTimezonesDTO.Builder builder() {
    return new ImmutableTestTimezonesDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTestTimezonesDTO ImmutableTestTimezonesDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "TestTimezonesDTO", generator = "Immutables")
  public static final class Builder {
    private List<TestTimezoneDTO> timezones = new ArrayList<TestTimezoneDTO>();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TestTimezonesDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(TestTimezonesDTO instance) {
      Objects.requireNonNull(instance, "instance");
      addAllTimezones(instance.timezones());
      return this;
    }

    /**
     * Adds one element to {@link TestTimezonesDTO#timezones() timezones} list.
     * @param element A timezones element
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addTimezones(TestTimezoneDTO element) {
      this.timezones.add(Objects.requireNonNull(element, "timezones element"));
      return this;
    }

    /**
     * Adds elements to {@link TestTimezonesDTO#timezones() timezones} list.
     * @param elements An array of timezones elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addTimezones(TestTimezoneDTO... elements) {
      for (TestTimezoneDTO element : elements) {
        this.timezones.add(Objects.requireNonNull(element, "timezones element"));
      }
      return this;
    }


    /**
     * Sets or replaces all elements for {@link TestTimezonesDTO#timezones() timezones} list.
     * @param elements An iterable of timezones elements
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("timezones")
    public final Builder timezones(Iterable<? extends TestTimezoneDTO> elements) {
      this.timezones.clear();
      return addAllTimezones(elements);
    }

    /**
     * Adds elements to {@link TestTimezonesDTO#timezones() timezones} list.
     * @param elements An iterable of timezones elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addAllTimezones(Iterable<? extends TestTimezoneDTO> elements) {
      for (TestTimezoneDTO element : elements) {
        this.timezones.add(Objects.requireNonNull(element, "timezones element"));
      }
      return this;
    }

    /**
     * Builds a new {@link ImmutableTestTimezonesDTO ImmutableTestTimezonesDTO}.
     * @return An immutable instance of TestTimezonesDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTestTimezonesDTO build() {
      return new ImmutableTestTimezonesDTO(createUnmodifiableList(true, timezones));
    }
  }

  private static <T> List<T> createSafeList(Iterable<? extends T> iterable, boolean checkNulls, boolean skipNulls) {
    ArrayList<T> list;
    if (iterable instanceof Collection<?>) {
      int size = ((Collection<?>) iterable).size();
      if (size == 0) return Collections.emptyList();
      list = new ArrayList<>();
    } else {
      list = new ArrayList<>();
    }
    for (T element : iterable) {
      if (skipNulls && element == null) continue;
      if (checkNulls) Objects.requireNonNull(element, "element");
      list.add(element);
    }
    return list;
  }

  private static <T> List<T> createUnmodifiableList(boolean clone, List<T> list) {
    switch(list.size()) {
    case 0: return Collections.emptyList();
    case 1: return Collections.singletonList(list.get(0));
    default:
      if (clone) {
        return Collections.unmodifiableList(new ArrayList<>(list));
      } else {
        if (list instanceof ArrayList<?>) {
          ((ArrayList<?>) list).trimToSize();
        }
        return Collections.unmodifiableList(list);
      }
    }
  }
}
