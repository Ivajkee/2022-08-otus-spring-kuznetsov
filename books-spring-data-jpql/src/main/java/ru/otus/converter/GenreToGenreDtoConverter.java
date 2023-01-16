package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Genre;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenreToGenreDtoConverter implements Converter<Genre, GenreDto> {
    @Override
    public GenreDto convert(Genre genre) {
        Set<BookDto> books = genre.getBooks().stream()
                .map(book -> new BookDto(book.getId(), book.getTitle()))
                .sorted(Comparator.comparing(BookDto::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return new GenreDto(genre.getId(), genre.getName(), books);
    }
}
