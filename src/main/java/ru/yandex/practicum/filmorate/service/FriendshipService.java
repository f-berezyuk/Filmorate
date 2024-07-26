package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.storage.Storage;

@Slf4j
@Service
@AllArgsConstructor
public class FriendshipService {
    private final Storage<Friendship, Integer> storage;

    public Friendship requestShip(Integer from, Integer to) {
        return storage.add(Friendship.builder()
                .userFromId(from)
                .userToId(to)
                .build());
    }

    public void approveShip(Integer id) {
        storage.update(storage.get(id).toBuilder().status(true).build(), id);
    }

    public void delete(Integer id) {
        storage.delete(id);
    }

    public Collection<Integer> getCommonFriends(Integer from, Integer to) {

        var friends_from = getFriendIds(from);
        var friends_to = getFriendIds(to);

        return friends_from.stream().filter(friends_to::contains).collect(Collectors.toList());
    }

    public Set<Integer> getFriendIds(Integer id) {
        return getAll(id).filter(Friendship::isStatus)
                .map(friendship ->
                        Objects.equals(friendship.getUserFromId(), id)
                                ? friendship.getUserToId()
                                : friendship.getUserFromId())
                .collect(Collectors.toSet());
    }

    public Stream<Friendship> getAll(Integer id) {
        return storage.getAll().stream().filter(friendship ->
                (Objects.equals(id, friendship.getUserFromId())
                        || Objects.equals(id, friendship.getUserToId()))
        );
    }

    public Friendship findByIds(Integer id, Integer friendId) {
        return getAll(id).filter(friendship -> Objects.equals(friendship.getUserFromId(), friendId)
                || Objects.equals(friendship.getUserToId(), friendId)).findFirst().orElseThrow();
    }
}
