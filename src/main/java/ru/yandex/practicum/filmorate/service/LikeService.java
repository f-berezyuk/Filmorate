package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exception.RepositoryNotFoundException;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.Storage;

@Slf4j
@Service
@AllArgsConstructor
public class LikeService {
    private final Storage<Like, Long> storage;

    public Like add(Long userId, Long filmId) {
        Like like = Like.builder().filmId(filmId).userId(userId).build();
        return storage.add(like);
    }

    public Collection<Long> getTop(Integer count) {
        Hashtable<Long, Long> filmToLikes = new Hashtable<>();
        List<Like> all = storage.getAll().stream().toList();
        all.forEach(like -> {
            long id = like.getFilmId();
            long value = filmToLikes.contains(id)
                    ? filmToLikes.get(id)
                    : 0;
            filmToLikes.put(id, value + 1);
        });
        return filmToLikes.keySet().stream()
                .sorted(Comparator.comparing(filmToLikes::get))
                .limit(count)
                .collect(Collectors.toList());
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
