package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.dto.FilmDtoV2;
import ru.yandex.practicum.filmorate.service.FilmService;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDto addFilm(@RequestBody @Valid FilmDto film) {
        return filmService.add(film);
    }

    @PutMapping
    public FilmDto updateFilm(@RequestBody @Valid FilmDto film) {
        return filmService.update(film);
    }

    @GetMapping
    public Collection<FilmDtoV2> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/by-release-date")
    public Collection<FilmDtoV2> findAll(@RequestParam(defaultValue = "10") @Positive Integer size,
                                         @RequestParam(defaultValue = "0") @Positive Integer from,
                                         @RequestParam(defaultValue = "asc") String sort) {
        return filmService.getFilms(size, from, sort);
    }

    @GetMapping("/{id}")
    public FilmDtoV2 getById(@PathVariable Long id) {
        return filmService.getById(id);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Long likeFilm(@PathVariable Long filmId, @PathVariable Long userId) {
        return filmService.like(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Long unlikeFilm(@PathVariable Long filmId, @PathVariable Long userId) {
        return filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<FilmDtoV2> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.getTop(count);
    }
}
