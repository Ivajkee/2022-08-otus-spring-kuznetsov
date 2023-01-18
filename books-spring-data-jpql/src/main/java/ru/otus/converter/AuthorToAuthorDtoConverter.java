package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.model.Author;

@Component
public class AuthorToAuthorDtoConverter implements Converter<Author, AuthorDto> {
    @Override
    public AuthorDto convert(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }
}
