package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        long count = genreDao.count();
        log.debug("Found {} genres", count);
        return count;
    }

    @Override
    public GenreDto saveGenre(GenreDto genreDto) {
        Genre genre = conversionService.convert(genreDto, Genre.class);
        Genre savedGenre = genreDao.save(genre);
        GenreDto savedGenreDto = conversionService.convert(savedGenre, GenreDto.class);
        log.debug("Saved genre: {}", savedGenreDto);
        return savedGenreDto;
    }

    @Transactional
    @Override
    public GenreDto updateGenre(GenreDto genreDto) {
        Genre updatedGenre = Optional.of(genreDto)
                .filter(dto -> genreDao.existsById(dto.getId()))
                .map(dto -> conversionService.convert(dto, Genre.class))
                .map(genreDao::update)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id " + genreDto.getId() + " not found!"));
        GenreDto updatedGenreDto = conversionService.convert(updatedGenre, GenreDto.class);
        log.debug("Updated genre: {}", updatedGenreDto);
        return updatedGenreDto;
    }

    @Override
    public boolean existsGenreById(long id) {
        boolean genreIsExist = genreDao.existsById(id);
        log.debug("Genre with id {} is exist: {}", id, genreIsExist);
        return genreIsExist;
    }

    @Override
    public GenreDto findGenreById(long id) {
        Genre genre = genreDao.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id " + id + " not found!"));
        GenreDto genreDto = conversionService.convert(genre, GenreDto.class);
        log.debug("Found genre: {}", genreDto);
        return genreDto;
    }

    @Override
    public List<GenreDto> findAllGenres() {
        List<Genre> genres = genreDao.findAll();
        List<GenreDto> genresDto = genres.stream()
                .map(genre -> conversionService.convert(genre, GenreDto.class))
                .collect(Collectors.toList());
        log.debug("Found {} genres", genresDto);
        return genresDto;
    }

    @Override
    public void deleteGenreById(long id) {
        genreDao.deleteById(id);
        log.debug("Genre with id {} deleted", id);
    }
}
