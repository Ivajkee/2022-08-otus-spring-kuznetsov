package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Genre;

@Component
public class GenreDtoToGenreConverter implements Converter<GenreDto, Genre> {
    @Override
    public Genre convert(GenreDto genreDto) {
        Genre genre = new Genre();
        genre.setName(genreDto.getName());
        return genre;
    }
}
