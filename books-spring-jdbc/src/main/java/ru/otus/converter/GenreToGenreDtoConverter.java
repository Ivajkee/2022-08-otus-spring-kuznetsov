package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Genre;

import java.util.stream.Collectors;

@Component
public class GenreToGenreDtoConverter implements Converter<Genre, GenreDto> {
    @Override
    public GenreDto convert(Genre genre) {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        if (genre.getBooks() != null) {
            genreDto.setBooks(genre.getBooks().stream()
                    .map(book -> {
                        AuthorDto authorDto = new AuthorDto();
                        authorDto.setId(book.getAuthor().getId());
                        authorDto.setFullName(book.getAuthor().getFullName());
                        BookDto bookDto = new BookDto();
                        bookDto.setId(book.getId());
                        bookDto.setTitle(book.getTitle());
                        bookDto.setAuthor(authorDto);
                        return bookDto;
                    })
                    .collect(Collectors.toList()));
        }
        return genreDto;
    }
}
