package ru.yandex.practicum.filmorate.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.utilities.IdGenerator;

@Slf4j
@Service
public class FilmService {
    private final Storage<Film, Long> storage;
    private final IdGenerator generator;
    private final Map<Long, Set<Long>> likes;

    public FilmService(Storage<Film, Long> repository, IdGenerator generator) {
        log.info("Init film service.");
        this.storage = repository;
        this.generator = generator;
        likes = new HashMap<>();
    }

    public Film add(Film film) {
        Long id = generator.generate();
        film.setId(id);
        storage.add(film, id);
        log.info("Add film [" + film.getName() + "] with key [" + id + "]");
        return film;
    }

    public Film update(Long id, Film film) {
        log.info("Update film with key: [" + id + "] with value [" + film + "]. Id not included.");
        return storage.update(film, id);
    }

    public Collection<Film> getAll() {
        log.info("Get all films");
        return storage.getAll();
    }

    public Film getById(Long id) {
        return storage.get(id);
    }

    public Collection<Film> getFilms(int size, int from, String sort) {
        Comparator<LocalDate> comparator = (o1, o2) -> sort.equals("asc") ? o1.compareTo(o2) : o2.compareTo(o1);
        return storage.getAll().stream().sorted(Comparator.comparing(Film::getReleaseDate, comparator)).skip(from).limit(size).toList();

    }

    public Integer like(Long filmId, Long userId, Boolean add) throws IllegalArgumentException {
        if (!likes.containsKey(filmId)) {
            getOrThrow(filmId);
            likes.put(filmId, new HashSet<>());
        }

        Set<Long> filmLikes = likes.get(filmId);
        if (add) {
            filmLikes.add(userId);
        } else {
            filmLikes.remove(userId);
        }

        return filmLikes.size();
    }

    private Film getOrThrow(Long filmId) {
        Film film = storage.get(filmId);
        if (film == null) {
            throw new IllegalArgumentException("Film with id " + filmId + "not found.");
        }

        return film;
    }

    public Collection<Film> getTop(Integer count) {
        return likes.keySet().stream()
                .sorted((k1, k2) -> Integer.compare(likes.get(k2).size(), likes.get(k1).size()))
                .limit(count)
                .map(this::getOrThrow)
                .collect(Collectors.toList());
    }
}
