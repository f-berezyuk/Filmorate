package ru.yandex.practicum.filmorate.utilities;

import org.springframework.stereotype.Component;

@Component
public class IntegerIdGenerator implements KeyGenerator<Integer> {
    private Integer lastId;

    public IntegerIdGenerator() {
        lastId = 0;
    }

    public Integer generate() {
        lastId = lastId + 1;
        return lastId;
    }
}
