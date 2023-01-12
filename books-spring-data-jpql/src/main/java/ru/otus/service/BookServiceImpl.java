package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.dto.CommentDto;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.BookNotFoundException;
import ru.otus.exception.CommentNotFoundException;
import ru.otus.exception.GenreNotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;
    private final ConversionService conversionService;

    @Override
    public long getCountOfBooks() {
        long count = bookRepository.count();
        log.debug("Found {} books", count);
        return count;
    }

    @Transactional
    @Override
    public BookDto saveBook(BookDto bookDto) {
        Book book = conversionService.convert(bookDto, Book.class);
        Book savedBook = bookRepository.save(book);
        BookDto savedBookDto = conversionService.convert(savedBook, BookDto.class);
        log.debug("Saved book: {}", savedBookDto);
        return savedBookDto;
    }

    @Transactional
    @Override
    public BookDto updateBook(BookDto bookDto) {
        BookDto updatedBookDto = bookRepository.findById(bookDto.getId()).map(book -> {
            book.setTitle(bookDto.getTitle());
            return conversionService.convert(book, BookDto.class);
        }).orElseThrow(() -> new BookNotFoundException(bookDto.getId()));
        log.debug("Updated book: {}", updatedBookDto);
        return updatedBookDto;
    }

    @Override
    public boolean existsBookById(long id) {
        boolean bookIsExist = bookRepository.existsById(id);
        log.debug("Book with id {} is exist: {}", id, bookIsExist);
        return bookIsExist;
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto findBookById(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        BookDto bookDto = conversionService.convert(book, BookDto.class);
        log.debug("Found book: {}", bookDto);
        return bookDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> booksDto = books.stream()
                .map(book -> conversionService.convert(book, BookDto.class))
                .collect(Collectors.toList());
        log.debug("Found books: {}", booksDto);
        return booksDto;
    }

    @Transactional
    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
        log.debug("Book with id {} deleted", id);
    }

    @Transactional
    @Override
    public BookDto addAuthorToBook(long bookId, long authorId) {
        BookDto bookDto = bookRepository.findById(bookId).map(book -> {
            authorRepository.findById(authorId).ifPresentOrElse(author -> {
                if (!book.getAuthors().contains(author)) {
                    book.addAuthor(author);
                }
            }, () -> {
                throw new AuthorNotFoundException(authorId);
            });
            return conversionService.convert(book, BookDto.class);
        }).orElseThrow(() -> new BookNotFoundException(bookId));
        log.debug("Updated book: {}", bookDto);
        return bookDto;
    }

    @Transactional
    @Override
    public BookDto deleteAuthorFromBook(long bookId, long authorId) {
        BookDto bookDto = bookRepository.findById(bookId).map(book -> {
            authorRepository.findById(authorId).ifPresentOrElse(book::deleteAuthor, () -> {
                throw new AuthorNotFoundException(authorId);
            });
            return conversionService.convert(book, BookDto.class);
        }).orElseThrow(() -> new BookNotFoundException(bookId));
        log.debug("Updated book: {}", bookDto);
        return bookDto;
    }

    @Transactional
    @Override
    public BookDto addGenreToBook(long bookId, long genreId) {
        BookDto bookDto = bookRepository.findById(bookId).map(book -> {
            genreRepository.findById(genreId).ifPresentOrElse(genre -> {
                if (!book.getGenres().contains(genre)) {
                    book.addGenre(genre);
                }
            }, () -> {
                throw new GenreNotFoundException(genreId);
            });
            return conversionService.convert(book, BookDto.class);
        }).orElseThrow(() -> new BookNotFoundException(bookId));
        log.debug("Updated book: {}", bookDto);
        return bookDto;
    }

    @Transactional
    @Override
    public BookDto deleteGenreFromBook(long bookId, long genreId) {
        BookDto bookDto = bookRepository.findById(bookId).map(book -> {
            genreRepository.findById(genreId).ifPresentOrElse(book::deleteGenre, () -> {
                throw new GenreNotFoundException(genreId);
            });
            return conversionService.convert(book, BookDto.class);
        }).orElseThrow(() -> new BookNotFoundException(bookId));
        log.debug("Updated book: {}", bookDto);
        return bookDto;
    }

    @Transactional
    @Override
    public BookDto addCommentToBook(long bookId, CommentDto commentDto) {
        BookDto bookDto = bookRepository.findById(bookId).map(book -> {
            Comment comment = conversionService.convert(commentDto, Comment.class);
            book.addComment(comment);
            return conversionService.convert(book, BookDto.class);
        }).orElseThrow(() -> new BookNotFoundException(bookId));
        log.debug("Updated book: {}", bookDto);
        return bookDto;
    }

    @Transactional
    @Override
    public BookDto deleteCommentFromBook(long bookId, long commentId) {
        BookDto bookDto = bookRepository.findById(bookId).map(book -> {
            commentRepository.findById(commentId).ifPresentOrElse(book::deleteComment, () -> {
                throw new CommentNotFoundException(commentId);
            });
            return conversionService.convert(book, BookDto.class);
        }).orElseThrow(() -> new BookNotFoundException(bookId));
        log.debug("Updated book: {}", bookDto);
        return bookDto;
    }
}
