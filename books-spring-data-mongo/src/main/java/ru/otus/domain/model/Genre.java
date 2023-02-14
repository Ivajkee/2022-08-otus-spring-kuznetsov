package ru.otus.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("genres")
public class Genre {
    @Id
    private String id;
    @NotBlank
    @Indexed(unique = true)
    @Field("name")
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
