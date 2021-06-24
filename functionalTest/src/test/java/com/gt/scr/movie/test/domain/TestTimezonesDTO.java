package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableTestTimezonesDTO.Builder.class)
public interface TestTimezonesDTO {
    List<TestTimezoneDTO> timezones();
}
