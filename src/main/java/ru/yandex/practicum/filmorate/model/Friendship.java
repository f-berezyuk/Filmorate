package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Friendship {
    private Integer id;
    private Integer userFromId;
    private Integer userToId;
    private boolean status;
}