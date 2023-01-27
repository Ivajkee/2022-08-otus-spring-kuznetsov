package ru.otus.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(exclude = {"authors", "genres"})
@EqualsAndHashCode(exclude = {"authors", "genres"})
@AllArgsConstructor
@NoArgsConstructor
@Document("books")
public class Book {
    @Transient
    public static final String SEQUENCE_NAME = "book_sequence";
    @Id
    private long id;
    @Indexed
    @NotBlank
    @Field("title")
    private String title;
    private Set<Author> authors = new HashSet<>();
    private Set<Genre> genres = new HashSet<>();

    public Book(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Book(String title) {
        this.title = title;
    }

    public boolean addAuthor(Author author) {
        if (authors.contains(author)) {
            return false;
        }
        return authors.add(author);
    }

    public boolean deleteAuthor(Author author) {
        return authors.remove(author);
    }

    public boolean addGenre(Genre genre) {
        if (genres.contains(genre)) {
            return false;
        }
        return genres.add(genre);
    }

    public boolean deleteGenre(Genre genre) {
        return genres.remove(genre);
    }
}
