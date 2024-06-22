package ru.yandex.practicum.filmorate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import ru.yandex.practicum.filmorate.validator.DurationPositiveValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DurationPositiveValidator.class)
public @interface PositiveDuration {
    String message() default "Продолжительность фильма должна быть не отрицательной.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
