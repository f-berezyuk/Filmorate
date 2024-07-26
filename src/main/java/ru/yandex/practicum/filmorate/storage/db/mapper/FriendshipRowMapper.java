package ru.yandex.practicum.filmorate.storage.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.Friendship;

@Component
public class FriendshipRowMapper implements RowMapper<Friendship> {
    @Override
    public Friendship mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Friendship.builder()
                .id(rs.getInt("id"))
                .userFromId(rs.getInt("userFromId"))
                .userToId(rs.getInt("userToId"))
                .status(rs.getBoolean("status"))
                .build();
    }
}