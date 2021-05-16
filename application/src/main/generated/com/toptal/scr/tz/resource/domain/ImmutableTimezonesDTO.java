package com.toptal.scr.tz.resource.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link TimezonesDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTimezonesDTO.builder()}.
 */
@Generated(from = "TimezonesDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableTimezonesDTO implements TimezonesDTO {
  private final List<TimezoneDTO> timezones;

  private ImmutableTimezonesDTO(List<TimezoneDTO> timezones) {
    this.timezones = timezones;
  }

  /**
   * @return The value of the {@code timezones} attribute
   */
  @JsonProperty("timezones")
  @Override
  public List<TimezoneDTO> timezones() {
    return timezones;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link TimezonesDTO#timezones() timezones}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableTimezonesDTO withTimezones(TimezoneDTO... elements) {
    List<TimezoneDTO> newValue = createUnmodifiableList(false, createSafeList(Arrays.asList(elements), true, false));
    return new ImmutableTimezonesDTO(newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link TimezonesDTO#timezones() timezones}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of timezones elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableTimezonesDTO withTimezones(Iterable<? extends TimezoneDTO> elements) {
    if (this.timezones == elements) return this;
    List<TimezoneDTO> newValue = createUnmodifiableList(false, createSafeList(elements, true, false));
    return new ImmutableTimezonesDTO(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTimezonesDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTimezonesDTO
        && equalTo((ImmutableTimezonesDTO) another);
  }

  private boolean equalTo(ImmutableTimezonesDTO another) {
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
   * Prints the immutable value {@code TimezonesDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "TimezonesDTO{"
        + "timezones=" + timezones
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link TimezonesDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TimezonesDTO instance
   */
  public static ImmutableTimezonesDTO copyOf(TimezonesDTO instance) {
    if (instance instanceof ImmutableTimezonesDTO) {
      return (ImmutableTimezonesDTO) instance;
    }
    return ImmutableTimezonesDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTimezonesDTO ImmutableTimezonesDTO}.
   * <pre>
   * ImmutableTimezonesDTO.builder()
   *    .addTimezones|addAllTimezones(com.toptal.scr.tz.resource.domain.TimezoneDTO) // {@link TimezonesDTO#timezones() timezones} elements
   *    .build();
   * </pre>
   * @return A new ImmutableTimezonesDTO builder
   */
  public static ImmutableTimezonesDTO.Builder builder() {
    return new ImmutableTimezonesDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTimezonesDTO ImmutableTimezonesDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "TimezonesDTO", generator = "Immutables")
  public static final class Builder {
    private List<TimezoneDTO> timezones = new ArrayList<TimezoneDTO>();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TimezonesDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(TimezonesDTO instance) {
      Objects.requireNonNull(instance, "instance");
      addAllTimezones(instance.timezones());
      return this;
    }

    /**
     * Adds one element to {@link TimezonesDTO#timezones() timezones} list.
     * @param element A timezones element
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addTimezones(TimezoneDTO element) {
      this.timezones.add(Objects.requireNonNull(element, "timezones element"));
      return this;
    }

    /**
     * Adds elements to {@link TimezonesDTO#timezones() timezones} list.
     * @param elements An array of timezones elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addTimezones(TimezoneDTO... elements) {
      for (TimezoneDTO element : elements) {
        this.timezones.add(Objects.requireNonNull(element, "timezones element"));
      }
      return this;
    }


    /**
     * Sets or replaces all elements for {@link TimezonesDTO#timezones() timezones} list.
     * @param elements An iterable of timezones elements
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("timezones")
    public final Builder timezones(Iterable<? extends TimezoneDTO> elements) {
      this.timezones.clear();
      return addAllTimezones(elements);
    }

    /**
     * Adds elements to {@link TimezonesDTO#timezones() timezones} list.
     * @param elements An iterable of timezones elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addAllTimezones(Iterable<? extends TimezoneDTO> elements) {
      for (TimezoneDTO element : elements) {
        this.timezones.add(Objects.requireNonNull(element, "timezones element"));
      }
      return this;
    }

    /**
     * Builds a new {@link ImmutableTimezonesDTO ImmutableTimezonesDTO}.
     * @return An immutable instance of TimezonesDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTimezonesDTO build() {
      return new ImmutableTimezonesDTO(createUnmodifiableList(true, timezones));
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
