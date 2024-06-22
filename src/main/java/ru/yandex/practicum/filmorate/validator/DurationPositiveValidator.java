package ru.yandex.practicum.filmorate.validator;

import java.time.Duration;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import ru.yandex.practicum.filmorate.annotation.PositiveDuration;

public class DurationPositiveValidator implements ConstraintValidator<PositiveDuration, Duration> {
    @Override
    public boolean isValid(Duration value, ConstraintValidatorContext context) {
        return value == null || value.toMinutes() > 0;
    }
}
