package ru.yandex.practicum.filmorate.storage.db;

import java.util.Collection;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.NoContentException;
import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.MpaFilm;
import ru.yandex.practicum.filmorate.storage.db.mapper.MpaFilmRowMapper;

@Component
public class MpaFilmsDbStorage extends BaseDbStorage<MpaFilm, Long> {

    public MpaFilmsDbStorage(JdbcTemplate jdbc, MpaFilmRowMapper mpaMapper) {
        super(jdbc, mpaMapper);
    }

    @Nullable
    public MpaFilm getByFilm(Long filmId) {
        String sql = "SELECT * FROM mpa_films WHERE film = ?";
        return findOne(sql, filmId).orElse(null);
    }

    @Override
    public MpaFilm get(Long key) {
        String sql = "SELECT * FROM mpa_films WHERE id = ?";
        return findOne(sql, key).orElseThrow(() -> new RepositoryNotFoundException("MPA не найден"));
    }

    @Override
    public MpaFilm update(MpaFilm mpa, Long key) throws RepositoryNotFoundException, IllegalArgumentException {
        String sql = "UPDATE mpa_films SET mpa = ? WHERE id = ?";
        update(sql, mpa.getMpaId(), key);
        return get(key);
    }

    @Override
    public Collection<MpaFilm> getAll() {
        String sql = "SELECT * FROM mpa_films";
        return findMany(sql);
    }

    @Override
    public MpaFilm add(@Valid @NotNull MpaFilm mpa) throws IllegalArgumentException {
        String sql = "INSERT INTO mpa_films (mpa, film) VALUES (?, ?)";
        Long id = insert(sql, mpa.getMpaId(), mpa.getFilmId());
        return get(id);
    }

    @Override
    public MpaFilm delete(Long key) {
        MpaFilm mpa = get(key);
        String sql = "DELETE FROM mpa_films WHERE id = ?";
        if (!delete(sql, key)) {
            throw new NoContentException("Нет такой записи.");
        }
        return mpa;
    }
}
