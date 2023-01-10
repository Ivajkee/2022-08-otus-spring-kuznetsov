package otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.dao.BookDao;
import ru.otus.dao.BookDaoJpa;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(BookDaoJpa.class)
@JdbcTest
class BookDaoTest {
    @Autowired
    private BookDao bookDao;

    @DisplayName("Should return expected books count")
    @Test
    void shouldReturnExpectedBooksCount() {
        long actualCount = bookDao.count();
        assertThat(actualCount).isEqualTo(3);
    }

    @DisplayName("Should save book")
    @Test
    void shouldSaveBook() {
        Author expectedAuthor = new Author(1, "Александр Сергеевич Пушкин");
        Genre expectedGenre = new Genre(3, "Фэнтези");
        Book expectedBook = new Book("Test book", expectedAuthor, expectedGenre);
        expectedBook = bookDao.save(expectedBook);
        Optional<Book> optionalActualBook = bookDao.findById(expectedBook.getId());
        assertThat(optionalActualBook).hasValue(expectedBook);
    }

    @DisplayName("Should update book")
    @Test
    void shouldUpdateBook() {
        Author expectedAuthor = new Author(1, "Александр Сергеевич Пушкин");
        Genre expectedGenre = new Genre(3, "Фэнтези");
        Book expectedBook = new Book(3, "Edited book", expectedAuthor, expectedGenre);
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
        boolean actualValue = bookDao.existsById(4);
        assertThat(actualValue).isFalse();
    }

    @DisplayName("Should find book")
    @Test
    void shouldFindBook() {
        Author expectedAuthor = new Author(1, "Александр Сергеевич Пушкин");
        Genre expectedGenre = new Genre(1, "Поэма");
        Book expectedBook = new Book(1, "Руслан и Людмила", expectedAuthor, expectedGenre);
        Optional<Book> optionalActualBook = bookDao.findById(1);
        assertThat(optionalActualBook).hasValue(expectedBook);
    }

    @DisplayName("Should find all books")
    @Test
    void shouldFindAllBooks() {
        Author expectedAuthor1 = new Author(1, "Александр Сергеевич Пушкин");
        Genre expectedGenre1 = new Genre(1, "Поэма");
        Author expectedAuthor2 = new Author(2, "Лев Николаевич Толстой");
        Genre expectedGenre2 = new Genre(2, "Роман");
        Author expectedAuthor3 = new Author(3, "Джоан Роулинг");
        Genre expectedGenre3 = new Genre(3, "Фэнтези");
        Book expectedBook1 = new Book(1, "Руслан и Людмила", expectedAuthor1, expectedGenre1);
        Book expectedBook2 = new Book(2, "Война и мир", expectedAuthor2, expectedGenre2);
        Book expectedBook3 = new Book(3, "Гарри Поттер", expectedAuthor3, expectedGenre3);
        List<Book> expectedBooks = List.of(expectedBook1, expectedBook2, expectedBook3);
        List<Book> actualBooks = bookDao.findAll();
        assertThat(actualBooks).isEqualTo(expectedBooks);
    }

    @DisplayName("Should delete book")
    @Test
    void shouldDeleteBook() {
        Author expectedAuthor = new Author(1, "Александр Сергеевич Пушкин");
        Genre expectedGenre = new Genre(1, "Поэма");
        Book expectedBook = new Book("Test book", expectedAuthor, expectedGenre);
        expectedBook = bookDao.save(expectedBook);
        Optional<Book> optionalAuthor = bookDao.findById(expectedBook.getId());
        assertThat(optionalAuthor).hasValue(expectedBook);
        bookDao.deleteById(expectedBook.getId());
        Optional<Book> optionalDeletedAuthor = bookDao.findById(expectedBook.getId());
        assertThat(optionalDeletedAuthor).isEmpty();
    }
}