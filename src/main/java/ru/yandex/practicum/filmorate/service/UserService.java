package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.utilities.IdGenerator;

@Slf4j
@Service
public class UserService {
    private final Storage<User, Long> storage;
    private final IdGenerator generator;

    public UserService(Storage<User, Long> repository, IdGenerator generator) {
        log.info("Init User service.");
        this.storage = repository;
        this.generator = generator;
    }

    public User add(User user) {
        Long id = generator.generate();
        user.setId(id);
        storage.add(user, id);
        log.info("Add user [" + user.getName() + "] with key [" + id + "]");
        return user;
    }

    public User addFriend(Long one, Long two) {
        User first = getUserOrThrow(one);
        User second = getUserOrThrow(two);

        first.addFriend(two);
        second.addFriend(one);

        return first;
    }

    private User getUserOrThrow(Long id) {
        User user = storage.get(id);
        if (user == null) {
            throw new NullPointerException("User with id " + id + " not found.");
        }
        return user;
    }

    public User removeFriend(Long one, Long two) {
        User first = getUserOrThrow(one);
        User second = getUserOrThrow(two);

        first.removeFriend(two);
        second.removeFriend(one);

        return first;
    }

    public Collection<User> getAllCommonFriends(Long one, Long two) {
        User first = getUserOrThrow(one);
        User second = getUserOrThrow(two);

        Set<Long> secondFriends = second.getFriends();

        return first.getFriends().stream()
                .filter(secondFriends::contains)
                .map(storage::get)
                .toList();
    }

    public User update(Long id, User user) {
        log.info("Update user with key: [" + id + "] with value [" + user + "]. Id not included.");
        return storage.update(user, id);
    }

    public Collection<User> getAll() {
        log.info("Get all users");
        return storage.getAll();
    }

    public User getById(Long id) {
        return storage.get(id);
    }

    public Collection<User> getFriends(Long id) {
        return getUserOrThrow(id).getFriends().stream()
                .map(this::getUserOrThrow)
                .collect(Collectors.toList());
    }
}

