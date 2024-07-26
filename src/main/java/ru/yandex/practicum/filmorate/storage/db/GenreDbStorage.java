package ru.yandex.practicum.filmorate.storage.db;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.mapper.GenreRowMapper;

@Component
public class GenreDbStorage extends BaseDbStorage<Genre, Integer> {

    public GenreDbStorage(JdbcTemplate jdbc, GenreRowMapper genreMapper) {
        super(jdbc, genreMapper);
    }

    @Override
    public Genre get(Integer key) {
        String sql = "SELECT * FROM genres WHERE id = ?";
        return findOne(sql, key).orElseThrow(() -> new RepositoryNotFoundException("Жанр не найден"));
    }

    @Override
    public Genre update(Genre genre, Integer key) throws RepositoryNotFoundException {
        String sql = "UPDATE genres SET genre=? WHERE id=?";
        update(sql, genre.getGenre(), key);
        return get(key);
    }

    @Override
    public Collection<Genre> getAll() {
        String sql = "SELECT * FROM genres";
        return findMany(sql);
    }

    @Override
    public Genre add(Genre genre) throws IllegalArgumentException {
        String sql = "INSERT INTO genres (genre) VALUES (?)";
        Integer id = insert(sql, genre.getGenre());
        return get(id);
    }

    @Override
    public Genre delete(Integer key) {
        Genre genre = get(key);
        String sql = "DELETE FROM genres WHERE id = ?";
        delete(sql, key);
        return genre;
    }
}
