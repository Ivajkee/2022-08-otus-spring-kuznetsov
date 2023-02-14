package ru.otus.service;

import ru.otus.domain.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    long getCountOfAuthors();

    AuthorDto saveAuthor(AuthorDto authorDto);

    AuthorDto updateAuthor(AuthorDto authorDto);

    boolean existsAuthorById(String id);

    AuthorDto findAuthorById(String id);

    AuthorDto findAuthorByFullName(String fullName);

    List<AuthorDto> findAllAuthors();

    void deleteAuthorById(String id);
}
