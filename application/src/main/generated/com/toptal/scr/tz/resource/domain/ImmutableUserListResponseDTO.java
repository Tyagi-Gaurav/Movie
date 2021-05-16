package com.toptal.scr.tz.resource.domain;

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
 * Immutable implementation of {@link UserListResponseDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableUserListResponseDTO.builder()}.
 */
@Generated(from = "UserListResponseDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableUserListResponseDTO
    implements UserListResponseDTO {
  private final ImmutableList<UserDetailsResponse> userDetails;

  private ImmutableUserListResponseDTO(
      ImmutableList<UserDetailsResponse> userDetails) {
    this.userDetails = userDetails;
  }

  /**
   * @return The value of the {@code userDetails} attribute
   */
  @JsonProperty("userDetails")
  @Override
  public ImmutableList<UserDetailsResponse> userDetails() {
    return userDetails;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link UserListResponseDTO#userDetails() userDetails}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableUserListResponseDTO withUserDetails(UserDetailsResponse... elements) {
    ImmutableList<UserDetailsResponse> newValue = ImmutableList.copyOf(elements);
    return new ImmutableUserListResponseDTO(newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link UserListResponseDTO#userDetails() userDetails}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of userDetails elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableUserListResponseDTO withUserDetails(Iterable<? extends UserDetailsResponse> elements) {
    if (this.userDetails == elements) return this;
    ImmutableList<UserDetailsResponse> newValue = ImmutableList.copyOf(elements);
    return new ImmutableUserListResponseDTO(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableUserListResponseDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableUserListResponseDTO
        && equalTo((ImmutableUserListResponseDTO) another);
  }

  private boolean equalTo(ImmutableUserListResponseDTO another) {
    return userDetails.equals(another.userDetails);
  }

  /**
   * Computes a hash code from attributes: {@code userDetails}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + userDetails.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code UserListResponseDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("UserListResponseDTO")
        .omitNullValues()
        .add("userDetails", userDetails)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link UserListResponseDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable UserListResponseDTO instance
   */
  public static ImmutableUserListResponseDTO copyOf(UserListResponseDTO instance) {
    if (instance instanceof ImmutableUserListResponseDTO) {
      return (ImmutableUserListResponseDTO) instance;
    }
    return ImmutableUserListResponseDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableUserListResponseDTO ImmutableUserListResponseDTO}.
   * <pre>
   * ImmutableUserListResponseDTO.builder()
   *    .addUserDetails|addAllUserDetails(com.toptal.scr.tz.resource.domain.UserDetailsResponse) // {@link UserListResponseDTO#userDetails() userDetails} elements
   *    .build();
   * </pre>
   * @return A new ImmutableUserListResponseDTO builder
   */
  public static ImmutableUserListResponseDTO.Builder builder() {
    return new ImmutableUserListResponseDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableUserListResponseDTO ImmutableUserListResponseDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "UserListResponseDTO", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private ImmutableList.Builder<UserDetailsResponse> userDetails = ImmutableList.builder();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code UserListResponseDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(UserListResponseDTO instance) {
      Objects.requireNonNull(instance, "instance");
      addAllUserDetails(instance.userDetails());
      return this;
    }

    /**
     * Adds one element to {@link UserListResponseDTO#userDetails() userDetails} list.
     * @param element A userDetails element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addUserDetails(UserDetailsResponse element) {
      this.userDetails.add(element);
      return this;
    }

    /**
     * Adds elements to {@link UserListResponseDTO#userDetails() userDetails} list.
     * @param elements An array of userDetails elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addUserDetails(UserDetailsResponse... elements) {
      this.userDetails.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link UserListResponseDTO#userDetails() userDetails} list.
     * @param elements An iterable of userDetails elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("userDetails")
    public final Builder userDetails(Iterable<? extends UserDetailsResponse> elements) {
      this.userDetails = ImmutableList.builder();
      return addAllUserDetails(elements);
    }

    /**
     * Adds elements to {@link UserListResponseDTO#userDetails() userDetails} list.
     * @param elements An iterable of userDetails elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllUserDetails(Iterable<? extends UserDetailsResponse> elements) {
      this.userDetails.addAll(elements);
      return this;
    }

    /**
     * Builds a new {@link ImmutableUserListResponseDTO ImmutableUserListResponseDTO}.
     * @return An immutable instance of UserListResponseDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableUserListResponseDTO build() {
      return new ImmutableUserListResponseDTO(userDetails.build());
    }
  }
}
