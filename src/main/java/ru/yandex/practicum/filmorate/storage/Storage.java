package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;

@Repository
public interface Storage<T, K> {
    T get(K key);

    T update(T value, K key) throws RepositoryNotFoundException, IllegalArgumentException;

    Collection<T> getAll();

    T add(T value) throws IllegalArgumentException;

    T delete(K key);
}
