package ru.yandex.practicum.filmorate.test.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.FriendshipService;
import ru.yandex.practicum.filmorate.service.LikeService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.inmemory.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.inmemory.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.utilities.IntegerIdGenerator;

public class ServiceTest {
    @MockBean
    private FriendshipService friendshipService;
    @MockBean
    private LikeService likeService;

    @Test
    public void shouldAddUser() {
        UserService userService = new UserService(new InMemoryUserStorage(), friendshipService);
        User user = new User("email", "login", "name", LocalDate.now().minusYears(19));
        User newUser = userService.add(user);

        Assertions.assertEquals(user, newUser);
        Assertions.assertEquals(user, userService.getById(user.getId()));
    }

    @Test
    public void shouldAddFilm() {
        FilmService filmService = new FilmService(new InMemoryFilmStorage(new IntegerIdGenerator()), likeService);
        Film film = new Film("name", "description", LocalDate.now().minusYears(3),
                120);
        Film newFilm = filmService.add(film);

        Assertions.assertEquals(film, newFilm);
        Assertions.assertEquals(film, filmService.getById(film.getId()));
    }

    @Test
    public void shouldWorkWithLikes() {
        FilmService filmService = new FilmService(new InMemoryFilmStorage(new IntegerIdGenerator()),
                likeService);
        Film film = new Film("name", "description", LocalDate.now().minusYears(3), 120);
        filmService.add(film);
        Assertions.assertEquals(1, filmService.like(film.getId(), 1));
        Assertions.assertEquals(2, filmService.like(film.getId(), 2));
        Assertions.assertEquals(3, filmService.like(film.getId(), 3));
        Assertions.assertEquals(2, filmService.deleteLike(film.getId(), 3));

        Assertions.assertEquals(film, filmService.getTop(10).stream().findFirst().orElseThrow());
    }

}
