package ru.yandex.practicum.filmorate.storage.inmemory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.utilities.KeyGenerator;

public abstract class BaseInMemoryStorage<T, K> implements Storage<T, K> {
    private final Map<K, T> storage;
    private final KeyGenerator<K> idGenerator;

    public BaseInMemoryStorage(KeyGenerator<K> idGenerator) {
        this.idGenerator = idGenerator;
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

    public T add(T value) throws IllegalArgumentException {
        K key = idGenerator.generate();
        return storage.put(key, value);
    }

    public T delete(K key) {
        return storage.remove(key);
    }
}

