package ru.yandex.practicum.filmorate.storage.db;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.storage.Storage;

@RequiredArgsConstructor
@Slf4j
public abstract class BaseDbStorage<T, K> implements Storage<T, K> {
    protected final JdbcTemplate jdbc;
    protected final RowMapper<T> mapper;

    protected Optional<T> findOne(String query, Object... params) {
        try {
            T result = jdbc.queryForObject(query, mapper, params);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        } catch (Throwable e) {
            log.error("Fail to get user.", e);
            throw e;
        }
    }

    protected List<T> findMany(String query, Object... params) {
        return jdbc.query(query, mapper, params);
    }

    protected boolean delete(String query, Long id) {
        int rowsDeleted = jdbc.update(query, id);
        return rowsDeleted > 0;
    }

    protected void update(String query, Object... params) throws RepositoryNotFoundException {
        int rowsUpdated = jdbc.update(query, params);
        if (rowsUpdated == 0) {
            throw new RepositoryNotFoundException("Не удалось обновить данные");
        }
    }

    protected Long insert(String query, Object... params) throws IllegalArgumentException {
        try {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                for (int idx = 0; idx < params.length; idx++) {
                    ps.setObject(idx + 1, params[idx]);
                }
                return ps;
            }, keyHolder);

            Integer keyAs = keyHolder.getKeyAs(Integer.class);

            if (keyAs != null) {
                return Long.valueOf(keyAs);
            } else {
                throw new IllegalArgumentException("Не удалось сохранить данные");
            }
        } catch (Throwable e) {
            log.error("Error on insert.", e);
            throw new IllegalArgumentException("Не удалось сохранить данные", e);
        }
    }
}