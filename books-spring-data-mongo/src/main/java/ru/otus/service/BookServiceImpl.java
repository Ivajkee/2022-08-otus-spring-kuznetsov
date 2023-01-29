package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.BookNotFoundException;
import ru.otus.exception.GenreNotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.sequence.SequenceGeneratorService;

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
    private final SequenceGeneratorService sequenceGeneratorService;
    private final ConversionService conversionService;

    @Override
    public long getCountOfBooks() {
        long count = bookRepository.count();
        log.debug("Found {} books", count);
        return count;
    }

    @Override
    public BookDto saveBook(BookDto bookDto) {
        Book book = conversionService.convert(bookDto, Book.class);
        book.setId(sequenceGeneratorService.generate(Book.SEQUENCE_NAME));
        Book savedBook = bookRepository.save(book);
        BookDto savedBookDto = conversionService.convert(savedBook, BookDto.class);
        log.debug("Saved book: {}", savedBookDto);
        return savedBookDto;
    }

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

    @Override
    public BookDto findBookById(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        BookDto bookDto = conversionService.convert(book, BookDto.class);
        log.debug("Found book: {}", bookDto);
        return bookDto;
    }

    @Override
    public BookDto findBookByTitle(String title) {
        Book book = bookRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new BookNotFoundException(title));
        BookDto bookDto = conversionService.convert(book, BookDto.class);
        log.debug("Found book: {}", bookDto);
        return bookDto;
    }

    @Override
    public List<BookDto> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> booksDto = books.stream()
                .map(book -> conversionService.convert(book, BookDto.class))
                .collect(Collectors.toList());
        log.debug("Found books: {}", booksDto);
        return booksDto;
    }

    @Override
    public List<BookDto> findAllBooksByAuthor(long authorId) {
        List<BookDto> booksDto = authorRepository.findById(authorId).map(author -> {
            List<Book> books = bookRepository.findAllByAuthors(author);
            return books.stream()
                    .map(book -> conversionService.convert(book, BookDto.class))
                    .collect(Collectors.toList());
        }).orElseThrow(() -> new AuthorNotFoundException(authorId));
        log.debug("Found books: {}", booksDto);
        return booksDto;
    }

    @Override
    public List<BookDto> findAllBooksByGenre(long genreId) {
        List<BookDto> booksDto = genreRepository.findById(genreId).map(genre -> {
            List<Book> books = bookRepository.findAllByGenres(genre);
            return books.stream()
                    .map(book -> conversionService.convert(book, BookDto.class))
                    .collect(Collectors.toList());
        }).orElseThrow(() -> new GenreNotFoundException(genreId));
        log.debug("Found books: {}", booksDto);
        return booksDto;
    }

    @Override
    public void deleteBookById(long id) {
        bookRepository.findById(id).ifPresentOrElse(book -> {
            List<Comment> comments = commentRepository.findAllByBook(book);
            commentRepository.deleteAll(comments);
            bookRepository.delete(book);
        }, () -> {
            throw new BookNotFoundException(id);
        });
        log.debug("Book with id {} deleted", id);
    }

    @Override
    public void addAuthorToBook(long authorId, long bookId) {
        bookRepository.findById(bookId).ifPresentOrElse(book -> authorRepository.findById(authorId)
                .ifPresentOrElse(author -> {
                    book.addAuthor(author);
                    bookRepository.save(book);
                }, () -> {
                    throw new AuthorNotFoundException(authorId);
                }), () -> {
            throw new BookNotFoundException(bookId);
        });
        log.debug("Author with id {} added to book with id {}", authorId, bookId);
    }

    @Override
    public void deleteAuthorFromBook(long authorId, long bookId) {
        bookRepository.findById(bookId).ifPresentOrElse(book -> authorRepository.findById(authorId)
                .ifPresentOrElse(author -> {
                    book.deleteAuthor(author);
                    bookRepository.save(book);
                }, () -> {
                    throw new AuthorNotFoundException(authorId);
                }), () -> {
            throw new BookNotFoundException(bookId);
        });
        log.debug("Author with id {} deleted from book with id {}", authorId, bookId);
    }

    @Override
    public void addGenreToBook(long genreId, long bookId) {
        bookRepository.findById(bookId).ifPresentOrElse(book -> genreRepository.findById(genreId)
                .ifPresentOrElse(genre -> {
                    book.addGenre(genre);
                    bookRepository.save(book);
                }, () -> {
                    throw new GenreNotFoundException(genreId);
                }), () -> {
            throw new BookNotFoundException(bookId);
        });
        log.debug("Genre with id {} added to book with id {}", genreId, bookId);
    }

    @Override
    public void deleteGenreFromBook(long genreId, long bookId) {
        bookRepository.findById(bookId).ifPresentOrElse(book -> genreRepository.findById(genreId)
                .ifPresentOrElse(genre -> {
                    book.deleteGenre(genre);
                    bookRepository.save(book);
                }, () -> {
                    throw new GenreNotFoundException(genreId);
                }), () -> {
            throw new BookNotFoundException(bookId);
        });
        log.debug("Genre with id {} deleted from book with id {}", genreId, bookId);
    }
}
