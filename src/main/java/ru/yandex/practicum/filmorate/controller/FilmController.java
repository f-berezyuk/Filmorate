package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

@RestController
@RequestMapping("/films")
public class FilmController {

    FilmService filmService = new FilmService();

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        return filmService.add(film);
    }

    @PatchMapping
    public Film updateFilm(@RequestBody @Valid Film film, Long id) {
        return filmService.update(id, film);
    }

    @GetMapping
    public Collection<Film> getAll() {
        return filmService.getAll();
    }
}
