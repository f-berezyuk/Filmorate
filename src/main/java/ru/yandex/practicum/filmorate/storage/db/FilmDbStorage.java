package ru.yandex.practicum.filmorate.storage.db;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.db.mapper.FilmRowMapper;

@Component("FilmDbStorage")
public class FilmDbStorage extends BaseDbStorage<Film, Long> {

    public FilmDbStorage(JdbcTemplate jdbc, FilmRowMapper filmMapper) {
        super(jdbc, filmMapper);
    }

    @Override
    public Film get(Long key) {
        String sql = "SELECT * FROM films WHERE id = ?";
        return findOne(sql, key).orElseThrow(() -> new RepositoryNotFoundException("Фильм не найден"));
    }

    @Override
    public Film update(Film film, Long key) throws RepositoryNotFoundException {
        String sql = "UPDATE films SET name=?, description=?, releaseDate=?, duration=? WHERE id=?";
        update(sql, film.getName(), film.getDescription(), film.getReleasedDate(), film.getDuration(), key);
        return get(key);
    }

    @Override
    public Collection<Film> getAll() {
        String sql = "SELECT * FROM films";
        return findMany(sql);
    }

    @Override
    public Film add(Film film) throws IllegalArgumentException {
        String sql = "INSERT INTO films (name, description, releaseDate, duration) VALUES (?, ?, ?, ?)";
        Long id = insert(sql, film.getName(), film.getDescription(), film.getReleasedDate(), film.getDuration());
        return get(id);
    }

    @Override
    public Film delete(Long key) {
        Film film = get(key);
        String sql = "DELETE FROM films WHERE id = ?";
        delete(sql, key);
        return film;
    }
}