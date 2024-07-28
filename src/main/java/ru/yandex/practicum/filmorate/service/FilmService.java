package ru.yandex.practicum.filmorate.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.GenreFilm;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.MpaFilm;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.dto.FilmDtoV2;
import ru.yandex.practicum.filmorate.model.dto.GenreDto;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.db.GenreFilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.db.MpaFilmsDbStorage;
import ru.yandex.practicum.filmorate.utilities.DtoMapper;

@Slf4j
@Service
public class FilmService {
    private final Storage<Film, Long> filmStorage;

    private final MpaFilmsDbStorage mpaFilmsStorage;
    private final MpaDbStorage mpaStorage;
    private final GenreFilmDbStorage genreFilmStorage;
    private final GenreDbStorage genreStorage;
    private final LikeService likeService;

    public FilmService(@Qualifier("FilmDbStorage") Storage<Film, Long> storage,
                       MpaFilmsDbStorage mpaFilmsStorage, MpaDbStorage mpaStorage, GenreFilmDbStorage genreFilmStorage,
                       GenreDbStorage genreStorage, LikeService likeService) {
        this.filmStorage = storage;
        this.mpaFilmsStorage = mpaFilmsStorage;
        this.mpaStorage = mpaStorage;
        this.genreFilmStorage = genreFilmStorage;
        this.genreStorage = genreStorage;
        this.likeService = likeService;
    }

    public FilmDto add(@Valid FilmDto film) {
        Film addedFilm = filmStorage.add(DtoMapper.fromDto(film));
        MpaFilm addedMpa = mpaFilmsStorage.add(MpaFilm.builder().filmId(addedFilm.getId())
                .mpaId(film.getMpa().getId()).build());
        Collection<GenreFilm> addedGenres =
                film.getGenres().stream().distinct().map(genre -> genreFilmStorage.add(GenreFilm.builder()
                                .genreId(genre.getId())
                                .filmId(addedFilm.getId())
                                .build()))
                        .toList();
        log.info("Add film [" + addedFilm.getName() + "] with key [" + addedFilm.getId() + "]");
        log.info("Genres: " + Arrays.toString(addedGenres.toArray()));
        log.info("Mpa: " + addedMpa);
        return film.toBuilder().id(addedFilm.getId()).build();
    }

    public FilmDto update(@Valid FilmDto filmDto) {
        Long id = filmDto.getId();
        getOrThrow(id);

        log.info("Update film with key: [" + id + "] with value [" + filmDto + "].");

        MpaFilm oldMpaFilm = mpaFilmsStorage.getByFilm(id);
        Film updatedFilm = filmStorage.update(DtoMapper.fromDto(filmDto), id);
        updateGenres(filmDto, id);
        Collection<Genre> updatedGenres = genreStorage.getByFilmId(id);
        Mpa updatedMpa = updateMpa(filmDto, oldMpaFilm);

        return DtoMapper.toDto(updatedFilm, updatedMpa, updatedGenres);
    }

    private Mpa updateMpa(FilmDto filmDto, MpaFilm oldMpaFilm) {
        Mpa updatedMpa = null;
        Mpa newMpa = mpaStorage.get(filmDto.getMpa().getId());

        if (!(oldMpaFilm == null && newMpa == null)) {
            if (oldMpaFilm == null) {
                updatedMpa = mpaStorage.get(mpaFilmsStorage.add(DtoMapper.mpaFilmFromDto(filmDto, null)).getMpaId());
            } else if (newMpa == null) {
                mpaFilmsStorage.delete(oldMpaFilm.getId());
            } else if (!Objects.equals(oldMpaFilm.getMpaId(), newMpa.getId())) {
                MpaFilm updatedMpaFilm = mpaFilmsStorage.update(DtoMapper.mpaFilmFromDto(filmDto, oldMpaFilm.getId()),
                        oldMpaFilm.getId());
                updatedMpa = mpaStorage.get(updatedMpaFilm.getMpaId());
            }
        }
        return updatedMpa;
    }

    private void updateGenres(FilmDto filmDto, Long id) {
        Set<GenreFilm> genres = new HashSet<>(genreFilmStorage.getByFilm(id));
        HashMap<Long, Long> genreToGenreFilms = new HashMap<>();
        genres.forEach(g -> genreToGenreFilms.put(g.getGenreId(), g.getId()));
        // Add new genres.
        Set<Long> newGenresIds = filmDto.getGenres().stream().map(GenreDto::getId).collect(Collectors.toSet());
        for (Long newGenreId : newGenresIds) {
            if (!genreToGenreFilms.containsKey(newGenreId)) {
                genreFilmStorage.add(GenreFilm.builder()
                        .id(null)
                        .filmId(id)
                        .genreId(newGenreId)
                        .build());
            }
        }
        // Remove old genres
        genreToGenreFilms.keySet().stream().filter(oldKey -> !newGenresIds.contains(oldKey))
                .forEach(oldKey -> genreFilmStorage.delete(genreToGenreFilms.get(oldKey)));
    }

    public Collection<FilmDtoV2> getAll() {
        log.info("Get all films");
        Collection<Film> allFilms = filmStorage.getAll();
        return allFilms.stream().map(film -> getById(film.getId())).collect(Collectors.toList());
    }

    public FilmDtoV2 getById(Long id) {
        Film film = getOrThrow(id);
        Collection<Genre> genres = genreStorage.getByFilmId(id);
        Mpa mpa = mpaStorage.getByFilmId(id);
        return DtoMapper.toDtoV2(film, mpa, genres);
    }

    public Collection<FilmDtoV2> getFilms(int size, int from, String sort) {
        Comparator<LocalDate> comparator = (o1, o2) -> sort.equals("asc") ? o1.compareTo(o2) : o2.compareTo(o1);
        return getAll().stream().sorted(Comparator.comparing(FilmDtoV2::getReleaseDate, comparator)).skip(from).limit(size).toList();

    }

    public Long like(Long filmId, Long userId) throws IllegalArgumentException {
        getOrThrow(filmId);
        likeService.add(userId, filmId);

        return likeService.getCount(filmId);
    }

    private Film getOrThrow(Long filmId) {
        Film film = filmStorage.get(filmId);
        if (film == null) {
            throw new IllegalArgumentException("Film with id " + filmId + "not found.");
        }

        return film;
    }

    public Collection<FilmDtoV2> getTop(Integer count) {
        return likeService.getTop(count).stream().map(this::getById).toList();
    }

    public Long deleteLike(Long filmId, Long userId) {
        return likeService.delete(userId, filmId);
    }
}
