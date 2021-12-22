package com.gt.scr.spc.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AlphanumericValidator implements ConstraintValidator<AlphaNumeric, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isAlphanumeric(value);
    }
}
