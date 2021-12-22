package com.gt.scr.spc.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AlphanumericValidator.class)
public @interface AlphaNumeric {
    String message() default "{com.gt.scr.movie.constraints.AlphaNumeric.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
