package ru.yandex.practicum.filmorate.utilities;

import org.springframework.stereotype.Component;

@Component
public interface KeyGenerator<T> {
    T generate();
}
