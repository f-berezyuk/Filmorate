package ru.yandex.practicum.filmorate.storage.db;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.GenreFilm;
import ru.yandex.practicum.filmorate.storage.db.mapper.GenreFilmRowMapper;

@Component
public class GenreFilmDbStorage extends BaseDbStorage<GenreFilm, Long> {

    public GenreFilmDbStorage(JdbcTemplate jdbc, GenreFilmRowMapper genreFilmMapper) {
        super(jdbc, genreFilmMapper);
    }

    public Collection<GenreFilm> getByFilm(Long filmId) {
        String sql = "SELECT * FROM genre_films WHERE film = ?";
        return findMany(sql, filmId);
    }

    @Override
    public GenreFilm get(Long key) {
        String sql = "SELECT * FROM genre_films WHERE id = ?";
        return findOne(sql, key).orElseThrow(() -> new RepositoryNotFoundException("Связь жанра с фильмом не найдена"));
    }

    @Override
    public GenreFilm update(GenreFilm genreFilm, Long key) throws RepositoryNotFoundException {
        String sql = "UPDATE genre_films SET genre=?, film=? WHERE id=?";
        update(sql, genreFilm.getGenreId(), genreFilm.getFilmId(), key);
        return get(key);
    }

    @Override
    public Collection<GenreFilm> getAll() {
        String sql = "SELECT * FROM genre_films";
        return findMany(sql);
    }

    @Override
    public GenreFilm add(GenreFilm genreFilm) throws IllegalArgumentException {
        String sql = "INSERT INTO genre_films (genre, film) VALUES (?, ?)";
        Long id = insert(sql, genreFilm.getGenreId(), genreFilm.getFilmId());
        return get(id);
    }

    @Override
    public GenreFilm delete(Long key) {
        GenreFilm genreFilm = get(key);
        String sql = "DELETE FROM genre_films WHERE id = ?";
        delete(sql, key);
        return genreFilm;
    }
}
