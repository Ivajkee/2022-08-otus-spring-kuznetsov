package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Genre;
import ru.otus.exception.GenreNotFoundException;
import ru.otus.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final ConversionService conversionService;

    @Override
    public long getCountOfGenres() {
        long count = genreRepository.count();
        log.debug("Found {} genres", count);
        return count;
    }

    @Transactional
    @Override
    public GenreDto saveGenre(GenreDto genreDto) {
        Genre genre = conversionService.convert(genreDto, Genre.class);
        Genre savedGenre = genreRepository.save(genre);
        GenreDto savedGenreDto = conversionService.convert(savedGenre, GenreDto.class);
        log.debug("Saved genre: {}", savedGenreDto);
        return savedGenreDto;
    }

    @Transactional
    @Override
    public GenreDto updateGenre(GenreDto genreDto) {
        GenreDto updatedGenreDto = genreRepository.findById(genreDto.getId()).map(genre -> {
            genre.setName(genreDto.getName());
            return conversionService.convert(genre, GenreDto.class);
        }).orElseThrow(() -> new GenreNotFoundException(genreDto.getId()));
        log.debug("Updated genre: {}", updatedGenreDto);
        return updatedGenreDto;
    }

    @Override
    public boolean existsGenreById(long id) {
        boolean genreIsExist = genreRepository.existsById(id);
        log.debug("Genre with id {} is exist: {}", id, genreIsExist);
        return genreIsExist;
    }

    @Transactional(readOnly = true)
    @Override
    public GenreDto findGenreById(long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException(id));
        GenreDto genreDto = conversionService.convert(genre, GenreDto.class);
        log.debug("Found genre: {}", genreDto);
        return genreDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> findAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        List<GenreDto> genresDto = genres.stream()
                .map(genre -> conversionService.convert(genre, GenreDto.class))
                .collect(Collectors.toList());
        log.debug("Found {} genres", genresDto);
        return genresDto;
    }

    @Transactional
    @Override
    public void deleteGenreById(long id) {
        genreRepository.findById(id).ifPresentOrElse(genre -> {
            genre.getBooks().forEach(book -> book.deleteGenre(genre));
            genreRepository.delete(genre);
        }, () -> {
            throw new GenreNotFoundException(id);
        });
        log.debug("Genre with id {} deleted", id);
    }
}
