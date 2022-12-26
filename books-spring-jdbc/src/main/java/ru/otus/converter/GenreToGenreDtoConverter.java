package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Genre;

@Component
public class GenreToGenreDtoConverter implements Converter<Genre, GenreDto> {
    @Override
    public GenreDto convert(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
