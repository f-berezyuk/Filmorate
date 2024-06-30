package ru.yandex.practicum.filmorate.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;

public class Repository<T, K> {
    private final Map<K, T> storage;

    public Repository() {
        this.storage = new HashMap<>();
    }

    public T get(K key) {
        return storage.get(key);
    }

    public T update(T value, K key) throws RepositoryNotFoundException {
        if (storage.containsKey(key)) {
            return storage.put(key, value);
        }

        throw new RepositoryNotFoundException("Value with key " + key + " not found.");
    }

    public Collection<T> getAll() {
        return storage.values();
    }

    public T add(T value, K key) throws IllegalArgumentException {
        if (storage.containsKey(key)) {
            throw new IllegalArgumentException("Attempt to rewrite value with key " + key + ".");
        }

        return storage.put(key, value);
    }
}

