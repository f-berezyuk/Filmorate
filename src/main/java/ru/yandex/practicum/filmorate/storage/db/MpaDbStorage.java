package ru.yandex.practicum.filmorate.storage.db;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.mapper.MpaRowMapper;

@Component
public class MpaDbStorage extends BaseDbStorage<Mpa, Integer> {

    public MpaDbStorage(JdbcTemplate jdbc, MpaRowMapper mpaMapper) {
        super(jdbc, mpaMapper);
    }

    @Override
    public Mpa get(Integer key) {
        String sql = "SELECT * FROM mpa WHERE id = ?";
        return findOne(sql, key).orElseThrow(() -> new RepositoryNotFoundException("MPA не найден"));
    }

    @Override
    public Mpa update(Mpa mpa, Integer key) throws RepositoryNotFoundException {
        String sql = "UPDATE mpa SET mpa=? WHERE id=?";
        update(sql, mpa.getMpa(), key);
        return get(key);
    }

    @Override
    public Collection<Mpa> getAll() {
        String sql = "SELECT * FROM mpa";
        return findMany(sql);
    }

    @Override
    public Mpa add(Mpa mpa) throws IllegalArgumentException {
        String sql = "INSERT INTO mpa (mpa) VALUES (?)";
        Integer id = insert(sql, mpa.getMpa());
        return get(id);
    }

    @Override
    public Mpa delete(Integer key) {
        Mpa mpa = get(key);
        String sql = "DELETE FROM mpa WHERE id = ?";
        delete(sql, key);
        return mpa;
    }
}
