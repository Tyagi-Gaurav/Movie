package com.gt.scr.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxLengthValidator implements ConstraintValidator<MaxLength, String> {

    private int maxLength;

    @Override
    public void initialize(MaxLength constraintAnnotation) {
        maxLength = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isNotEmpty(value) && value.length() <= maxLength;
    }
}
