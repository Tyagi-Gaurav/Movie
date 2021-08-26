package com.gt.scr.movie.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MaxLengthValidatorTest {
    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"wadhjashjdg", ""})
    void shouldThrowExceptionWhenMinLengthIsLessThanOrZero(String value) {
        MaxLengthValidatorTest.TestMaxLengthValidator underTest = new MaxLengthValidatorTest.TestMaxLengthValidator(value);
        Set<ConstraintViolation<MaxLengthValidatorTest.TestMaxLengthValidator>> violations =
                validator.validate(underTest);
        assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "ds" , "123", "abc12", "238ab"})
    void shouldValidateSuccessWhenValueIsAlphanumeric(String value) {
        MaxLengthValidatorTest.TestMaxLengthValidator underTest = new MaxLengthValidatorTest.TestMaxLengthValidator(value);
        Set<ConstraintViolation<MaxLengthValidatorTest.TestMaxLengthValidator>> violations =
                validator.validate(underTest);
        assertThat(violations).isEmpty();
    }

    private record TestMaxLengthValidator(@MaxLength(5) String field) {}
}