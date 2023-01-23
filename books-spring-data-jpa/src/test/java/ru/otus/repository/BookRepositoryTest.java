package ru.otus.repository;

import jakarta.persistence.PersistenceUnitUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.domain.model.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Should find book by id without authors and genres")
    @Test
    void shouldFindBookByIdWithoutAuthorsAndGenres() {
        PersistenceUnitUtil persistenceUnitUtil = em.getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil();
        Book expectedBook = new Book(1, "Руслан и Людмила");
        Optional<Book> optionalActualBook = bookRepository.findById(1L);
        assertThat(optionalActualBook).hasValue(expectedBook).get().satisfies(book -> {
            assertThat(persistenceUnitUtil.isLoaded(book.getAuthors())).isFalse();
            assertThat(persistenceUnitUtil.isLoaded(book.getGenres())).isFalse();
        });
    }

    @DisplayName("Should find book by id with authors and genres")
    @Test
    void shouldFindBookByIdWithAuthorsAndGenres() {
        PersistenceUnitUtil persistenceUnitUtil = em.getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil();
        Book expectedBook = new Book(1, "Руслан и Людмила");
        Optional<Book> optionalActualBook = bookRepository.findByIdWithInfo(1L);
        assertThat(optionalActualBook).hasValue(expectedBook).get().satisfies(book -> {
            assertThat(persistenceUnitUtil.isLoaded(book.getAuthors())).isTrue();
            assertThat(persistenceUnitUtil.isLoaded(book.getGenres())).isTrue();
        });
    }

    @DisplayName("Should find book by title with authors and genres")
    @Test
    void shouldFindBookByTitleWithAuthorsAndGenres() {
        PersistenceUnitUtil persistenceUnitUtil = em.getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil();
        Book expectedBook = new Book(1, "Руслан и Людмила");
        Optional<Book> optionalActualBook = bookRepository.findByTitleIgnoreCase("руслан и людмила");
        assertThat(optionalActualBook).hasValue(expectedBook).get().satisfies(book -> {
            assertThat(persistenceUnitUtil.isLoaded(book.getAuthors())).isTrue();
            assertThat(persistenceUnitUtil.isLoaded(book.getGenres())).isTrue();
        });
    }

    @DisplayName("Should find all books without authors and genres")
    @Test
    void shouldFindAllBooksWithoutAuthorsAndGenres() {
        PersistenceUnitUtil persistenceUnitUtil = em.getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil();
        Book expectedBook1 = new Book(1, "Руслан и Людмила");
        Book expectedBook2 = new Book(2, "Война и мир");
        Book expectedBook3 = new Book(3, "Гарри Поттер");
        List<Book> expectedBooks = List.of(expectedBook1, expectedBook2, expectedBook3);
        List<Book> actualBooks = bookRepository.findAll();
        assertThat(actualBooks).isEqualTo(expectedBooks);
        actualBooks.forEach(book -> {
            assertThat(persistenceUnitUtil.isLoaded(book.getAuthors())).isFalse();
            assertThat(persistenceUnitUtil.isLoaded(book.getGenres())).isFalse();
        });
    }

    @DisplayName("Should find all books with authors and genres")
    @Test
    void shouldFindAllBooksWithAuthorsAndGenres() {
        PersistenceUnitUtil persistenceUnitUtil = em.getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil();
        Book expectedBook1 = new Book(1, "Руслан и Людмила");
        Book expectedBook2 = new Book(2, "Война и мир");
        Book expectedBook3 = new Book(3, "Гарри Поттер");
        List<Book> expectedBooks = List.of(expectedBook1, expectedBook2, expectedBook3);
        List<Book> actualBooks = bookRepository.findAllWIthInfo();
        assertThat(actualBooks).isEqualTo(expectedBooks);
        actualBooks.forEach(book -> {
            assertThat(persistenceUnitUtil.isLoaded(book.getAuthors())).isTrue();
            assertThat(persistenceUnitUtil.isLoaded(book.getGenres())).isTrue();
        });
    }
}