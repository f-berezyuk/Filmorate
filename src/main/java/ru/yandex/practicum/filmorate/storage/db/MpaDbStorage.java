package ru.yandex.practicum.filmorate.storage.db;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.NoContentException;
import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.mapper.MpaRowMapper;

@Component
public class MpaDbStorage extends BaseDbStorage<Mpa, Long> {
    public MpaDbStorage(JdbcTemplate jdbc, MpaRowMapper mpaMapper) {
        super(jdbc, mpaMapper);
    }

    @Override
    public Mpa get(Long key) {
        String sql = "SELECT * FROM mpa WHERE id = ?";
        return findOne(sql, key).orElseThrow(() -> new RepositoryNotFoundException("MPA не найден"));
    }

    @Override
    public Mpa update(Mpa mpa, Long key) throws RepositoryNotFoundException, IllegalArgumentException {
        String sql = "UPDATE mpa SET mpa = ? WHERE id = ?";
        update(sql, mpa.getName());
        return get(key);
    }

    @Override
    public Collection<Mpa> getAll() {
        String sql = "SELECT * FROM mpa ORDER BY ID";
        return findMany(sql);
    }

    @Override
    public Mpa add(Mpa mpa) throws IllegalArgumentException {
        String sql = "INSERT INTO mpa (mpa) VALUES (?)";
        Long id = insert(sql, mpa.getName());
        return get(id);
    }

    @Override
    public Mpa delete(Long key) {
        Mpa mpa = get(key);
        String sql = "DELETE FROM mpa WHERE id = ?";
        if (!delete(sql, key)) {
            throw new NoContentException("Нет такой записи.");
        }
        return mpa;
    }

    public Mpa getByFilmId(Long id) {
        String sql = "SELECT * FROM mpa WHERE id = (SELECT mpa FROM MPA_FILMS WHERE FILM = ?)";
        return findOne(sql, id)
                .orElse(null);
    }
}
