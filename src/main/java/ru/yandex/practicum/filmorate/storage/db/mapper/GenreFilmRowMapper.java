package ru.yandex.practicum.filmorate.storage.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.GenreFilm;

@Component
public class GenreFilmRowMapper implements RowMapper<GenreFilm> {
    @Override
    public GenreFilm mapRow(ResultSet rs, int rowNum) throws SQLException {
        return GenreFilm.builder()
                .id(rs.getLong("id"))
                .genreId(rs.getLong("genre"))
                .filmId(rs.getLong("film"))
                .build();
    }
}