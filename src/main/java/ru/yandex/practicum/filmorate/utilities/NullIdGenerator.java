package ru.yandex.practicum.filmorate.utilities;

public class NullIdGenerator implements KeyGenerator<Long> {

    @Override
    public Long generate() {
        return null;
    }
}
