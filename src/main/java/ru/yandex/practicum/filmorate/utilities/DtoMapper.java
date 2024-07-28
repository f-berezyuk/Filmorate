package ru.yandex.practicum.filmorate.utilities;

import java.util.Collection;
import java.util.stream.Collectors;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.MpaFilm;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.dto.FilmDtoV2;
import ru.yandex.practicum.filmorate.model.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.dto.MpaDto;

public class DtoMapper {

    public static FilmDto toDto(Film film, Mpa mpa, Collection<Genre> genres) {
        FilmDto.FilmDtoBuilder builder = FilmDto.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleasedDate())
                .duration(film.getDuration())
                .genres(genres.stream().map(Genre::getId).map(GenreDto::new).collect(Collectors.toList()));
        if (mpa != null) {
            builder.mpa(new MpaDto(mpa.getId()));
        }
        return builder.build();
    }

    public static Film fromDto(FilmDto filmDto) {
        return Film.builder()
                .id(filmDto.getId())
                .name(filmDto.getName())
                .description(filmDto.getDescription())
                .releasedDate(filmDto.getReleaseDate())
                .duration(filmDto.getDuration())
                .build();
    }

    public static MpaFilm mpaFilmFromDto(FilmDto filmDto, Long mpaFilmId) {
        return MpaFilm.builder()
                .id(mpaFilmId)
                .filmId(filmDto.getId())
                .mpaId(filmDto.getMpa().getId())
                .build();
    }

    public static FilmDtoV2 toDtoV2(Film film, Mpa mpa, Collection<Genre> genres) {
        FilmDtoV2.FilmDtoV2Builder builder = FilmDtoV2.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleasedDate())
                .duration(film.getDuration())
                .genres(genres);
        if (mpa != null) {
            builder.mpa(mpa);
        }
        return builder.build();
    }
}
