package ru.yandex.practicum.filmorate.model;

import org.springframework.stereotype.Component;

@Component
public interface IdModel<T> {
    void setId(T id);
}
