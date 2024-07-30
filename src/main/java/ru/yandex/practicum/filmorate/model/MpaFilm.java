package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class MpaFilm {
    private Long id;
    private Long mpaId;
    private Long filmId;
}
