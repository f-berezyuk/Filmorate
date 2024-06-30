package ru.yandex.practicum.filmorate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import ru.yandex.practicum.filmorate.validator.LoginValidator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = LoginValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface Login {

    String message() default "Логин не может быть пустым и содержать пробелы.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
