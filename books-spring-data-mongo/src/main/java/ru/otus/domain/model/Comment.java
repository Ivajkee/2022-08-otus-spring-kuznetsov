package ru.otus.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;

@Data
@ToString(exclude = {"book"})
@EqualsAndHashCode(exclude = {"book"})
@AllArgsConstructor
@NoArgsConstructor
@Document("comments")
public class Comment {
    @Transient
    public static final String SEQUENCE_NAME = "comment_sequence";
    @Id
    private long id;
    @NotBlank
    @Field("text")
    private String text;
    @DBRef(lazy = true)
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
