package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import ru.yandex.practicum.filmorate.annotation.DateThreshold;
import ru.yandex.practicum.filmorate.annotation.PositiveDuration;


@Data
@Valid
@AllArgsConstructor
@Builder
public class Film {
    private Long id;
    @NotBlank(message = "Name may not be null or empty")
    private String name;
    @Size(max = 200, message = "Description constrained by 200 characters.")
    private String description;
    @DateThreshold(minDate = "1895-12-28")
    private LocalDate releasedDate;
    @PositiveDuration
    private Integer duration;

    public Film(String name, String description, LocalDate releaseDate, Integer duration) {
        this.name = name;
        this.description = description;
        this.releasedDate = releaseDate;
        this.duration = duration;
    }
}

