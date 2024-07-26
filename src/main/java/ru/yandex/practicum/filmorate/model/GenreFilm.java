package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class GenreFilm {
    private int id;
    private int genreId;
    private int filmId;
}
