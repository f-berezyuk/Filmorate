package ru.yandex.practicum.filmorate.service;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.MpaDbStorage;

@Service
public class MpaService {

    private final MpaDbStorage mpaDbStorage;

    public MpaService(MpaDbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }

    @GetMapping
    public Collection<Mpa> getAll() {
        return mpaDbStorage.getAll();
    }

    @GetMapping("/{id}")
    public Mpa getById(@PathVariable Long id) {
        return mpaDbStorage.get(id);
    }
}
