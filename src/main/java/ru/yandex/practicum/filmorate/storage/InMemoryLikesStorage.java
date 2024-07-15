package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Likes;
import ru.yandex.practicum.filmorate.utilities.KeyGenerator;

@Repository
public class InMemoryLikesStorage extends BaseInMemoryStorage<Likes, Long> {
    public InMemoryLikesStorage(KeyGenerator<Long> idGenerator) {
        super(idGenerator);
    }
}
