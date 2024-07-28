package ru.yandex.practicum.filmorate.service;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;

@Service
public class GenreService {

    private final GenreDbStorage genreDbStorage;

    public GenreService(GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    @GetMapping
    public Collection<Genre> getAll() {
        return genreDbStorage.getAll();
    }

    public Genre getById(Long id) {
        return genreDbStorage.get(id);
    }
}

