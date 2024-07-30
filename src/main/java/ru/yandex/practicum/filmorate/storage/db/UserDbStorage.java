package ru.yandex.practicum.filmorate.storage.db;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.mapper.UserRowMapper;

@Component("UserDbStorage")
@Slf4j
public class UserDbStorage extends BaseDbStorage<User, Long> {

    public UserDbStorage(JdbcTemplate jdbc, UserRowMapper userMapper) {
        super(jdbc, userMapper);
    }

    @Override
    public User get(Long key) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return findOne(sql, key).orElseThrow(() -> new RepositoryNotFoundException("Пользователь не найден"));
    }

    @Override
    public User update(User user, Long key) throws RepositoryNotFoundException {
        String sql = "UPDATE users SET name=?, login=?, email=?, birthday=? WHERE id=?";
        update(sql, user.getName(), user.getLogin(), user.getEmail(), user.getBirthday(), key);
        return get(key);
    }

    @Override
    public Collection<User> getAll() {
        String sql = "SELECT * FROM users";
        return findMany(sql);
    }

    @Override
    public User add(User user) throws IllegalArgumentException {
        log.info("Insert user: " + user.toString());
        String sql = "INSERT INTO users (name, login, email, birthday) VALUES (?, ?, ?, ?)";
        Long id = insert(sql, user.getName(), user.getLogin(), user.getEmail(), user.getBirthday());
        return get(id);
    }

    @Override
    public User delete(Long key) {
        User user = get(key);
        String sql = "DELETE FROM users WHERE id = ?";
        delete(sql, key);
        return user;
    }
}