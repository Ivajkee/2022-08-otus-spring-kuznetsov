package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.model.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(BookRepositoryJpa.class)
@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Should return expected books count")
    @Test
    void shouldReturnExpectedBooksCount() {
        long actualCount = bookRepository.count();
        assertThat(actualCount).isEqualTo(3);
    }

    @DisplayName("Should save book")
    @Test
    void shouldSaveBook() {
        Book book = new Book("Test book");
        Book expectedBook = bookRepository.save(book);
        Optional<Book> optionalActualBook = bookRepository.findByIdWithInfo(expectedBook.getId());
        assertThat(optionalActualBook).hasValue(expectedBook);
    }

    @DisplayName("Should update book")
    @Test
    void shouldUpdateBook() {
        Book book = new Book(3, "Edited book");
        Book expectedBook = bookRepository.update(book);
        Optional<Book> optionalActualBook = bookRepository.findByIdWithInfo(expectedBook.getId());
        assertThat(optionalActualBook).hasValue(expectedBook);
    }

    @DisplayName("Should be exist book")
    @Test
    void shouldBeExistBook() {
        boolean actualValue = bookRepository.existsById(1);
        assertThat(actualValue).isTrue();
    }

    @DisplayName("Should be not exist book")
    @Test
    void shouldBeNotExistBook() {
        boolean actualValue = bookRepository.existsById(4);
        assertThat(actualValue).isFalse();
    }

    @DisplayName("Should find book by id")
    @Test
    void shouldFindBookById() {
        Book expectedBook = new Book(1, "Руслан и Людмила");
        Optional<Book> optionalActualBook = bookRepository.findByIdWithInfo(1);
        assertThat(optionalActualBook).hasValue(expectedBook);
    }

    @DisplayName("Should find book by title")
    @Test
    void shouldFindBookByTitle() {
        Book expectedBook = new Book(1, "Руслан и Людмила");
        Optional<Book> optionalActualBook = bookRepository.findByTitle("руслан и людмила");
        assertThat(optionalActualBook).hasValue(expectedBook);
    }

    @DisplayName("Should find all books")
    @Test
    void shouldFindAllBooks() {
        Book expectedBook1 = new Book(1, "Руслан и Людмила");
        Book expectedBook2 = new Book(2, "Война и мир");
        Book expectedBook3 = new Book(3, "Гарри Поттер");
        List<Book> expectedBooks = List.of(expectedBook1, expectedBook2, expectedBook3);
        List<Book> actualBooks = bookRepository.findAll();
        assertThat(actualBooks).isEqualTo(expectedBooks);
    }

    @DisplayName("Should delete book")
    @Test
    void shouldDeleteBook() {
        Book book = new Book("Test book");
        Book expectedBook = em.persistAndFlush(book);
        Optional<Book> optionalAuthor = bookRepository.findByIdWithInfo(expectedBook.getId());
        assertThat(optionalAuthor).hasValue(expectedBook);
        bookRepository.deleteById(expectedBook.getId());
        Optional<Book> optionalDeletedAuthor = bookRepository.findByIdWithInfo(expectedBook.getId());
        assertThat(optionalDeletedAuthor).isEmpty();
    }
}