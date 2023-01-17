package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.dto.CommentDto;
import ru.otus.domain.model.Comment;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.BookNotFoundException;
import ru.otus.exception.GenreNotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final ConversionService conversionService;

    @Transactional
    @Override
    public void addAuthorToBook(long authorId, long bookId) {
        bookRepository.findById(bookId).ifPresentOrElse(book -> authorRepository.findById(authorId)
                .ifPresentOrElse(book::addAuthor, () -> {
                    throw new AuthorNotFoundException(authorId);
                }), () -> {
            throw new BookNotFoundException(bookId);
        });
        log.debug("Author with id {} added to book with id {}", authorId, bookId);
    }

    @Transactional
    @Override
    public void deleteAuthorFromBook(long authorId, long bookId) {
        bookRepository.findById(bookId).ifPresentOrElse(book -> authorRepository.findById(authorId)
                .ifPresentOrElse(book::deleteAuthor, () -> {
                    throw new AuthorNotFoundException(authorId);
                }), () -> {
            throw new BookNotFoundException(bookId);
        });
        log.debug("Author with id {} deleted from book with id {}", authorId, bookId);
    }

    @Transactional
    @Override
    public void addGenreToBook(long genreId, long bookId) {
        bookRepository.findById(bookId).ifPresentOrElse(book -> genreRepository.findById(genreId)
                .ifPresentOrElse(book::addGenre, () -> {
                    throw new GenreNotFoundException(genreId);
                }), () -> {
            throw new BookNotFoundException(bookId);
        });
        log.debug("Genre with id {} added to book with id {}", genreId, bookId);
    }

    @Transactional
    @Override
    public void deleteGenreFromBook(long genreId, long bookId) {
        bookRepository.findById(bookId).ifPresentOrElse(book -> genreRepository.findById(genreId)
                .ifPresentOrElse(book::deleteGenre, () -> {
                    throw new GenreNotFoundException(genreId);
                }), () -> {
            throw new BookNotFoundException(bookId);
        });
        log.debug("Genre with id {} deleted from book with id {}", genreId, bookId);
    }

    @Transactional
    @Override
    public void addCommentToBook(long bookId, CommentDto commentDto) {
        Comment comment = conversionService.convert(commentDto, Comment.class);
        bookRepository.findById(bookId).ifPresentOrElse(book -> book.addComment(Objects.requireNonNull(comment)), () -> {
            throw new BookNotFoundException(bookId);
        });
        log.debug("Comment with text '{}' added to book with id {}", commentDto.getText(), bookId);
    }
}
