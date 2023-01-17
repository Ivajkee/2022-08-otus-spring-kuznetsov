package ru.otus.service;

import ru.otus.domain.dto.GenreDto;

import java.util.List;

public interface GenreService {
    long getCountOfGenres();

    GenreDto saveGenre(GenreDto genreDto);

    GenreDto updateGenre(GenreDto genreDto);

    boolean existsGenreById(long id);

    GenreDto findGenreById(long id);

    GenreDto findGenreByName(String name);

    List<GenreDto> findAllGenres();

    void deleteGenreById(long id);
}
