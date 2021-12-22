package com.gt.scr.spc.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinLengthValidator implements ConstraintValidator<MinLength, String> {

    private int minLength;

    @Override
    public void initialize(MinLength constraintAnnotation) {
        minLength = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isNotEmpty(value) && value.length() >= minLength;
    }
}
