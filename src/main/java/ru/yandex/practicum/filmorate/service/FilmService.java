package ru.yandex.practicum.filmorate.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {
    private final Storage<Film, Integer> storage;
    private final LikeService likeService;

    public Film add(Film film) {
        storage.add(film);
        log.info("Add film [" + film.getName() + "] with key [" + film.getId() + "]");
        return film;
    }

    public Film update(Film film) {
        Integer id = film.getId();
        log.info("Update film with key: [" + id + "] with value [" + film + "].");
        return storage.update(film, id);
    }

    public Collection<Film> getAll() {
        log.info("Get all films");
        return storage.getAll();
    }

    public Film getById(Integer id) {
        return storage.get(id);
    }

    public Collection<Film> getFilms(int size, int from, String sort) {
        Comparator<LocalDate> comparator = (o1, o2) -> sort.equals("asc") ? o1.compareTo(o2) : o2.compareTo(o1);
        return storage.getAll().stream().sorted(Comparator.comparing(Film::getReleased, comparator)).skip(from).limit(size).toList();

    }

    public Long like(Integer filmId, Integer userId) throws IllegalArgumentException {
        getOrThrow(filmId);
        likeService.add(userId, filmId);

        return likeService.getCount(filmId);
    }

    private Film getOrThrow(Integer filmId) {
        Film film = storage.get(filmId);
        if (film == null) {
            throw new IllegalArgumentException("Film with id " + filmId + "not found.");
        }

        return film;
    }

    public Collection<Film> getTop(Integer count) {
        return likeService.getTop(count).stream().map(this::getById).toList();
    }

    public Long deleteLike(Integer filmId, Integer userId) {
        return likeService.delete(userId, filmId);
    }
}
