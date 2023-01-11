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
        AuthorDto authorDto = new AuthorDto(book.getAuthor().getId(), book.getAuthor().getFullName());
        GenreDto genreDto = new GenreDto(book.getGenre().getId(), book.getGenre().getName());
        List<CommentDto> comments = book.getComments().stream()
                .map(comment -> new CommentDto(comment.getId(), comment.getText()))
                .toList();
        return new BookDto(book.getId(), book.getTitle(), authorDto, genreDto, comments);
    }
}
