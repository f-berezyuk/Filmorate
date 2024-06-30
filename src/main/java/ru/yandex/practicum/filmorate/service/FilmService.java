package ru.yandex.practicum.filmorate.service;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.Repository;

@Slf4j
public class FilmService {
    private final Repository<Film, Long> repository;
    private final IdGenerator generator;

    public FilmService() {
        log.info("Init film service.");
        repository = new Repository<>();
        generator = new IdGenerator();
    }

    public Film add(Film film) {
        Long id = generator.generate();
        film.setId(id);
        repository.add(film, id);
        log.info("Add film [" + film.getName() + "] with key [" + id + "]");
        return film;
    }

    public Film update(Long id, Film film) {
        log.info("Update film with key: [" + id + "] with value [" + film + "]. Id not included.");
        return repository.update(film, id);
    }

    public Collection<Film> getAll() {
        log.info("Get all films");
        return repository.getAll();
    }
}

