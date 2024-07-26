package ru.yandex.practicum.filmorate.test.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

public class ModelsValidationTest {
    Validator validator;

    @BeforeEach
    public void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    public void validInputShouldCreateUser() {
        User user = new User("name", "login", "email@mail.com", LocalDate.parse("2000-01-01"));

        Set<ConstraintViolation<User>> violation = validator.validate(user);
        Assertions.assertTrue(violation.isEmpty());
    }

    @Test
    public void invalidInputShouldUserHasViolations() {
        User user = new User("name", "", "notAnEmail.com", LocalDate.parse("3001-01-01"));

        Set<ConstraintViolation<User>> violation = validator.validate(user);
        System.out.println(violation.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n")));
        Assertions.assertFalse(violation.isEmpty());
    }

    @Test
    public void validInputShouldCreateFilm() {
        Film film = new Film("FILM",
                "Desc",
                LocalDate.parse("1895-12-28"),
                127);

        Set<ConstraintViolation<Film>> violation = validator.validate(film);
        Assertions.assertTrue(violation.isEmpty(), String.join("\n", violation.toString()));
    }

    @Test
    public void invalidInputShouldFilmHasViolations() {
        String reallyBigDescription = new String(new char[201]);
        Film film = new Film("  ",
                reallyBigDescription,
                LocalDate.parse("1895-12-27"),
                -1);

        Set<ConstraintViolation<Film>> violation = validator.validate(film);
        System.out.println(violation.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n")));
        Assertions.assertFalse(violation.isEmpty());
        Assertions.assertEquals(4, violation.size());
    }
}
