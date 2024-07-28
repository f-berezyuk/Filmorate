package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Friendship {
    private Long id;
    private Long userFrom;
    private Long userTo;
    private boolean status;
}