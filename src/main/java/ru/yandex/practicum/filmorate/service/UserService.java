package ru.yandex.practicum.filmorate.service;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.Repository;

@Slf4j
public class UserService {
    private final Repository<User, Long> repository;
    private final IdGenerator generator;

    public UserService() {
        log.info("Init User service.");
        repository = new Repository<>();
        generator = new IdGenerator();
    }

    public User add(User user) {
        Long id = generator.generate();
        user.setId(id);
        repository.add(user, id);
        log.info("Add user [" + user.getName() + "] with key [" + id + "]");
        return user;
    }

    public User update(Long id, User user) {
        log.info("Update user with key: [" + id + "] with value [" + user + "]. Id not included.");
        return repository.update(user, id);
    }

    public Collection<User> getAll() {
        log.info("Get all users");
        return repository.getAll();
    }
}

