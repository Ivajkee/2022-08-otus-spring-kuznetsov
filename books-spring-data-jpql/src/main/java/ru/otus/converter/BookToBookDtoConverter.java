package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.dto.CommentDto;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Book;

import java.util.List;

@Component
public class BookToBookDtoConverter implements Converter<Book, BookDto> {
    @Override
    public BookDto convert(Book book) {
        List<AuthorDto> authors = book.getAuthors().stream()
                .map(author -> new AuthorDto(author.getId(), author.getFullName()))
                .toList();
        List<GenreDto> genres = book.getGenres().stream()
                .map(genre -> new GenreDto(genre.getId(), genre.getName()))
                .toList();
        List<CommentDto> comments = book.getComments().stream()
                .map(comment -> new CommentDto(comment.getId(), comment.getText()))
                .toList();
        return new BookDto(book.getId(), book.getTitle(), authors, genres, comments);
    }
}
