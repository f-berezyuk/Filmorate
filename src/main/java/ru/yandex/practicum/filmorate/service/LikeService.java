package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
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
    private final Storage<Like, Integer> storage;

    public Like add(Integer userId, Integer filmId) {
        Like like = Like.builder().filmId(filmId).userId(userId).build();
        return storage.add(like);
    }

    public Collection<Integer> getTop(Integer count) {
        Hashtable<Integer, Long> filmToLikes = new Hashtable<>();
        List<Like> all = storage.getAll().stream().toList();
        all.forEach(like -> {
            int id = like.getFilmId();
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

    public void delete(Integer id) {
        storage.delete(id);
    }

    public Like get(Integer id) {
        return storage.get(id);
    }

    public Long getCount(Integer filmId) {
        return storage.getAll().stream().filter(l -> filmId == l.getFilmId()).count();
    }

    public Long delete(Integer userId, Integer filmId) {
        var like = findByUserFilmId(userId, filmId);
        storage.delete(like.getId());
        return getCount(filmId);
    }

    private Like findByUserFilmId(Integer userId, Integer filmId) {
        return storage.getAll().stream()
                .filter(f -> f.getFilmId() == filmId)
                .filter(f -> f.getUserId() == userId)
                .findFirst()
                .orElseThrow(() -> new RepositoryNotFoundException("Didn't find like"));
    }
}
