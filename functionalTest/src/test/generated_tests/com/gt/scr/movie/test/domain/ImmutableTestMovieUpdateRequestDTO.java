package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link TestMovieUpdateRequestDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTestMovieUpdateRequestDTO.builder()}.
 */
@Generated(from = "TestMovieUpdateRequestDTO", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableTestMovieUpdateRequestDTO
    implements TestMovieUpdateRequestDTO {
  private final UUID id;
  private final String name;
  private final BigDecimal rating;
  private final int yearProduced;
  private final TestMovieVideoRequestDTO videoRequestDto;

  private ImmutableTestMovieUpdateRequestDTO(ImmutableTestMovieUpdateRequestDTO.Builder builder) {
    this.id = builder.id;
    if (builder.name != null) {
      initShim.name(builder.name);
    }
    if (builder.rating != null) {
      initShim.rating(builder.rating);
    }
    if (builder.yearProducedIsSet()) {
      initShim.yearProduced(builder.yearProduced);
    }
    if (builder.videoRequestDto != null) {
      initShim.videoRequestDto(builder.videoRequestDto);
    }
    this.name = initShim.name();
    this.rating = initShim.rating();
    this.yearProduced = initShim.yearProduced();
    this.videoRequestDto = initShim.videoRequestDto();
    this.initShim = null;
  }

  private ImmutableTestMovieUpdateRequestDTO(
      UUID id,
      String name,
      BigDecimal rating,
      int yearProduced,
      TestMovieVideoRequestDTO videoRequestDto) {
    this.id = id;
    this.name = name;
    this.rating = rating;
    this.yearProduced = yearProduced;
    this.videoRequestDto = videoRequestDto;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "TestMovieUpdateRequestDTO", generator = "Immutables")
  private final class InitShim {
    private byte nameBuildStage = STAGE_UNINITIALIZED;
    private String name;

    String name() {
      if (nameBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (nameBuildStage == STAGE_UNINITIALIZED) {
        nameBuildStage = STAGE_INITIALIZING;
        this.name = Objects.requireNonNull(nameInitialize(), "name");
        nameBuildStage = STAGE_INITIALIZED;
      }
      return this.name;
    }

    void name(String name) {
      this.name = name;
      nameBuildStage = STAGE_INITIALIZED;
    }

    private byte ratingBuildStage = STAGE_UNINITIALIZED;
    private BigDecimal rating;

    BigDecimal rating() {
      if (ratingBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (ratingBuildStage == STAGE_UNINITIALIZED) {
        ratingBuildStage = STAGE_INITIALIZING;
        this.rating = Objects.requireNonNull(ratingInitialize(), "rating");
        ratingBuildStage = STAGE_INITIALIZED;
      }
      return this.rating;
    }

    void rating(BigDecimal rating) {
      this.rating = rating;
      ratingBuildStage = STAGE_INITIALIZED;
    }

    private byte yearProducedBuildStage = STAGE_UNINITIALIZED;
    private int yearProduced;

    int yearProduced() {
      if (yearProducedBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (yearProducedBuildStage == STAGE_UNINITIALIZED) {
        yearProducedBuildStage = STAGE_INITIALIZING;
        this.yearProduced = yearProducedInitialize();
        yearProducedBuildStage = STAGE_INITIALIZED;
      }
      return this.yearProduced;
    }

    void yearProduced(int yearProduced) {
      this.yearProduced = yearProduced;
      yearProducedBuildStage = STAGE_INITIALIZED;
    }

    private byte videoRequestDtoBuildStage = STAGE_UNINITIALIZED;
    private TestMovieVideoRequestDTO videoRequestDto;

    TestMovieVideoRequestDTO videoRequestDto() {
      if (videoRequestDtoBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (videoRequestDtoBuildStage == STAGE_UNINITIALIZED) {
        videoRequestDtoBuildStage = STAGE_INITIALIZING;
        this.videoRequestDto = Objects.requireNonNull(videoRequestDtoInitialize(), "videoRequestDto");
        videoRequestDtoBuildStage = STAGE_INITIALIZED;
      }
      return this.videoRequestDto;
    }

    void videoRequestDto(TestMovieVideoRequestDTO videoRequestDto) {
      this.videoRequestDto = videoRequestDto;
      videoRequestDtoBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (nameBuildStage == STAGE_INITIALIZING) attributes.add("name");
      if (ratingBuildStage == STAGE_INITIALIZING) attributes.add("rating");
      if (yearProducedBuildStage == STAGE_INITIALIZING) attributes.add("yearProduced");
      if (videoRequestDtoBuildStage == STAGE_INITIALIZING) attributes.add("videoRequestDto");
      return "Cannot build TestMovieUpdateRequestDTO, attribute initializers form cycle " + attributes;
    }
  }

  private String nameInitialize() {
    return TestMovieUpdateRequestDTO.super.name();
  }

  private BigDecimal ratingInitialize() {
    return TestMovieUpdateRequestDTO.super.rating();
  }

  private int yearProducedInitialize() {
    return TestMovieUpdateRequestDTO.super.yearProduced();
  }

  private TestMovieVideoRequestDTO videoRequestDtoInitialize() {
    return TestMovieUpdateRequestDTO.super.videoRequestDto();
  }

  /**
   * @return The value of the {@code id} attribute
   */
  @JsonProperty("id")
  @Override
  public UUID id() {
    return id;
  }

  /**
   * @return The value of the {@code name} attribute
   */
  @JsonProperty("name")
  @Override
  public String name() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.name()
        : this.name;
  }

  /**
   * @return The value of the {@code rating} attribute
   */
  @JsonProperty("rating")
  @Override
  public BigDecimal rating() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.rating()
        : this.rating;
  }

  /**
   * @return The value of the {@code yearProduced} attribute
   */
  @JsonProperty("yearProduced")
  @Override
  public int yearProduced() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.yearProduced()
        : this.yearProduced;
  }

  /**
   * @return The value of the {@code videoRequestDto} attribute
   */
  @JsonProperty("videoRequestDto")
  @Override
  public TestMovieVideoRequestDTO videoRequestDto() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.videoRequestDto()
        : this.videoRequestDto;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestMovieUpdateRequestDTO#id() id} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestMovieUpdateRequestDTO withId(UUID value) {
    if (this.id == value) return this;
    UUID newValue = Objects.requireNonNull(value, "id");
    return new ImmutableTestMovieUpdateRequestDTO(newValue, this.name, this.rating, this.yearProduced, this.videoRequestDto);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestMovieUpdateRequestDTO#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestMovieUpdateRequestDTO withName(String value) {
    String newValue = Objects.requireNonNull(value, "name");
    if (this.name.equals(newValue)) return this;
    return new ImmutableTestMovieUpdateRequestDTO(this.id, newValue, this.rating, this.yearProduced, this.videoRequestDto);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestMovieUpdateRequestDTO#rating() rating} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for rating
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestMovieUpdateRequestDTO withRating(BigDecimal value) {
    BigDecimal newValue = Objects.requireNonNull(value, "rating");
    if (this.rating.equals(newValue)) return this;
    return new ImmutableTestMovieUpdateRequestDTO(this.id, this.name, newValue, this.yearProduced, this.videoRequestDto);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestMovieUpdateRequestDTO#yearProduced() yearProduced} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for yearProduced
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestMovieUpdateRequestDTO withYearProduced(int value) {
    if (this.yearProduced == value) return this;
    return new ImmutableTestMovieUpdateRequestDTO(this.id, this.name, this.rating, value, this.videoRequestDto);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link TestMovieUpdateRequestDTO#videoRequestDto() videoRequestDto} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for videoRequestDto
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTestMovieUpdateRequestDTO withVideoRequestDto(TestMovieVideoRequestDTO value) {
    if (this.videoRequestDto == value) return this;
    TestMovieVideoRequestDTO newValue = Objects.requireNonNull(value, "videoRequestDto");
    return new ImmutableTestMovieUpdateRequestDTO(this.id, this.name, this.rating, this.yearProduced, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTestMovieUpdateRequestDTO} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTestMovieUpdateRequestDTO
        && equalTo((ImmutableTestMovieUpdateRequestDTO) another);
  }

  private boolean equalTo(ImmutableTestMovieUpdateRequestDTO another) {
    return id.equals(another.id)
        && name.equals(another.name)
        && rating.equals(another.rating)
        && yearProduced == another.yearProduced
        && videoRequestDto.equals(another.videoRequestDto);
  }

  /**
   * Computes a hash code from attributes: {@code id}, {@code name}, {@code rating}, {@code yearProduced}, {@code videoRequestDto}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + id.hashCode();
    h += (h << 5) + name.hashCode();
    h += (h << 5) + rating.hashCode();
    h += (h << 5) + yearProduced;
    h += (h << 5) + videoRequestDto.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code TestMovieUpdateRequestDTO} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "TestMovieUpdateRequestDTO{"
        + "id=" + id
        + ", name=" + name
        + ", rating=" + rating
        + ", yearProduced=" + yearProduced
        + ", videoRequestDto=" + videoRequestDto
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link TestMovieUpdateRequestDTO} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TestMovieUpdateRequestDTO instance
   */
  public static ImmutableTestMovieUpdateRequestDTO copyOf(TestMovieUpdateRequestDTO instance) {
    if (instance instanceof ImmutableTestMovieUpdateRequestDTO) {
      return (ImmutableTestMovieUpdateRequestDTO) instance;
    }
    return ImmutableTestMovieUpdateRequestDTO.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTestMovieUpdateRequestDTO ImmutableTestMovieUpdateRequestDTO}.
   * <pre>
   * ImmutableTestMovieUpdateRequestDTO.builder()
   *    .id(UUID) // required {@link TestMovieUpdateRequestDTO#id() id}
   *    .name(String) // optional {@link TestMovieUpdateRequestDTO#name() name}
   *    .rating(java.math.BigDecimal) // optional {@link TestMovieUpdateRequestDTO#rating() rating}
   *    .yearProduced(int) // optional {@link TestMovieUpdateRequestDTO#yearProduced() yearProduced}
   *    .videoRequestDto(com.gt.scr.movie.test.domain.TestMovieVideoRequestDTO) // optional {@link TestMovieUpdateRequestDTO#videoRequestDto() videoRequestDto}
   *    .build();
   * </pre>
   * @return A new ImmutableTestMovieUpdateRequestDTO builder
   */
  public static ImmutableTestMovieUpdateRequestDTO.Builder builder() {
    return new ImmutableTestMovieUpdateRequestDTO.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTestMovieUpdateRequestDTO ImmutableTestMovieUpdateRequestDTO}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "TestMovieUpdateRequestDTO", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private static final long OPT_BIT_YEAR_PRODUCED = 0x1L;
    private long initBits = 0x1L;
    private long optBits;

    private UUID id;
    private String name;
    private BigDecimal rating;
    private int yearProduced;
    private TestMovieVideoRequestDTO videoRequestDto;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code TestMovieUpdateRequestDTO} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(TestMovieUpdateRequestDTO instance) {
      Objects.requireNonNull(instance, "instance");
      id(instance.id());
      name(instance.name());
      rating(instance.rating());
      yearProduced(instance.yearProduced());
      videoRequestDto(instance.videoRequestDto());
      return this;
    }

    /**
     * Initializes the value for the {@link TestMovieUpdateRequestDTO#id() id} attribute.
     * @param id The value for id 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("id")
    public final Builder id(UUID id) {
      this.id = Objects.requireNonNull(id, "id");
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Initializes the value for the {@link TestMovieUpdateRequestDTO#name() name} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link TestMovieUpdateRequestDTO#name() name}.</em>
     * @param name The value for name 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("name")
    public final Builder name(String name) {
      this.name = Objects.requireNonNull(name, "name");
      return this;
    }

    /**
     * Initializes the value for the {@link TestMovieUpdateRequestDTO#rating() rating} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link TestMovieUpdateRequestDTO#rating() rating}.</em>
     * @param rating The value for rating 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("rating")
    public final Builder rating(BigDecimal rating) {
      this.rating = Objects.requireNonNull(rating, "rating");
      return this;
    }

    /**
     * Initializes the value for the {@link TestMovieUpdateRequestDTO#yearProduced() yearProduced} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link TestMovieUpdateRequestDTO#yearProduced() yearProduced}.</em>
     * @param yearProduced The value for yearProduced 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("yearProduced")
    public final Builder yearProduced(int yearProduced) {
      this.yearProduced = yearProduced;
      optBits |= OPT_BIT_YEAR_PRODUCED;
      return this;
    }

    /**
     * Initializes the value for the {@link TestMovieUpdateRequestDTO#videoRequestDto() videoRequestDto} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link TestMovieUpdateRequestDTO#videoRequestDto() videoRequestDto}.</em>
     * @param videoRequestDto The value for videoRequestDto 
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("videoRequestDto")
    public final Builder videoRequestDto(TestMovieVideoRequestDTO videoRequestDto) {
      this.videoRequestDto = Objects.requireNonNull(videoRequestDto, "videoRequestDto");
      return this;
    }

    /**
     * Builds a new {@link ImmutableTestMovieUpdateRequestDTO ImmutableTestMovieUpdateRequestDTO}.
     * @return An immutable instance of TestMovieUpdateRequestDTO
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTestMovieUpdateRequestDTO build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableTestMovieUpdateRequestDTO(this);
    }

    private boolean yearProducedIsSet() {
      return (optBits & OPT_BIT_YEAR_PRODUCED) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      return "Cannot build TestMovieUpdateRequestDTO, some of required attributes are not set " + attributes;
    }
  }
}
