package ru.otus.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@ToString(exclude = {"book"})
@EqualsAndHashCode(exclude = {"book"})
@AllArgsConstructor
@NoArgsConstructor
@Document("comments")
public class Comment {
    @Id
    private String id;
    @NotBlank
    @Field("text")
    private String text;
    @DBRef(lazy = true)
    private Book book;

    public Comment(String id, String text) {
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
