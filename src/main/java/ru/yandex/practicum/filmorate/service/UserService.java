package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exception.NoContentException;
import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

@Slf4j
@Service
public class UserService {
    private final Storage<User, Long> storage;
    private final FriendshipService friendshipService;

    public UserService(@Qualifier("UserDbStorage") Storage<User, Long> storage,
                       FriendshipService friendshipService) {
        this.storage = storage;
        this.friendshipService = friendshipService;
    }

    public User add(User user) {
        User added = storage.add(user);
        log.info("Add user [" + added.getName() + "] with key [" + added.getId() + "]");
        return added;
    }

    public void addFriend(Long from, Long to) {
        getOrThrow(from);
        getOrThrow(to);

        friendshipService.requestShip(from, to);
    }

    public User getOrThrow(Long id) {
        User user = storage.get(id);
        if (user == null) {
            throw new RepositoryNotFoundException("User with id " + id + " not found.");
        }
        return user;
    }

    public void removeFriend(Long id) {
        friendshipService.delete(id);
    }

    public Collection<User> getAllCommonFriends(Long from, Long to) {
        Set<User> first = new HashSet<>(getFriends(from));
        Set<User> second = new HashSet<>(getFriends(to));

        return first.stream().filter(second::contains).collect(Collectors.toSet());
    }

    public User update(User user) {
        Long id = user.getId();
        log.info("Update user with key: [" + id + "] with value [" + user + "].");
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
        getOrThrow(id);
        return friendshipService.getAllFriends(id).stream()
                .flatMap(f -> Stream.of(f.getUserTo(), f.getUserFrom()))
                .filter(i -> !Objects.equals(id, i))
                .map(this::getById)
                .collect(Collectors.toList());
    }

    public void removeFriend(Long id, Long friendId) throws NoContentException {
        getOrThrow(id);
        getOrThrow(friendId);
        Friendship friendship = friendshipService.findByIds(id, friendId);
        if (friendship == null) {
            throw new NoContentException("Дружба не найдена");
        }
        friendshipService.delete(friendship.getId());
    }

    public User delete(Long id) {
        return storage.delete(id);
    }
}

