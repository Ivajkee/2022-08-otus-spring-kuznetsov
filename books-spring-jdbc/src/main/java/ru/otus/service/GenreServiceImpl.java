package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.otus.dao.GenreDao;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Genre;
import ru.otus.exception.GenreNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;
    private final ConversionService conversionService;

    @Override
    public long getCountOfGenres() {
        return genreDao.count();
    }

    @Override
    public GenreDto saveGenre(GenreDto genreDto) {
        Genre genre = conversionService.convert(genreDto, Genre.class);
        Genre savedGenre = genreDao.save(genre);
        return conversionService.convert(savedGenre, GenreDto.class);
    }

    @Override
    public GenreDto updateGenre(GenreDto genreDto) {
        Genre updatedGenre = Optional.of(genreDto)
                .filter(genre -> genreDao.existsById(genre.getId()))
                .map(bd -> conversionService.convert(bd, Genre.class))
                .map(genreDao::update)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id " + genreDto.getId() + " not found!"));
        return conversionService.convert(updatedGenre, GenreDto.class);
    }

    @Override
    public boolean existsGenreById(long id) {
        return genreDao.existsById(id);
    }

    @Override
    public GenreDto findGenreById(long id) {
        Genre genre = genreDao.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id " + id + " not found!"));
        return conversionService.convert(genre, GenreDto.class);
    }

    @Override
    public List<GenreDto> findAllGenres() {
        List<Genre> genres = genreDao.findAll();
        List<GenreDto> genresDto = genres.stream()
                .map(genre -> conversionService.convert(genre, GenreDto.class))
                .collect(Collectors.toList());
        log.info("Found {} genres", genresDto.size());
        return genresDto;
    }

    @Override
    public void deleteGenreById(long id) {
        genreDao.deleteById(id);
        log.info("Genre with id {} deleted", id);
    }
}
