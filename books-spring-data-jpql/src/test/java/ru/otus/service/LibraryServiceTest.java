package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import ru.otus.domain.dto.CommentDto;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.domain.model.Genre;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.exception.BookNotFoundException;
import ru.otus.exception.GenreNotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = LibraryServiceImpl.class)
class LibraryServiceTest {
    @Autowired
    private LibraryService libraryService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private ConversionService conversionService;

    @DisplayName("Should add author to book")
    @Test
    void shouldAddAuthorToBook() {
        long bookId = 1;
        long authorId = 1;
        Book book = new Book(bookId, "Test book");
        Author author = new Author(authorId, "Test author");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        libraryService.addAuthorToBook(authorId, bookId);
        assertThat(book.getAuthors()).contains(author);
    }

    @DisplayName("Should throw exception when try add author to not existing book")
    @Test
    void shouldThrowExceptionWhenTryAddAuthorToNotExistingBook() {
        long bookId = 1;
        long authorId = 1;
        Book book = new Book(bookId, "Test book");
        Author author = new Author(authorId, "Test author");
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        assertThatThrownBy(() -> libraryService.addAuthorToBook(authorId, bookId)).isInstanceOf(BookNotFoundException.class);
        assertThat(book.getAuthors()).doesNotContain(author);

    }

    @DisplayName("Should throw exception when try add not existing author to book")
    @Test
    void shouldThrowExceptionWhenTryAddNotExistingAuthorToBook() {
        long bookId = 1;
        long authorId = 1;
        Book book = new Book(bookId, "Test book");
        Author author = new Author(authorId, "Test author");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> libraryService.addAuthorToBook(authorId, bookId)).isInstanceOf(AuthorNotFoundException.class);
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
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        libraryService.deleteAuthorFromBook(authorId, bookId);
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
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        assertThatThrownBy(() -> libraryService.deleteAuthorFromBook(authorId, bookId)).isInstanceOf(BookNotFoundException.class);
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
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> libraryService.deleteAuthorFromBook(authorId, bookId)).isInstanceOf(AuthorNotFoundException.class);
        assertThat(book.getAuthors()).contains(author);
    }

    @DisplayName("Should add genre to book")
    @Test
    void shouldAddGenreToBook() {
        long bookId = 1;
        long genreId = 1;
        Book book = new Book(bookId, "Test book");
        Genre genre = new Genre(genreId, "Test genre");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        libraryService.addGenreToBook(genreId, bookId);
        assertThat(book.getGenres()).contains(genre);
        assertThat(genre.getBooks()).contains(book);
    }

    @DisplayName("Should throw exception when try add genre to not existing book")
    @Test
    void shouldThrowExceptionWhenTryAddGenreToNotExistingBook() {
        long bookId = 1;
        long genreId = 1;
        Book book = new Book(bookId, "Test book");
        Genre genre = new Genre(genreId, "Test genre");
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        assertThatThrownBy(() -> libraryService.addGenreToBook(genreId, bookId)).isInstanceOf(BookNotFoundException.class);
        assertThat(book.getGenres()).doesNotContain(genre);
        assertThat(genre.getBooks()).doesNotContain(book);

    }

    @DisplayName("Should throw exception when try add not existing genre to book")
    @Test
    void shouldThrowExceptionWhenTryAddNotExistingGenreToBook() {
        long bookId = 1;
        long genreId = 1;
        Book book = new Book(bookId, "Test book");
        Genre genre = new Genre(genreId, "Test genre");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> libraryService.addGenreToBook(genreId, bookId)).isInstanceOf(GenreNotFoundException.class);
        assertThat(book.getGenres()).doesNotContain(genre);
        assertThat(genre.getBooks()).doesNotContain(book);
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
        assertThat(genre.getBooks()).contains(book);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        libraryService.deleteGenreFromBook(genreId, bookId);
        assertThat(book.getGenres()).doesNotContain(genre);
        assertThat(genre.getBooks()).doesNotContain(book);
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
        assertThat(genre.getBooks()).contains(book);
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        assertThatThrownBy(() -> libraryService.deleteGenreFromBook(genreId, bookId)).isInstanceOf(BookNotFoundException.class);
        assertThat(book.getGenres()).contains(genre);
        assertThat(genre.getBooks()).contains(book);
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
        assertThat(genre.getBooks()).contains(book);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> libraryService.deleteGenreFromBook(genreId, bookId)).isInstanceOf(GenreNotFoundException.class);
        assertThat(book.getGenres()).contains(genre);
        assertThat(genre.getBooks()).contains(book);
    }

    @DisplayName("Should add comment to book")
    @Test
    void shouldAddCommentToBook() {
        long bookId = 1;
        Book book = new Book(bookId, "Test book");
        CommentDto commentDto = new CommentDto("comment");
        Comment comment = new Comment(commentDto.getText());
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(conversionService.convert(commentDto, Comment.class)).thenReturn(comment);
        libraryService.addCommentToBook(bookId, commentDto);
        assertThat(book.getComments()).contains(comment);
    }
}