package ru.yandex.practicum.filmorate.storage.db;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.mapper.GenreRowMapper;

@Component
public class GenreDbStorage extends BaseDbStorage<Genre, Long> {

    public GenreDbStorage(JdbcTemplate jdbc, GenreRowMapper genreMapper) {
        super(jdbc, genreMapper);
    }

    @Override
    public Genre get(Long key) {
        String sql = "SELECT * FROM genres WHERE id = ?";
        return findOne(sql, key).orElseThrow(() -> new RepositoryNotFoundException("Жанр не найден"));
    }

    @Override
    public Genre update(Genre genre, Long key) throws RepositoryNotFoundException {
        String sql = "UPDATE genres SET genre=? WHERE id=?";
        update(sql, genre.getName(), key);
        return get(key);
    }

    @Override
    public Collection<Genre> getAll() {
        String sql = "SELECT * FROM genres ORDER BY ID";
        return findMany(sql);
    }

    @Override
    public Genre add(Genre genre) throws IllegalArgumentException {
        String sql = "INSERT INTO genres (genre) VALUES (?)";
        Long id = insert(sql, genre.getName());
        return get(id);
    }

    @Override
    public Genre delete(Long key) {
        Genre genre = get(key);
        String sql = "DELETE FROM genres WHERE id = ?";
        delete(sql, key);
        return genre;
    }

    public Collection<Genre> getByFilmId(Long filmId) {
        String sql = "SELECT g.* FROM genres g INNER JOIN GENRE_FILMS GF on g.ID = GF.GENRE WHERE GF.FILM = ?";
        return findMany(sql, filmId);
    }
}
