package com.toptal.scr.tz.resource.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link UserProfile}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableUserProfile.builder()}.
 */
@Generated(from = "UserProfile", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableUserProfile implements UserProfile {
  private final String userName;
  private final String authority;

  private ImmutableUserProfile(String userName, String authority) {
    this.userName = userName;
    this.authority = authority;
  }

  /**
   * @return The value of the {@code userName} attribute
   */
  @Override
  public String userName() {
    return userName;
  }

  /**
   * @return The value of the {@code authority} attribute
   */
  @Override
  public String authority() {
    return authority;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link UserProfile#userName() userName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for userName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableUserProfile withUserName(String value) {
    String newValue = Objects.requireNonNull(value, "userName");
    if (this.userName.equals(newValue)) return this;
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
    return new ImmutableUserProfile(this.userName, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableUserProfile} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableUserProfile
        && equalTo((ImmutableUserProfile) another);
  }

  private boolean equalTo(ImmutableUserProfile another) {
    return userName.equals(another.userName)
        && authority.equals(another.authority);
  }

  /**
   * Computes a hash code from attributes: {@code userName}, {@code authority}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + userName.hashCode();
    h += (h << 5) + authority.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code UserProfile} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "UserProfile{"
        + "userName=" + userName
        + ", authority=" + authority
        + "}";
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
   *    .userName(String) // required {@link UserProfile#userName() userName}
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
  public static final class Builder {
    private static final long INIT_BIT_USER_NAME = 0x1L;
    private static final long INIT_BIT_AUTHORITY = 0x2L;
    private long initBits = 0x3L;

    private String userName;
    private String authority;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code UserProfile} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(UserProfile instance) {
      Objects.requireNonNull(instance, "instance");
      userName(instance.userName());
      authority(instance.authority());
      return this;
    }

    /**
     * Initializes the value for the {@link UserProfile#userName() userName} attribute.
     * @param userName The value for userName 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder userName(String userName) {
      this.userName = Objects.requireNonNull(userName, "userName");
      initBits &= ~INIT_BIT_USER_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link UserProfile#authority() authority} attribute.
     * @param authority The value for authority 
     * @return {@code this} builder for use in a chained invocation
     */
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
      return new ImmutableUserProfile(userName, authority);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_USER_NAME) != 0) attributes.add("userName");
      if ((initBits & INIT_BIT_AUTHORITY) != 0) attributes.add("authority");
      return "Cannot build UserProfile, some of required attributes are not set " + attributes;
    }
  }
}
