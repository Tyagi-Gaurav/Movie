package com.gt.scr.spc.validator;

import org.apache.commons.lang3.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private Class enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        enumClass = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return EnumUtils.isValidEnum(enumClass, value);
    }
}
