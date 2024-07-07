package ru.yandex.practicum.filmorate.utilities;

import org.springframework.stereotype.Component;

@Component
public class LongIdGenerator implements KeyGenerator<Long> {
    private Long lastId;

    public LongIdGenerator() {
        lastId = 0L;
    }

    public Long generate() {
        lastId = lastId + 1;
        return lastId;
    }
}
