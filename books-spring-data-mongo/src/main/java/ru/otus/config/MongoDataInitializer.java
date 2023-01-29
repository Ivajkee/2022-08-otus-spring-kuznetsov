package ru.otus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.domain.model.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.sequence.SequenceGeneratorService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class MongoDataInitializer {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final SequenceGeneratorService idGenerator;

    //C mongock не получилось
    @PostConstruct
    private void insertData() {
        Author author1 = new Author(idGenerator.generate(Author.SEQUENCE_NAME), "Александр Сергеевич Пушкин");
        Author author2 = new Author(idGenerator.generate(Author.SEQUENCE_NAME), "Лев Николаевич Толстой");
        Author author3 = new Author(idGenerator.generate(Author.SEQUENCE_NAME), "Джоан Роулинг");
        authorRepository.saveAll(List.of(author1, author2, author3));

        Genre genre1 = new Genre(idGenerator.generate(Genre.SEQUENCE_NAME), "Поэма");
        Genre genre2 = new Genre(idGenerator.generate(Genre.SEQUENCE_NAME), "Роман");
        Genre genre3 = new Genre(idGenerator.generate(Genre.SEQUENCE_NAME), "Фэнтези");
        genreRepository.saveAll(List.of(genre1, genre2, genre3));

        Book book1 = new Book(idGenerator.generate(Book.SEQUENCE_NAME), "Руслан и Людмила", Set.of(author1), Set.of(genre1));
        Book book2 = new Book(idGenerator.generate(Book.SEQUENCE_NAME), "Война и мир", Set.of(author2), Set.of(genre2));
        Book book3 = new Book(idGenerator.generate(Book.SEQUENCE_NAME), "Гарри Поттер", Set.of(author3), Set.of(genre3));
        bookRepository.saveAll(List.of(book1, book2, book3));

        Comment comment1 = new Comment(idGenerator.generate(Comment.SEQUENCE_NAME), "comment 1", book1);
        Comment comment2 = new Comment(idGenerator.generate(Comment.SEQUENCE_NAME), "comment 2", book2);
        Comment comment3 = new Comment(idGenerator.generate(Comment.SEQUENCE_NAME), "comment 3", book3);
        commentRepository.saveAll(List.of(comment1, comment2, comment3));
    }
}
