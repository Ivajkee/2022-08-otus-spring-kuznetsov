package ru.otus.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString(exclude = {"book"})
@EqualsAndHashCode(exclude = {"book"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "text")
    private String text;
    @JoinColumn(name = "book_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    public Comment(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }

    public Comment(String text) {
        this.text = text;
    }
}
