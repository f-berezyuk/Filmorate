package ru.yandex.practicum.filmorate.storage.db;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.db.mapper.LikeRowMapper;

@Component
public class LikeDbStorage extends BaseDbStorage<Like, Integer> {

    public LikeDbStorage(JdbcTemplate jdbc, LikeRowMapper likeMapper) {
        super(jdbc, likeMapper);
    }

    @Override
    public Like get(Integer key) {
        String sql = "SELECT * FROM likes WHERE id = ?";
        return findOne(sql, key).orElseThrow(() -> new RepositoryNotFoundException("Лайк не найден"));
    }

    @Override
    public Like update(Like like, Integer key) throws RepositoryNotFoundException {
        String sql = "UPDATE likes SET user_id=?, film_id=? WHERE id=?";
        update(sql, like.getUserId(), like.getFilmId(), key);
        return get(key);
    }

    @Override
    public Collection<Like> getAll() {
        String sql = "SELECT * FROM likes";
        return findMany(sql);
    }

    @Override
    public Like add(Like like) throws IllegalArgumentException {
        String sql = "INSERT INTO likes (user_id, film_id) VALUES (?, ?)";
        Integer id = insert(sql, like.getUserId(), like.getFilmId());
        return get(id);
    }

    @Override
    public Like delete(Integer key) {
        Like like = get(key);
        String sql = "DELETE FROM likes WHERE id = ?";
        delete(sql, key);
        return like;
    }
}