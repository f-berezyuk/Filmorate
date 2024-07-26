package ru.yandex.practicum.filmorate.storage.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.Like;

@Component
public class LikeRowMapper implements RowMapper<Like> {
    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Like.builder()
                .id(rs.getInt("id"))
                .userId(rs.getInt("userId"))
                .filmId(rs.getInt("filmId"))
                .build();
    }
}