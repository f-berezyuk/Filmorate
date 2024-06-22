package ru.yandex.practicum.filmorate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import ru.yandex.practicum.filmorate.validator.DateThresholdValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateThresholdValidator.class)
public @interface DateThreshold {
    String message() default "Date threshold validation failed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String maxDate() default "";

    String minDate() default "";
}
