package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

@Slf4j
@Service
public class UserService {
    private final Storage<User, Long> storage;

    public UserService(Storage<User, Long> repository) {
        log.info("Init User service.");
        this.storage = repository;
    }

    public User add(User user) {
        storage.add(user);
        log.info("Add user [" + user.getName() + "] with key [" + user.getId() + "]");
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

