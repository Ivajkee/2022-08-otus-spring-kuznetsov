package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.model.Author;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;

    @Override
    public long getCountOfAuthors() {
        long count = authorRepository.count();
        log.debug("Found {} authors", count);
        return count;
    }

    @Transactional
    @Override
    public AuthorDto saveAuthor(AuthorDto authorDto) {
        Author author = conversionService.convert(authorDto, Author.class);
        Author savedAuthor = authorRepository.save(author);
        AuthorDto savedAuthorDto = conversionService.convert(savedAuthor, AuthorDto.class);
        log.debug("Saved author: {}", savedAuthorDto);
        return savedAuthorDto;
    }

    @Transactional
    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto) {
        Author updatedAuthor = Optional.of(authorDto)
                .filter(dto -> authorRepository.existsById(dto.getId()))
                .map(dto -> conversionService.convert(dto, Author.class))
                .map(authorRepository::update)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id " + authorDto.getId() + " not found!"));
        AuthorDto updatedAuthorDto = conversionService.convert(updatedAuthor, AuthorDto.class);
        log.debug("Updated author: {}", updatedAuthorDto);
        return updatedAuthorDto;
    }

    @Override
    public boolean existsAuthorById(long id) {
        boolean authorIsExist = authorRepository.existsById(id);
        log.debug("Author with id {} is exist: {}", id, authorIsExist);
        return authorIsExist;
    }

    @Override
    public AuthorDto findAuthorById(long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id " + id + " not found!"));
        AuthorDto authorDto = conversionService.convert(author, AuthorDto.class);
        log.debug("Found author: {}", authorDto);
        return authorDto;
    }

    @Override
    public List<AuthorDto> findAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> authorsDto = authors.stream()
                .map(author -> conversionService.convert(author, AuthorDto.class))
                .collect(Collectors.toList());
        log.debug("Found authors: {}", authorsDto);
        return authorsDto;
    }

    @Transactional
    @Override
    public void deleteAuthorById(long id) {
        authorRepository.deleteById(id);
        log.debug("Author with id {} deleted", id);
    }
}
