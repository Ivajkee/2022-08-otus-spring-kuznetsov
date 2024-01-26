package ru.otus.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("authors")
public class Author {
    @Id
    private String id;
    @Indexed
    @NotBlank
    @Field("full_name")
    private String fullName;

    public Author(String fullName) {
        this.fullName = fullName;
    }
}
