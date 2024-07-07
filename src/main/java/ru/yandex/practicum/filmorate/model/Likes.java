package ru.yandex.practicum.filmorate.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Likes implements IdModel<Long> {
    private Long id;
    private Set<Long> likes;

    public Likes() {
        likes = new HashSet<>();
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void add(Long id) {
        likes.add(id);
    }

    public void remove(Long id) {
        likes.remove(id);
    }

    public Integer size() {
        return likes.size();
    }
}
