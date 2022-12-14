package ru.otus.service;

import ru.otus.domain.dto.GenreDto;

import java.util.List;

public interface GenreService {
    long getCountOfGenres();

    GenreDto saveGenre(GenreDto genreDto);

    GenreDto findGenreById(long id);

    List<GenreDto> findAllGenres();

    void deleteGenreById(long id);
}
