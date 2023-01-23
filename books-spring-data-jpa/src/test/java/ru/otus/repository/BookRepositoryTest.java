package ru.otus.repository;

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

    @DisplayName("Should find book by id")
    @Test
    void shouldFindBookById() {
        Book expectedBook = new Book(1, "Руслан и Людмила");
        Optional<Book> optionalActualBook = bookRepository.findById(1L);
        assertThat(optionalActualBook).hasValue(expectedBook);
    }

    @DisplayName("Should find book by title")
    @Test
    void shouldFindBookByTitle() {
        Book expectedBook = new Book(1, "Руслан и Людмила");
        Optional<Book> optionalActualBook = bookRepository.findByTitleIgnoreCase("руслан и людмила");
        assertThat(optionalActualBook).hasValue(expectedBook);
    }

    @DisplayName("Should find all books")
    @Test
    void shouldFindAllBooks() {
        Book expectedBook1 = new Book(1, "Руслан и Людмила");
        Book expectedBook2 = new Book(2, "Война и мир");
        Book expectedBook3 = new Book(3, "Гарри Поттер");
        List<Book> expectedBooks = List.of(expectedBook1, expectedBook2, expectedBook3);
        List<Book> actualBooks = bookRepository.findAllWIthInfo();
        assertThat(actualBooks).isEqualTo(expectedBooks);
    }
}