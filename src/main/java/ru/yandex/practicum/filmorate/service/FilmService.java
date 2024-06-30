package ru.yandex.practicum.filmorate.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.Repository;
import ru.yandex.practicum.filmorate.utilities.IdGenerator;

@Slf4j
@Service
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

    public Film getById(Long id) {
        return repository.get(id);
    }

    public Collection<Film> getFilms(int size, int from, String sort) {
        Comparator<LocalDate> comparator = (o1, o2) ->
                sort.equals("asc")
                        ? o1.compareTo(o2)
                        : o2.compareTo(o1);
        return repository.getAll().stream()
                .sorted(Comparator.comparing(Film::getReleaseDate, comparator))
                .skip(from)
                .limit(size)
                .toList();

    }
}

