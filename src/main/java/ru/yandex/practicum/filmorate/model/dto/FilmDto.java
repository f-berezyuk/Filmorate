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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FilmDto {
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
    @JsonAlias("duration")
    private Integer duration;

    private MpaDto mpa = null;

    private Collection<GenreDto> genres = Collections.emptyList();

    public FilmDto(String name, String description, LocalDate releaseDate, Integer duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public FilmDto(String name, String description, LocalDate releaseDate, Integer duration, MpaDto mpa,
                   Collection<GenreDto> genres) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.genres = genres;
    }
}
