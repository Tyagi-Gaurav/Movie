package com.gt.scr.movie.resource.domain;

import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link UserProfile}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableUserProfile.builder()}.
 */
@Generated(from = "UserProfile", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableUserProfile implements UserProfile {
  private final UUID id;
  private final String authority;

  private ImmutableUserProfile(UUID id, String authority) {
    this.id = id;
    this.authority = authority;
  }

  /**
   * @return The value of the {@code id} attribute
   */
  @Override
  public UUID id() {
    return id;
  }

  /**
   * @return The value of the {@code authority} attribute
   */
  @Override
  public String authority() {
    return authority;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link UserProfile#id() id} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUserProfile withId(UUID value) {
    if (this.id == value) return this;
    UUID newValue = Objects.requireNonNull(value, "id");
    return new ImmutableUserProfile(newValue, this.authority);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link UserProfile#authority() authority} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for authority
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUserProfile withAuthority(String value) {
    String newValue = Objects.requireNonNull(value, "authority");
    if (this.authority.equals(newValue)) return this;
    return new ImmutableUserProfile(this.id, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableUserProfile} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableUserProfile
        && equalTo((ImmutableUserProfile) another);
  }

  private boolean equalTo(ImmutableUserProfile another) {
    return id.equals(another.id)
        && authority.equals(another.authority);
  }

  /**
   * Computes a hash code from attributes: {@code id}, {@code authority}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + id.hashCode();
    h += (h << 5) + authority.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code UserProfile} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("UserProfile")
        .omitNullValues()
        .add("id", id)
        .add("authority", authority)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link UserProfile} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable UserProfile instance
   */
  public static ImmutableUserProfile copyOf(UserProfile instance) {
    if (instance instanceof ImmutableUserProfile) {
      return (ImmutableUserProfile) instance;
    }
    return ImmutableUserProfile.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableUserProfile ImmutableUserProfile}.
   * <pre>
   * ImmutableUserProfile.builder()
   *    .id(UUID) // required {@link UserProfile#id() id}
   *    .authority(String) // required {@link UserProfile#authority() authority}
   *    .build();
   * </pre>
   * @return A new ImmutableUserProfile builder
   */
  public static ImmutableUserProfile.Builder builder() {
    return new ImmutableUserProfile.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableUserProfile ImmutableUserProfile}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "UserProfile", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private static final long INIT_BIT_AUTHORITY = 0x2L;
    private long initBits = 0x3L;

    private @Nullable UUID id;
    private @Nullable String authority;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code UserProfile} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(UserProfile instance) {
      Objects.requireNonNull(instance, "instance");
      id(instance.id());
      authority(instance.authority());
      return this;
    }

    /**
     * Initializes the value for the {@link UserProfile#id() id} attribute.
     * @param id The value for id 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder id(UUID id) {
      this.id = Objects.requireNonNull(id, "id");
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Initializes the value for the {@link UserProfile#authority() authority} attribute.
     * @param authority The value for authority 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder authority(String authority) {
      this.authority = Objects.requireNonNull(authority, "authority");
      initBits &= ~INIT_BIT_AUTHORITY;
      return this;
    }

    /**
     * Builds a new {@link ImmutableUserProfile ImmutableUserProfile}.
     * @return An immutable instance of UserProfile
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableUserProfile build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableUserProfile(id, authority);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      if ((initBits & INIT_BIT_AUTHORITY) != 0) attributes.add("authority");
      return "Cannot build UserProfile, some of required attributes are not set " + attributes;
    }
  }
}
