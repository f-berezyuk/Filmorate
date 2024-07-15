package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utilities.KeyGenerator;

@Repository
public class InMemoryUserStorage extends BaseInMemoryStorage<User, Long> {
    public InMemoryUserStorage(KeyGenerator<Long> keyGenerator) {
        super(keyGenerator);
    }
}
