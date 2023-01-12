package otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import ru.otus.config.ConversionServiceConfig;
import ru.otus.converter.BookDtoToBookConverter;
import ru.otus.converter.BookToBookDtoConverter;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;
import ru.otus.exception.BookNotFoundException;
import ru.otus.repository.BookRepository;
import ru.otus.service.BookService;
import ru.otus.service.BookServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {BookServiceImpl.class, ConversionServiceConfig.class, BookDtoToBookConverter.class,
        BookToBookDtoConverter.class})
class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private ConversionService conversionService;
    @MockBean
    private BookRepository bookRepository;

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
        AuthorDto authorDto = new AuthorDto("New author");
        GenreDto genreDto = new GenreDto("New genre");
        BookDto bookDto = new BookDto("New book");
        Author author = new Author(authorDto.getFullName());
        Genre genre = new Genre(genreDto.getName());
        Book book = new Book(bookDto.getTitle());
        Author savedAuthor = new Author(id, authorDto.getFullName());
        Genre savedGenre = new Genre(id, genreDto.getName());
        Book savedBook = new Book(id, book.getTitle());
        when(bookRepository.save(book)).thenReturn(savedBook);
        AuthorDto expectedAuthorDto = new AuthorDto(id, author.getFullName());
        GenreDto expectedGenreDto = new GenreDto(id, genre.getName());
        BookDto expectedBookDto = new BookDto(id, savedBook.getTitle());
        BookDto actualBookDto = bookService.saveBook(bookDto);
        assertThat(actualBookDto).isEqualTo(expectedBookDto);
    }

    @DisplayName("Should update book")
    @Test
    void shouldUpdateBook() {
        long id = 1;
        AuthorDto authorDto = new AuthorDto(id, null);
        GenreDto genreDto = new GenreDto(id, null);
        BookDto bookDto = new BookDto(id, "Edited book");
        Author author = new Author(id, null);
        Genre genre = new Genre(id, null);
        Book book = new Book(id, bookDto.getTitle());
        when(bookRepository.existsById(id)).thenReturn(true);
        when(bookRepository.update(book)).thenReturn(book);
        AuthorDto expectedAuthorDto = new AuthorDto(id, author.getFullName());
        GenreDto expectedGenreDto = new GenreDto(id, genre.getName());
        BookDto expectedBookDto = new BookDto(id, book.getTitle());
        BookDto actualBookDto = bookService.updateBook(bookDto);
        assertThat(actualBookDto).isEqualTo(expectedBookDto);
    }

    @DisplayName("Should throw exception when try update not existing book")
    @Test
    void shouldThrowExceptionWhenTryUpdateNotExistingBook() {
        long id = 1;
        AuthorDto authorDto = new AuthorDto(id, null);
        GenreDto genreDto = new GenreDto(id, null);
        BookDto bookDto = new BookDto(id, "Edited book");
        when(bookRepository.existsById(id)).thenReturn(false);
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

    @DisplayName("Should find book")
    @Test
    void shouldFindBook() {
        long id = 1;
        Author author = new Author(id, "Test author");
        Genre genre = new Genre(id, "Test genre");
        Book book = new Book(id, "Test book");
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        AuthorDto expectedAuthorDto = new AuthorDto(id, author.getFullName());
        GenreDto expectedGenreDto = new GenreDto(id, genre.getName());
        BookDto expectedBookDto = new BookDto(id, book.getTitle());
        BookDto actualBookDto = bookService.findBookById(id);
        assertThat(actualBookDto).isEqualTo(expectedBookDto);
    }

    @DisplayName("Should throw exception when try find not existing book")
    @Test
    void shouldThrowExceptionWhenTryFindNotExistingBook() {
        long id = 1;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.findBookById(id)).isInstanceOf(BookNotFoundException.class);
    }

    @DisplayName("Should find all books")
    @Test
    void shouldFindAllBooks() {
        Author author1 = new Author(1, "Test author 1");
        Author author2 = new Author(2, "Test author 2");
        Author author3 = new Author(3, "Test author 3");
        Genre genre1 = new Genre(1, "Test genre 1");
        Genre genre2 = new Genre(2, "Test genre 2");
        Genre genre3 = new Genre(3, "Test genre 3");
        Book book1 = new Book(1, "Test book 1");
        Book book2 = new Book(2, "Test book 2");
        Book book3 = new Book(3, "Test book 3");
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2, book3));
        AuthorDto authorDto1 = new AuthorDto(author1.getId(), author1.getFullName());
        AuthorDto authorDto2 = new AuthorDto(author2.getId(), author2.getFullName());
        AuthorDto authorDto3 = new AuthorDto(author3.getId(), author3.getFullName());
        GenreDto genreDto1 = new GenreDto(genre1.getId(), genre1.getName());
        GenreDto genreDto2 = new GenreDto(genre2.getId(), genre2.getName());
        GenreDto genreDto3 = new GenreDto(genre3.getId(), genre3.getName());
        BookDto bookDto1 = new BookDto(book1.getId(), book1.getTitle());
        BookDto bookDto2 = new BookDto(book2.getId(), book2.getTitle());
        BookDto bookDto3 = new BookDto(book3.getId(), book3.getTitle());
        List<BookDto> expectedBookDtoList = List.of(bookDto1, bookDto2, bookDto3);
        List<BookDto> actualBookDtoList = bookService.findAllBooks();
        assertThat(actualBookDtoList).isEqualTo(expectedBookDtoList);
    }

    @DisplayName("Should delete book")
    @Test
    void shouldDeleteBook() {
        assertThatCode(() -> bookService.deleteBookById(1)).doesNotThrowAnyException();
    }
}