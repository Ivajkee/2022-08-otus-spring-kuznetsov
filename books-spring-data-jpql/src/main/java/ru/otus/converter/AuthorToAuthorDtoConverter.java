package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.model.Author;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorToAuthorDtoConverter implements Converter<Author, AuthorDto> {
    @Override
    public AuthorDto convert(Author author) {
        Set<BookDto> books = author.getBooks().stream()
                .map(book -> new BookDto(book.getId(), book.getTitle()))
                .sorted(Comparator.comparing(BookDto::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return new AuthorDto(author.getId(), author.getFullName(), books);
    }
}
