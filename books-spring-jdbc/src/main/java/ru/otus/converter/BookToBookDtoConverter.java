package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Book;

@Component
public class BookToBookDtoConverter implements Converter<Book, BookDto> {
    @Override
    public BookDto convert(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        if (book.getAuthor() != null) {
            AuthorDto authorDto = new AuthorDto();
            authorDto.setId(book.getAuthor().getId());
            authorDto.setName(book.getAuthor().getName());
            bookDto.setAuthor(authorDto);
        }
        if (book.getGenre() != null) {
            GenreDto genreDto = new GenreDto();
            genreDto.setId(book.getGenre().getId());
            genreDto.setName(book.getGenre().getName());
            bookDto.setGenre(genreDto);
        }
        return bookDto;
    }
}
