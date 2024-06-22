package ru.yandex.practicum.filmorate.validator;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import ru.yandex.practicum.filmorate.annotation.DateThreshold;

public class DateThresholdValidator implements ConstraintValidator<DateThreshold, LocalDate> {
    private LocalDate minDate;
    private LocalDate maxDate;

    @Override
    public void initialize(DateThreshold constraint) {
        if (!constraint.minDate().isBlank()) {
            this.minDate = LocalDate.parse(constraint.minDate());
        }
        if (!constraint.maxDate().isBlank()) {
            this.maxDate = LocalDate.parse(constraint.maxDate());
        }
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value == null
                || (minDate == null || value.isEqual(minDate) || value.isAfter(minDate))
                && (maxDate == null || value.isEqual(maxDate) || value.isBefore(maxDate));
    }
}
