package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class MpaFilm {
    private int id;
    private int mpaId;
    private int filmId;
}
