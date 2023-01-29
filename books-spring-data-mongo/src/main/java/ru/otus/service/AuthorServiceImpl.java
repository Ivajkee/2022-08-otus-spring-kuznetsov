package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.model.Author;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.service.sequence.SequenceGeneratorService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final ConversionService conversionService;

    @Override
    public long getCountOfAuthors() {
        long count = authorRepository.count();
        log.debug("Found {} authors", count);
        return count;
    }

    @Override
    public AuthorDto saveAuthor(AuthorDto authorDto) {
        Author author = conversionService.convert(authorDto, Author.class);
        author.setId(sequenceGeneratorService.generate(Author.SEQUENCE_NAME));
        Author savedAuthor = authorRepository.save(author);
        AuthorDto savedAuthorDto = conversionService.convert(savedAuthor, AuthorDto.class);
        log.debug("Saved author: {}", savedAuthorDto);
        return savedAuthorDto;
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto) {
        AuthorDto updatedAuthorDto = authorRepository.findById(authorDto.getId()).map(author -> {
            author.setFullName(authorDto.getFullName());
            Author updatedAuthor = authorRepository.save(author);
            return conversionService.convert(updatedAuthor, AuthorDto.class);
        }).orElseThrow(() -> new AuthorNotFoundException(authorDto.getId()));
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
                .orElseThrow(() -> new AuthorNotFoundException(id));
        AuthorDto authorDto = conversionService.convert(author, AuthorDto.class);
        log.debug("Found author: {}", authorDto);
        return authorDto;
    }

    @Override
    public AuthorDto findAuthorByFullName(String fullName) {
        Author author = authorRepository.findByFullNameIgnoreCase(fullName)
                .orElseThrow(() -> new AuthorNotFoundException(fullName));
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

    @Override
    public void deleteAuthorById(long id) {
        authorRepository.findById(id).ifPresentOrElse(author -> {
            bookRepository.findAllByAuthors(author).forEach(book -> {
                book.deleteAuthor(author);
                bookRepository.save(book);
            });
            authorRepository.delete(author);
        }, () -> {
            throw new AuthorNotFoundException(id);
        });
        log.debug("Author with id {} deleted", id);
    }
}
