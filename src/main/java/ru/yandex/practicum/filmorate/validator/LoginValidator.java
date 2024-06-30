package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import ru.yandex.practicum.filmorate.annotation.Login;

public class LoginValidator implements ConstraintValidator<Login, String> {
    @Override
    public void initialize(Login constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !(value.isBlank() || value.contains(" "));
    }
}
