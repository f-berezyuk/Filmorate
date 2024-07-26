package ru.yandex.practicum.filmorate.storage.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.MpaFilm;

@Component
public class MpaFilmMapper implements RowMapper<MpaFilm> {
    @Override
    public MpaFilm mapRow(ResultSet rs, int rowNum) throws SQLException {
        return MpaFilm.builder()
                .id(rs.getInt("id"))
                .mpaId(rs.getInt("mpaId"))
                .filmId(rs.getInt("filmId"))
                .build();
    }
}
