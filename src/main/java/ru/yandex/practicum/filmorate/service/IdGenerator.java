package ru.yandex.practicum.filmorate.service;

public class IdGenerator {
    private Long lastId = 0L;

    public long generate() {
        lastId = lastId + 1;
        return lastId;
    }
}
