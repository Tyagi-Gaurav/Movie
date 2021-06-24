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
 * Immutable implementation of {@link TimezonesDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTimezonesDTO.builder()}.
 */
@Generated(from = "TimezonesDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableTimezonesDTO implements TimezonesDTO {
  private final ImmutableList<TimezoneDTO> timezones;

  private ImmutableTimezonesDTO(ImmutableList<TimezoneDTO> timezones) {
    this.timezones = timezones;
  }

  /**
   * @return The value of the {@code timezones} attribute
   */
  @JsonProperty("timezones")
  @Override
  public ImmutableList<TimezoneDTO> timezones() {
    return timezones;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link TimezonesDTO#timezones() timezones}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableTimezonesDTO withTimezones(TimezoneDTO... elements) {
    ImmutableList<TimezoneDTO> newValue = ImmutableList.copyOf(elements);
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
    ImmutableList<TimezoneDTO> newValue = ImmutableList.copyOf(elements);
    return new ImmutableTimezonesDTO(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTimezonesDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
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
    @Var int h = 5381;
    h += (h << 5) + timezones.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code TimezonesDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("TimezonesDTO")
        .omitNullValues()
        .add("timezones", timezones)
        .toString();
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
   *    .addTimezones|addAllTimezones(com.gt.scr.movie.resource.domain.TimezoneDTO) // {@link TimezonesDTO#timezones() timezones} elements
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
  @NotThreadSafe
  public static final class Builder {
    private ImmutableList.Builder<TimezoneDTO> timezones = ImmutableList.builder();

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
    @CanIgnoreReturnValue 
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
    @CanIgnoreReturnValue 
    public final Builder addTimezones(TimezoneDTO element) {
      this.timezones.add(element);
      return this;
    }

    /**
     * Adds elements to {@link TimezonesDTO#timezones() timezones} list.
     * @param elements An array of timezones elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addTimezones(TimezoneDTO... elements) {
      this.timezones.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link TimezonesDTO#timezones() timezones} list.
     * @param elements An iterable of timezones elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("timezones")
    public final Builder timezones(Iterable<? extends TimezoneDTO> elements) {
      this.timezones = ImmutableList.builder();
      return addAllTimezones(elements);
    }

    /**
     * Adds elements to {@link TimezonesDTO#timezones() timezones} list.
     * @param elements An iterable of timezones elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllTimezones(Iterable<? extends TimezoneDTO> elements) {
      this.timezones.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link ImmutableTimezonesDTO ImmutableTimezonesDTO}.
     * @return An immutable instance of TimezonesDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTimezonesDTO build() {
      return new ImmutableTimezonesDTO(timezones.build());
    }
  }
}
