package ru.yandex.practicum.filmorate.storage.db;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.mapper.UserRowMapper;

@Component
public class UserDbStorage extends BaseDbStorage<User, Integer> {

    public UserDbStorage(JdbcTemplate jdbc, UserRowMapper userMapper) {
        super(jdbc, userMapper);
    }

    @Override
    public User get(Integer key) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return findOne(sql, key).orElseThrow(() -> new RepositoryNotFoundException("Пользователь не найден"));
    }

    @Override
    public User update(User user, Integer key) throws RepositoryNotFoundException {
        String sql = "UPDATE users SET name=?, login=?, email=?, birthdate=? WHERE id=?";
        update(sql, user.getName(), user.getLogin(), user.getEmail(), user.getBirthdate(), key);
        return get(key);
    }

    @Override
    public Collection<User> getAll() {
        String sql = "SELECT * FROM users";
        return findMany(sql);
    }

    @Override
    public User add(User user) throws IllegalArgumentException {
        String sql = "INSERT INTO users (name, login, email, birthdate) VALUES (?, ?, ?, ?)";
        Integer id = insert(sql, user.getName(), user.getLogin(), user.getEmail(), user.getBirthdate());
        return get(id);
    }

    @Override
    public User delete(Integer key) {
        User user = get(key);
        String sql = "DELETE FROM users WHERE id = ?";
        delete(sql, key);
        return user;
    }
}