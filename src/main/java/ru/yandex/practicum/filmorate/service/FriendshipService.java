package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.storage.Storage;

@Slf4j
@Service
@AllArgsConstructor
public class FriendshipService {
    private final Storage<Friendship, Long> storage;

    private static boolean oneOf(Long id, Friendship friendship) {
        return Objects.equals(id, friendship.getUserFrom())
                || Objects.equals(id, friendship.getUserTo());
    }

    private static boolean isFriends(Long id, Friendship f) {
        return Objects.equals(f.getUserFrom(), id)
                || (Objects.equals(f.getUserTo(), id) && f.isStatus());
    }

    public Friendship requestShip(Long from, Long to) {
        return storage.add(Friendship.builder()
                .userFrom(from)
                .userTo(to)
                .build());
    }

    public void approveShip(Long id) {
        storage.update(storage.get(id).toBuilder().status(true).build(), id);
    }

    public void delete(Long id) {
        storage.delete(id);
    }

    public Set<Friendship> getAllFriends(Long id) {
        return getAll().stream().filter(f -> isFriends(id, f))
                .collect(Collectors.toSet());
    }

    public Set<Long> getFriendIds(Long id) {
        return getAllConfirmed(id)
                .map(friendship ->
                        Objects.equals(friendship.getUserFrom(), id)
                                ? friendship.getUserTo()
                                : friendship.getUserFrom())
                .collect(Collectors.toSet());
    }

    public Collection<Friendship> getAll() {
        return storage.getAll();
    }

    public Collection<Friendship> getAll(Long id) {
        return storage.getAll().stream().filter(friendship -> oneOf(id, friendship)).collect(Collectors.toList());
    }

    public Stream<Friendship> getAllConfirmed(Long id) {
        return storage.getAll().stream().filter(Friendship::isStatus).filter(friendship ->
                oneOf(id, friendship)
        );
    }

    @Nullable
    public Friendship findByIds(Long id, Long friendId) {
        return getAll().stream().filter(f -> (Objects.equals(f.getUserFrom(), id)) ||
                        (Objects.equals(f.getUserTo(), id) && f.isStatus())).filter(f -> oneOf(friendId, f)).findFirst()
                .orElse(null);
    }
}
