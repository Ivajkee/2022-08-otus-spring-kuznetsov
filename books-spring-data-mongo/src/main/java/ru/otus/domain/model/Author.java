package ru.otus.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("authors")
public class Author {
    @Transient
    public static final String SEQUENCE_NAME = "author_sequence";
    @Id
    private long id;
    @Indexed
    @NotBlank
    @Field("full_name")
    private String fullName;

    public Author(String fullName) {
        this.fullName = fullName;
    }
}
