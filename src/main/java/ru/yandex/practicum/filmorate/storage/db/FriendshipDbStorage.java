package ru.yandex.practicum.filmorate.storage.db;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.storage.db.mapper.FriendshipRowMapper;

@Component
public class FriendshipDbStorage extends BaseDbStorage<Friendship, Integer> {

    public FriendshipDbStorage(JdbcTemplate jdbc, FriendshipRowMapper friendshipMapper) {
        super(jdbc, friendshipMapper);
    }

    @Override
    public Friendship get(Integer key) {
        String sql = "SELECT * FROM friendships WHERE id = ?";
        return findOne(sql, key).orElseThrow(() -> new RepositoryNotFoundException("Дружба не найдена"));
    }

    @Override
    public Friendship update(Friendship friendship, Integer key) throws RepositoryNotFoundException {
        String sql = "UPDATE friendships SET user_from_id=?, user_to_id=?, status=? WHERE id=?";
        update(sql, friendship.getUserFromId(), friendship.getUserToId(), friendship.isStatus(), key);
        return get(key);
    }

    @Override
    public Collection<Friendship> getAll() {
        String sql = "SELECT * FROM friendships";
        return findMany(sql);
    }

    @Override
    public Friendship add(Friendship friendship) throws IllegalArgumentException {
        String sql = "INSERT INTO friendships (user_from_id, user_to_id, status) VALUES (?, ?, ?)";
        Integer id = insert(sql, friendship.getUserFromId(), friendship.getUserToId(), friendship.isStatus());
        return get(id);
    }

    @Override
    public Friendship delete(Integer key) {
        Friendship friendship = get(key);
        String sql = "DELETE FROM friendships WHERE id = ?";
        delete(sql, key);
        return friendship;
    }
}