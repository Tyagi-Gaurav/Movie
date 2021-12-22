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

class EnumValidatorTest {
    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"abc", "admin", "user", "aDmIn", "uSeR"})
    void shouldNotValidateAndThrowExceptionWhenNonEnumValuesProvided(String value) {
        TestValidEnumAnnotation underTest = new TestValidEnumAnnotation(value);
        Set<ConstraintViolation<TestValidEnumAnnotation>> violations =
                validator.validate(underTest);
        assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"ADMIN", "USER"})
    void shouldValidateSuccessWhenEnumValuesProvided(String value) {
        TestValidEnumAnnotation underTest = new TestValidEnumAnnotation(value);
        Set<ConstraintViolation<TestValidEnumAnnotation>> violations =
                validator.validate(underTest);
        assertThat(violations).isEmpty();
    }

    private record TestValidEnumAnnotation(@ValidEnum(TestRole.class) String field) { }

    private enum TestRole {
        ADMIN,
        USER
    }
}