package ru.yandex.practicum.filmorate.storage.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.MpaFilm;

@Component
public class MpaFilmRowMapper implements RowMapper<MpaFilm> {
    @Override
    public MpaFilm mapRow(ResultSet rs, int rowNum) throws SQLException {
        return MpaFilm.builder()
                .id(rs.getLong("id"))
                .mpaId(rs.getLong("mpa"))
                .filmId(rs.getLong("film"))
                .build();
    }
}
