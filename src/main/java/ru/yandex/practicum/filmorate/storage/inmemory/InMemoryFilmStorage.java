package ru.yandex.practicum.filmorate.storage.inmemory;

import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utilities.KeyGenerator;

@Repository
public class InMemoryFilmStorage extends BaseInMemoryStorage<Film, Integer> {
    public InMemoryFilmStorage(KeyGenerator<Integer> keyGenerator) {
        super(keyGenerator);
    }
}
