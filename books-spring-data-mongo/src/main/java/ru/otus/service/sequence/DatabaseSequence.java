package ru.otus.service.sequence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("database_sequences")
public class DatabaseSequence {
    @Id
    private String id;
    @Field("sequence")
    private long sequence;
}
