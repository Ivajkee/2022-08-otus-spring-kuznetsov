package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

@Component
public class BookDtoToBookConverter implements Converter<BookDto, Book> {
    @Override
    public Book convert(BookDto bookDto) {
        Author author = new Author(bookDto.getAuthor().getId(), bookDto.getAuthor().getFullName());
        Genre genre = new Genre(bookDto.getGenre().getId(), bookDto.getGenre().getName());
        return new Book(bookDto.getId(), bookDto.getTitle(), author, genre);
    }
}
