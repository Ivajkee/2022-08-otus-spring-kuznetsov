package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Book;
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
    private final MongoTemplate mongoTemplate;
    private final ConversionService conversionService;

    @Override
    public long getCountOfGenres() {
        long count = genreRepository.count();
        log.debug("Found {} genres", count);
        return count;
    }

    @Override
    public GenreDto saveGenre(GenreDto genreDto) {
        Genre genre = conversionService.convert(genreDto, Genre.class);
        Genre savedGenre = genreRepository.save(genre);
        GenreDto savedGenreDto = conversionService.convert(savedGenre, GenreDto.class);
        log.debug("Saved genre: {}", savedGenreDto);
        return savedGenreDto;
    }

    @Override
    public GenreDto updateGenre(GenreDto genreDto) {
        GenreDto updatedGenreDto = genreRepository.findById(genreDto.getId()).map(genre -> {
            genre.setName(genreDto.getName());
            Genre updatedGenre = genreRepository.save(genre);
            return conversionService.convert(updatedGenre, GenreDto.class);
        }).orElseThrow(() -> new GenreNotFoundException(genreDto.getId()));
        log.debug("Updated genre: {}", updatedGenreDto);
        return updatedGenreDto;
    }

    @Override
    public boolean existsGenreById(String id) {
        boolean genreIsExist = genreRepository.existsById(id);
        log.debug("Genre with id {} is exist: {}", id, genreIsExist);
        return genreIsExist;
    }

    @Override
    public GenreDto findGenreById(String id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException(id));
        GenreDto genreDto = conversionService.convert(genre, GenreDto.class);
        log.debug("Found genre: {}", genreDto);
        return genreDto;
    }

    @Override
    public GenreDto findGenreByName(String name) {
        Genre genre = genreRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new GenreNotFoundException(name));
        GenreDto genreDto = conversionService.convert(genre, GenreDto.class);
        log.debug("Found genre: {}", genreDto);
        return genreDto;
    }

    @Override
    public List<GenreDto> findAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        List<GenreDto> genresDto = genres.stream()
                .map(genre -> conversionService.convert(genre, GenreDto.class))
                .collect(Collectors.toList());
        log.debug("Found {} genres", genresDto);
        return genresDto;
    }

    @Override
    public void deleteGenreById(String id) {
        Query query = Query.query(Criteria.where("$id").is(new ObjectId(id)));
        Update update = new Update().pull("genres", query);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
        genreRepository.deleteById(id);
        log.debug("Genre with id {} deleted", id);
    }
}
