package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.dto.CommentDto;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Book;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookToBookDtoConverter implements Converter<Book, BookDto> {
    @Override
    public BookDto convert(Book book) {
        Set<AuthorDto> authors = book.getAuthors().stream()
                .map(author -> new AuthorDto(author.getId(), author.getFullName()))
                .sorted(Comparator.comparing(AuthorDto::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        Set<GenreDto> genres = book.getGenres().stream()
                .map(genre -> new GenreDto(genre.getId(), genre.getName()))
                .sorted(Comparator.comparing(GenreDto::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        List<CommentDto> comments = book.getComments().stream()
                .map(comment -> new CommentDto(comment.getId(), comment.getText()))
                .toList();
        return new BookDto(book.getId(), book.getTitle(), authors, genres, comments);
    }
}
