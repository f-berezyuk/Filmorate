package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final Storage<User, Integer> storage;
    private final FriendshipService friendshipService;

    public User add(User user) {
        storage.add(user);
        log.info("Add user [" + user.getName() + "] with key [" + user.getId() + "]");
        return user;
    }

    public void addFriend(Integer from, Integer to) {
        getOrThrow(from);
        getOrThrow(to);

        friendshipService.requestShip(from, to);
    }

    public User getOrThrow(Integer id) {
        User user = storage.get(id);
        if (user == null) {
            throw new NullPointerException("User with id " + id + " not found.");
        }
        return user;
    }

    public void removeFriend(Integer id) {
        friendshipService.delete(id);
    }

    public Collection<User> getAllCommonFriends(Integer from, Integer to) {
        getOrThrow(from);
        getOrThrow(to);

        return friendshipService.getCommonFriends(from, to).stream().map(this::getById).collect(Collectors.toList());
    }

    public User update(Integer id, User user) {
        log.info("Update user with key: [" + id + "] with value [" + user + "]. Id not included.");
        return storage.update(user, id);
    }

    public Collection<User> getAll() {
        log.info("Get all users");
        return storage.getAll();
    }

    public User getById(Integer id) {
        return storage.get(id);
    }

    public Collection<User> getFriends(Integer id) {
        return friendshipService.getAll(id)
                .map(friendship -> Objects.equals(friendship.getUserFromId(), id)
                        ? friendship.getUserToId()
                        : friendship.getUserFromId())
                .map(this::getById)
                .collect(Collectors.toList());
    }

    public void removeFriend(Integer id, Integer friendId) {
        friendshipService.delete(friendshipService.findByIds(id, friendId).getId());
    }
}

