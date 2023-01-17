package otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.model.Book;
import ru.otus.exception.BookNotFoundException;
import ru.otus.repository.BookRepository;
import ru.otus.service.BookService;
import ru.otus.service.BookServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceTest {
    @Autowired
    private BookService bookService;
    @MockBean
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
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(conversionService.convert(book, BookDto.class)).thenReturn(expectedBookDto);
        BookDto actualBookDto = bookService.updateBook(bookDto);
        assertThat(actualBookDto).isEqualTo(expectedBookDto);
    }

    @DisplayName("Should throw exception when try update not existing book")
    @Test
    void shouldThrowExceptionWhenTryUpdateNotExistingBook() {
        long id = 1;
        BookDto bookDto = new BookDto(id, "Edited book");
        when(bookRepository.findById(id)).thenReturn(Optional.empty());
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
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(conversionService.convert(book, BookDto.class)).thenReturn(expectedBookDto);
        BookDto actualBookDto = bookService.findBookById(id);
        assertThat(actualBookDto).isEqualTo(expectedBookDto);
    }

    @DisplayName("Should throw exception when try find not existing book by id")
    @Test
    void shouldThrowExceptionWhenTryFindNotExistingBookById() {
        long id = 1;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());
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
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        assertThatCode(() -> bookService.deleteBookById(1)).doesNotThrowAnyException();
    }
}