package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.model.Author;

@Component
public class AuthorDtoToAuthorConverter implements Converter<AuthorDto, Author> {
    @Override
    public Author convert(AuthorDto authorDto) {
        return new Author(authorDto.getId(), authorDto.getFullName());
    }
}
