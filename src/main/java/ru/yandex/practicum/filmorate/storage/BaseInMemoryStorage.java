package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.IdModel;
import ru.yandex.practicum.filmorate.utilities.KeyGenerator;

public abstract class BaseInMemoryStorage<T extends IdModel<K>, K> implements Storage<T, K> {
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
        return add(key, value);
    }

    public T add(K key, T value) {
        if (storage.containsKey(key)) {
            throw new IllegalArgumentException("Attempt to rewrite value with key " + key + ".");
        }
        value.setId(key);
        return storage.put(key, value);
    }

    @Override
    public Collection<K> keySet() {
        return storage.keySet();
    }
}

