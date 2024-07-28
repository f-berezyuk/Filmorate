package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

@RestController
@RequestMapping("/mpa")
@Slf4j
public class MpaController {
    private final MpaService mpaService;

    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public Collection<Mpa> getAll() {
        return mpaService.getAll();
    }

    @GetMapping("/{id}")
    public Mpa get(@PathVariable Long id) {
        return mpaService.getById(id);
    }
}
