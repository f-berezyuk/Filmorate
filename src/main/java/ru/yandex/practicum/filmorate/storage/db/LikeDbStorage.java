package ru.yandex.practicum.filmorate.storage.db;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.db.mapper.LikeRowMapper;

@Component
public class LikeDbStorage extends BaseDbStorage<Like, Long> {

    public LikeDbStorage(JdbcTemplate jdbc, LikeRowMapper likeMapper) {
        super(jdbc, likeMapper);
    }

    @Override
    public Like get(Long key) {
        String sql = "SELECT * FROM likes WHERE id = ?";
        return findOne(sql, key).orElseThrow(() -> new RepositoryNotFoundException("Лайк не найден"));
    }

    @Override
    public Like update(Like like, Long key) throws RepositoryNotFoundException {
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
        Long id = insert(sql, like.getUserId(), like.getFilmId());
        return get(id);
    }

    @Override
    public Like delete(Long key) {
        Like like = get(key);
        String sql = "DELETE FROM likes WHERE id = ?";
        delete(sql, key);
        return like;
    }

    public Collection<Long> getTop(Integer count) {
        String sql = "SELECT f.id FROM FILMS f INNER JOIN LIKES L on f.ID = L.FILM_ID GROUP BY f.id ORDER BY COUNT(*) DESC LIMIT ?";
        return jdbc.queryForList(sql, Long.class, count);
    }
}