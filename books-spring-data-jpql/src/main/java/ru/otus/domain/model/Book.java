package ru.otus.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString(exclude = {"authors", "genres", "comments"})
@EqualsAndHashCode(exclude = {"authors", "genres", "comments"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "book-graph", attributeNodes = {
        @NamedAttributeNode("authors"),
        @NamedAttributeNode("genres"),
        @NamedAttributeNode("comments")
})
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @JoinTable(name = "books_authors", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Author> authors = new HashSet<>();
    @JoinTable(name = "books_genres", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Genre> genres = new HashSet<>();
    @JoinColumn(name = "book_id", nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

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

    public boolean addComment(Comment comment) {
        return comments.add(comment);
    }
}
