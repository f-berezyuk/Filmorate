package ru.yandex.practicum.filmorate.test.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.FriendshipService;
import ru.yandex.practicum.filmorate.service.LikeService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.db.GenreFilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.db.MpaFilmsDbStorage;
import ru.yandex.practicum.filmorate.storage.inmemory.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.inmemory.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.utilities.IntegerIdGenerator;

public class ServiceTest {
    @MockBean
    private FriendshipService friendshipService;
    @MockBean
    private LikeService likeService;
    @MockBean
    private MpaFilmsDbStorage mpaFilmsStorage;
    @MockBean
    private MpaDbStorage mpaStorage;
    @MockBean
    private GenreFilmDbStorage genreFilmStorage;
    @MockBean
    private GenreDbStorage genreStorage;

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
        FilmService filmService = new FilmService(new InMemoryFilmStorage(new IntegerIdGenerator()), mpaFilmsStorage,
                mpaStorage, genreFilmStorage, genreStorage, likeService);
        FilmDto film = new FilmDto("name", "description", LocalDate.now().minusYears(3),
                120);
        FilmDto newFilm = filmService.add(film);

        Assertions.assertEquals(film, newFilm);
        Assertions.assertEquals(film, filmService.getById(film.getId()));
    }

    @Test
    public void shouldWorkWithLikes() {
        FilmService filmService = new FilmService(new InMemoryFilmStorage(new IntegerIdGenerator()),
                mpaFilmsStorage, mpaStorage, genreFilmStorage, genreStorage, likeService);
        FilmDto film = new FilmDto("name", "description", LocalDate.now().minusYears(3), 120);
        filmService.add(film);
        Assertions.assertEquals(1, filmService.like(film.getId(), 1L));
        Assertions.assertEquals(2, filmService.like(film.getId(), 2L));
        Assertions.assertEquals(3, filmService.like(film.getId(), 3L));
        Assertions.assertEquals(2, filmService.deleteLike(film.getId(), 3L));

        Assertions.assertEquals(film, filmService.getTop(10).stream().findFirst().orElseThrow());
    }
}
