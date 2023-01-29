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
@Document("genres")
public class Genre {
    @Transient
    public static final String SEQUENCE_NAME = "genre_sequence";
    @Id
    private long id;
    @NotBlank
    @Indexed(unique = true)
    @Field("name")
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}