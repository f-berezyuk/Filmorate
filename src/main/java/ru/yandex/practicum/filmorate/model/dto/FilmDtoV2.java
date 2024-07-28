package ru.yandex.practicum.filmorate.model.dto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import ru.yandex.practicum.filmorate.annotation.DateThreshold;
import ru.yandex.practicum.filmorate.annotation.PositiveDuration;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FilmDtoV2 {
    private Long id;
    @NotBlank(message = "Name may not be null or empty")
    private String name;
    @Size(max = 200, message = "Description constrained by 200 characters.")
    private String description;
    @DateThreshold(minDate = "1895-12-28")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonAlias("releaseDate")
    private LocalDate releaseDate;
    @PositiveDuration
    private Integer duration;

    private Mpa mpa = null;

    private Collection<Genre> genres = Collections.emptyList();
}
