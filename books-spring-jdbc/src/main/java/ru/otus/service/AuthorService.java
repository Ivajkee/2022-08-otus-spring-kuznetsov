package ru.otus.service;

import ru.otus.domain.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    long getCountOfAuthors();

    AuthorDto saveAuthor(AuthorDto authorDto);

    AuthorDto findAuthorById(long id);

    List<AuthorDto> findAllAuthors();

    void deleteAuthorById(long id);
}
