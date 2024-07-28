package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.db.LikeDbStorage;

@Slf4j
@Service
@AllArgsConstructor
public class LikeService {
    private final LikeDbStorage storage;

    public Like add(Long userId, Long filmId) {
        Like like = Like.builder().filmId(filmId).userId(userId).build();
        return storage.add(like);
    }

    public Collection<Long> getTop(Integer count) {
        return storage.getTop(count);
    }

    public void delete(Long id) {
        storage.delete(id);
    }

    public Like get(Long id) {
        return storage.get(id);
    }

    public Long getCount(Long filmId) {
        return storage.getAll().stream().filter(l -> Objects.equals(filmId, l.getFilmId())).count();
    }

    public Long delete(Long userId, Long filmId) {
        var like = findByUserFilmId(userId, filmId);
        storage.delete(like.getId());
        return getCount(filmId);
    }

    private Like findByUserFilmId(Long userId, Long filmId) {
        return storage.getAll().stream()
                .filter(f -> Objects.equals(f.getFilmId(), filmId))
                .filter(f -> Objects.equals(f.getUserId(), userId))
                .findFirst()
                .orElseThrow(() -> new RepositoryNotFoundException("Didn't find like"));
    }
}
