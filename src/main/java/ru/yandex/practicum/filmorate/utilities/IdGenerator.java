package ru.yandex.practicum.filmorate.utilities;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
    private Long lastId = 0L;

    public long generate() {
        lastId = lastId + 1;
        return lastId;
    }
}
