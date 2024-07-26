package ru.yandex.practicum.filmorate.storage.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.Film;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Film.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .released(rs.getObject("released", LocalDate.class))
                .durationMin(rs.getInt("duration_min"))
                .build();
    }
}

