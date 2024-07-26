package ru.yandex.practicum.filmorate.storage.inmemory;

import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utilities.IntegerIdGenerator;

@Repository
public class InMemoryUserStorage extends BaseInMemoryStorage<User, Integer> {
    public InMemoryUserStorage() {
        super(new IntegerIdGenerator());
    }
}
