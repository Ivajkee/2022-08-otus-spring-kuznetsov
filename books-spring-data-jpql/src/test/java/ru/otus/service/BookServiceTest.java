package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.BookNotFoundException;
import ru.otus.exception.GenreNotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceTest {
    @Autowired
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private ConversionService conversionService;

    @DisplayName("Should return expected books count")
    @Test
    void shouldReturnExpectedBooksCount() {
        long expectedCount = 3;
        when(bookRepository.count()).thenReturn(expectedCount);
        long actualCount = bookService.getCountOfBooks();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("Should save book")
    @Test
    void shouldSaveBook() {
        long id = 1;
        BookDto bookDto = new BookDto("New book");
        Book book = new Book(bookDto.getTitle());
        Book savedBook = new Book(id, book.getTitle());
        BookDto expectedBookDto = new BookDto(id, savedBook.getTitle());
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(conversionService.convert(bookDto, Book.class)).thenReturn(book);
        when(conversionService.convert(savedBook, BookDto.class)).thenReturn(expectedBookDto);
        BookDto actualBookDto = bookService.saveBook(bookDto);
        assertThat(actualBookDto).isEqualTo(expectedBookDto);
    }

    @DisplayName("Should update book")
    @Test
    void shouldUpdateBook() {
        long id = 1;
        BookDto bookDto = new BookDto(id, "Edited book");
        Book book = new Book(id, bookDto.getTitle());
        BookDto expectedBookDto = new BookDto(id, book.getTitle());
        when(bookRepository.findByIdWithInfo(id)).thenReturn(Optional.of(book));
        when(conversionService.convert(book, BookDto.class)).thenReturn(expectedBookDto);
        BookDto actualBookDto = bookService.updateBook(bookDto);
        assertThat(actualBookDto).isEqualTo(expectedBookDto);
    }

    @DisplayName("Should throw exception when try update not existing book")
    @Test
    void shouldThrowExceptionWhenTryUpdateNotExistingBook() {
        long id = 1;
        BookDto bookDto = new BookDto(id, "Edited book");
        when(bookRepository.findByIdWithInfo(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.updateBook(bookDto)).isInstanceOf(BookNotFoundException.class);
    }

    @DisplayName("Should be exist book")
    @Test
    void shouldBeExistBook() {
        long id = 1;
        when(bookRepository.existsById(id)).thenReturn(true);
        boolean bookIsExist = bookService.existsBookById(id);
        assertThat(bookIsExist).isTrue();
    }

    @DisplayName("Should be not exist book")
    @Test
    void shouldBeNotExistBook() {
        long id = 1;
        when(bookRepository.existsById(id)).thenReturn(false);
        boolean bookIsExist = bookService.existsBookById(id);
        assertThat(bookIsExist).isFalse();
    }

    @DisplayName("Should find book by id")
    @Test
    void shouldFindBookById() {
        long id = 1;
        Book book = new Book(id, "Test book");
        BookDto expectedBookDto = new BookDto(id, book.getTitle());
        when(bookRepository.findByIdWithInfo(id)).thenReturn(Optional.of(book));
        when(conversionService.convert(book, BookDto.class)).thenReturn(expectedBookDto);
        BookDto actualBookDto = bookService.findBookById(id);
        assertThat(actualBookDto).isEqualTo(expectedBookDto);
    }

    @DisplayName("Should throw exception when try find not existing book by id")
    @Test
    void shouldThrowExceptionWhenTryFindNotExistingBookById() {
        long id = 1;
        when(bookRepository.findByIdWithInfo(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.findBookById(id)).isInstanceOf(BookNotFoundException.class);
    }

    @DisplayName("Should find book by title")
    @Test
    void shouldFindBookByTitle() {
        long id = 1;
        String title = "Test book";
        Book book = new Book(id, title);
        BookDto expectedBookDto = new BookDto(id, title);
        when(bookRepository.findByTitle(title)).thenReturn(Optional.of(book));
        when(conversionService.convert(book, BookDto.class)).thenReturn(expectedBookDto);
        BookDto actualBookDto = bookService.findBookByTitle(title);
        assertThat(actualBookDto).isEqualTo(expectedBookDto);
    }

    @DisplayName("Should throw exception when try find not existing book by title")
    @Test
    void shouldThrowExceptionWhenTryFindNotExistingBookByTitle() {
        String title = "Test book";
        when(bookRepository.findByTitle(title)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.findBookByTitle(title)).isInstanceOf(BookNotFoundException.class);
    }

    @DisplayName("Should find all books")
    @Test
    void shouldFindAllBooks() {
        Book book1 = new Book(1, "Test book 1");
        Book book2 = new Book(2, "Test book 2");
        Book book3 = new Book(3, "Test book 3");
        BookDto bookDto1 = new BookDto(book1.getId(), book1.getTitle());
        BookDto bookDto2 = new BookDto(book2.getId(), book2.getTitle());
        BookDto bookDto3 = new BookDto(book3.getId(), book3.getTitle());
        List<Book> books = List.of(book1, book2, book3);
        List<BookDto> expectedBookDtoList = List.of(bookDto1, bookDto2, bookDto3);
        when(bookRepository.findAll()).thenReturn(books);
        when(conversionService.convert(book1, BookDto.class)).thenReturn(bookDto1);
        when(conversionService.convert(book2, BookDto.class)).thenReturn(bookDto2);
        when(conversionService.convert(book3, BookDto.class)).thenReturn(bookDto3);
        List<BookDto> actualBookDtoList = bookService.findAllBooks();
        assertThat(actualBookDtoList).isEqualTo(expectedBookDtoList);
    }

    @DisplayName("Should delete book")
    @Test
    void shouldDeleteBook() {
        long id = 1;
        Book book = new Book(id, "Test book");
        when(bookRepository.findByIdWithInfo(id)).thenReturn(Optional.of(book));
        assertThatCode(() -> bookService.deleteBookById(1)).doesNotThrowAnyException();
    }

    @DisplayName("Should add author to book")
    @Test
    void shouldAddAuthorToBook() {
        long bookId = 1;
        long authorId = 1;
        Book book = new Book(bookId, "Test book");
        Author author = new Author(authorId, "Test author");
        when(bookRepository.findByIdWithInfo(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        bookService.addAuthorToBook(authorId, bookId);
        assertThat(book.getAuthors()).contains(author);
    }

    @DisplayName("Should throw exception when try add author to not existing book")
    @Test
    void shouldThrowExceptionWhenTryAddAuthorToNotExistingBook() {
        long bookId = 1;
        long authorId = 1;
        Book book = new Book(bookId, "Test book");
        Author author = new Author(authorId, "Test author");
        when(bookRepository.findByIdWithInfo(bookId)).thenReturn(Optional.empty());
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        assertThatThrownBy(() -> bookService.addAuthorToBook(authorId, bookId)).isInstanceOf(BookNotFoundException.class);
        assertThat(book.getAuthors()).doesNotContain(author);

    }

    @DisplayName("Should throw exception when try add not existing author to book")
    @Test
    void shouldThrowExceptionWhenTryAddNotExistingAuthorToBook() {
        long bookId = 1;
        long authorId = 1;
        Book book = new Book(bookId, "Test book");
        Author author = new Author(authorId, "Test author");
        when(bookRepository.findByIdWithInfo(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.addAuthorToBook(authorId, bookId)).isInstanceOf(AuthorNotFoundException.class);
        assertThat(book.getAuthors()).doesNotContain(author);
    }

    @DisplayName("Should delete author from book")
    @Test
    void shouldDeleteAuthorFromBook() {
        long bookId = 1;
        long authorId = 1;
        Book book = new Book(bookId, "Test book");
        Author author = new Author(authorId, "Test author");
        book.addAuthor(author);
        assertThat(book.getAuthors()).contains(author);
        when(bookRepository.findByIdWithInfo(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        bookService.deleteAuthorFromBook(authorId, bookId);
        assertThat(book.getAuthors()).doesNotContain(author);
    }

    @DisplayName("Should throw exception when try delete author from not existing book")
    @Test
    void shouldThrowExceptionWhenTryDeleteAuthorFromNotExistingBook() {
        long bookId = 1;
        long authorId = 1;
        Book book = new Book(bookId, "Test book");
        Author author = new Author(authorId, "Test author");
        book.addAuthor(author);
        assertThat(book.getAuthors()).contains(author);
        when(bookRepository.findByIdWithInfo(bookId)).thenReturn(Optional.empty());
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        assertThatThrownBy(() -> bookService.deleteAuthorFromBook(authorId, bookId)).isInstanceOf(BookNotFoundException.class);
        assertThat(book.getAuthors()).contains(author);
    }

    @DisplayName("Should throw exception when try delete not existing author from book")
    @Test
    void shouldThrowExceptionWhenTryDeleteNotExistingAuthorFromBook() {
        long bookId = 1;
        long authorId = 1;
        Book book = new Book(bookId, "Test book");
        Author author = new Author(authorId, "Test author");
        book.addAuthor(author);
        assertThat(book.getAuthors()).contains(author);
        when(bookRepository.findByIdWithInfo(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.deleteAuthorFromBook(authorId, bookId)).isInstanceOf(AuthorNotFoundException.class);
        assertThat(book.getAuthors()).contains(author);
    }

    @DisplayName("Should add genre to book")
    @Test
    void shouldAddGenreToBook() {
        long bookId = 1;
        long genreId = 1;
        Book book = new Book(bookId, "Test book");
        Genre genre = new Genre(genreId, "Test genre");
        when(bookRepository.findByIdWithInfo(bookId)).thenReturn(Optional.of(book));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        bookService.addGenreToBook(genreId, bookId);
        assertThat(book.getGenres()).contains(genre);
    }

    @DisplayName("Should throw exception when try add genre to not existing book")
    @Test
    void shouldThrowExceptionWhenTryAddGenreToNotExistingBook() {
        long bookId = 1;
        long genreId = 1;
        Book book = new Book(bookId, "Test book");
        Genre genre = new Genre(genreId, "Test genre");
        when(bookRepository.findByIdWithInfo(bookId)).thenReturn(Optional.empty());
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        assertThatThrownBy(() -> bookService.addGenreToBook(genreId, bookId)).isInstanceOf(BookNotFoundException.class);
        assertThat(book.getGenres()).doesNotContain(genre);

    }

    @DisplayName("Should throw exception when try add not existing genre to book")
    @Test
    void shouldThrowExceptionWhenTryAddNotExistingGenreToBook() {
        long bookId = 1;
        long genreId = 1;
        Book book = new Book(bookId, "Test book");
        Genre genre = new Genre(genreId, "Test genre");
        when(bookRepository.findByIdWithInfo(bookId)).thenReturn(Optional.of(book));
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.addGenreToBook(genreId, bookId)).isInstanceOf(GenreNotFoundException.class);
        assertThat(book.getGenres()).doesNotContain(genre);
    }

    @DisplayName("Should delete genre from book")
    @Test
    void shouldDeleteGenreFromBook() {
        long bookId = 1;
        long genreId = 1;
        Book book = new Book(bookId, "Test book");
        Genre genre = new Genre(genreId, "Test genre");
        book.addGenre(genre);
        assertThat(book.getGenres()).contains(genre);
        when(bookRepository.findByIdWithInfo(bookId)).thenReturn(Optional.of(book));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        bookService.deleteGenreFromBook(genreId, bookId);
        assertThat(book.getGenres()).doesNotContain(genre);
    }

    @DisplayName("Should throw exception when try delete genre from not existing book")
    @Test
    void shouldThrowExceptionWhenTryDeleteGenreFromNotExistingBook() {
        long bookId = 1;
        long genreId = 1;
        Book book = new Book(bookId, "Test book");
        Genre genre = new Genre(genreId, "Test genre");
        book.addGenre(genre);
        assertThat(book.getGenres()).contains(genre);
        when(bookRepository.findByIdWithInfo(bookId)).thenReturn(Optional.empty());
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        assertThatThrownBy(() -> bookService.deleteGenreFromBook(genreId, bookId)).isInstanceOf(BookNotFoundException.class);
        assertThat(book.getGenres()).contains(genre);
    }

    @DisplayName("Should throw exception when try delete not existing genre from book")
    @Test
    void shouldThrowExceptionWhenTryDeleteNotExistingGenreFromBook() {
        long bookId = 1;
        long genreId = 1;
        Book book = new Book(bookId, "Test book");
        Genre genre = new Genre(genreId, "Test genre");
        book.addGenre(genre);
        assertThat(book.getGenres()).contains(genre);
        when(bookRepository.findByIdWithInfo(bookId)).thenReturn(Optional.of(book));
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.deleteGenreFromBook(genreId, bookId)).isInstanceOf(GenreNotFoundException.class);
        assertThat(book.getGenres()).contains(genre);
    }
}