package ru.yandex.practicum.filmorate.test.service;

import java.time.Duration;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.utilities.IdGenerator;

public class ServiceTest {
    @Test
    public void shouldAddUser() {
        UserService userService = new UserService(new InMemoryUserStorage(), new IdGenerator());
        User user = new User("email", "login", "name", LocalDate.now().minusYears(19));
        User newUser = userService.add(user);

        Assertions.assertEquals(user, newUser);
        Assertions.assertEquals(user, userService.getById(user.getId()));
    }

    @Test
    public void shouldAddFilm() {
        FilmService filmService = new FilmService(new InMemoryFilmStorage(), new IdGenerator());
        Film film = new Film("name", "description", LocalDate.now().minusYears(3), Duration.ofMinutes(120));
        Film newFilm = filmService.add(film);

        Assertions.assertEquals(film, newFilm);
        Assertions.assertEquals(film, filmService.getById(film.getId()));
    }
}
