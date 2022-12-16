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
        Author author = new Author();
        author.setId(bookDto.getAuthor().getId());
        author.setName(bookDto.getAuthor().getName());
        Genre genre = new Genre();
        genre.setId(bookDto.getGenre().getId());
        genre.setName(bookDto.getGenre().getName());
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(author);
        book.setGenre(genre);
        return book;
    }
}
