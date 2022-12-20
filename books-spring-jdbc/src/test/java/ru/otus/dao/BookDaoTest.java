package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(BookDaoJdbc.class)
@JdbcTest
class BookDaoTest {
    @Autowired
    private BookDao bookDao;

    @DisplayName("Should return number of five books")
    @Test
    void shouldReturnNumberOfFiveBooks() {
        long actualCount = bookDao.count();
        assertThat(actualCount).isEqualTo(5);
    }

    @DisplayName("Should save one book")
    @Test
    void shouldSaveOneBook() {
        Author expectedAuthor = new Author(1, "Александр Сергеевич Пушкин", null);
        Genre expectedGenre = new Genre(5, "Ужасы", null);
        Book expectedBook = new Book("Test book", expectedAuthor, expectedGenre);
        expectedBook = bookDao.save(expectedBook);
        Optional<Book> optionalActualBook = bookDao.findById(expectedBook.getId());
        assertThat(optionalActualBook).hasValue(expectedBook);
    }

    @DisplayName("Should update one book")
    @Test
    void shouldUpdateOneBook() {
        Author expectedAuthor = new Author(1, "Александр Сергеевич Пушкин", null);
        Genre expectedGenre = new Genre(5, "Ужасы", null);
        Book expectedBook = new Book(5L, "Edited book", expectedAuthor, expectedGenre);
        expectedBook = bookDao.update(expectedBook);
        Optional<Book> optionalActualBook = bookDao.findById(expectedBook.getId());
        assertThat(optionalActualBook).hasValue(expectedBook);
    }

    @DisplayName("Should be exist book")
    @Test
    void shouldBeExistBook() {
        boolean actualValue = bookDao.existsById(1);
        assertThat(actualValue).isTrue();
    }

    @DisplayName("Should be not exist book")
    @Test
    void shouldBeNotExistBook() {
        boolean actualValue = bookDao.existsById(6);
        assertThat(actualValue).isFalse();
    }

    @DisplayName("Should find one book")
    @Test
    void shouldFindOneBook() {
        Author expectedAuthor = new Author(1, "Александр Сергеевич Пушкин", null);
        Genre expectedGenre = new Genre(1, "Поэма", null);
        Book expectedBook = new Book(1, "Руслан и Людмила", expectedAuthor, expectedGenre);
        Optional<Book> optionalActualBook = bookDao.findById(1);
        assertThat(optionalActualBook).hasValue(expectedBook);
    }

    @DisplayName("Should find all books")
    @Test
    void shouldFindAllBooks() {
        Book expectedBook1 = new Book(1, "Руслан и Людмила", null, null);
        Book expectedBook2 = new Book(2, "Война и мир", null, null);
        Book expectedBook3 = new Book(3, "Гарри Поттер", null, null);
        Book expectedBook4 = new Book(4, "Убийство в Восточном экспрессе", null, null);
        Book expectedBook5 = new Book(5, "Оно", null, null);
        List<Book> expectedBooks = List.of(expectedBook1, expectedBook2, expectedBook3, expectedBook4, expectedBook5);
        List<Book> actualBooks = bookDao.findAll();
        assertThat(actualBooks).isEqualTo(expectedBooks);
    }

    @DisplayName("Should delete one book")
    @Test
    void shouldDeleteOneBook() {
        Author expectedAuthor = new Author(1, "Александр Сергеевич Пушкин", null);
        Genre expectedGenre = new Genre(1, "Поэма", null);
        Book expectedBook = new Book("Test book", expectedAuthor, expectedGenre);
        expectedBook = bookDao.save(expectedBook);
        Optional<Book> optionalAuthor = bookDao.findById(expectedBook.getId());
        assertThat(optionalAuthor).hasValue(expectedBook);
        bookDao.deleteById(expectedBook.getId());
        Optional<Book> optionalDeletedAuthor = bookDao.findById(expectedBook.getId());
        assertThat(optionalDeletedAuthor).isEmpty();
    }
}