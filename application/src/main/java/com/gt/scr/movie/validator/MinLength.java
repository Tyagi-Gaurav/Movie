package com.gt.scr.movie.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinLengthValidator.class)
public @interface MinLength {
    String message() default "{com.gt.scr.movie.constraints.MinLength.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int value();
}



