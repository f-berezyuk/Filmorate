package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import ru.yandex.practicum.filmorate.annotation.PositiveDuration;

public class DurationPositiveValidator implements ConstraintValidator<PositiveDuration, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null || value > 0;
    }
}
