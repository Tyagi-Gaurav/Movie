package com.gt.scr.spc.validator;

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

class MinLengthValidatorTest {
    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "a", "ab"})
    void shouldThrowExceptionWhenMinLengthIsLessThanOrZero(String value) {
        TestMinLengthValidator underTest = new TestMinLengthValidator(value);
        Set<ConstraintViolation<TestMinLengthValidator>> violations =
                validator.validate(underTest);
        assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "123", "abc123", "238abc"})
    void shouldValidateSuccessWhenValueIsAlphanumeric(String value) {
        TestMinLengthValidator underTest = new TestMinLengthValidator(value);
        Set<ConstraintViolation<TestMinLengthValidator>> violations =
                validator.validate(underTest);
        assertThat(violations).isEmpty();
    }

    private record TestMinLengthValidator(@MinLength(3) String field) {}
}