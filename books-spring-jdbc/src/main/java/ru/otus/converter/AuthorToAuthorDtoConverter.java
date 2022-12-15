package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Author;

import java.util.stream.Collectors;

@Component
public class AuthorToAuthorDtoConverter implements Converter<Author, AuthorDto> {
    @Override
    public AuthorDto convert(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        if (author.getBooks() != null) {
            authorDto.setBooks(author.getBooks().stream()
                    .map(book -> {
                        GenreDto genreDto = new GenreDto();
                        genreDto.setId(book.getGenre().getId());
                        genreDto.setName(book.getGenre().getName());
                        BookDto bookDto = new BookDto();
                        bookDto.setId(book.getId());
                        bookDto.setTitle(book.getTitle());
                        bookDto.setGenre(genreDto);
                        return bookDto;
                    })
                    .collect(Collectors.toList()));
        }
        return authorDto;
    }
}
