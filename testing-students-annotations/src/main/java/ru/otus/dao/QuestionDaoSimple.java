package ru.otus.dao;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.config.ResourceConfig;
import ru.otus.domain.Question;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionDaoSimple implements QuestionDao {
    private final ResourceConfig resourceConfig;

    @Override
    public List<Question> findAll() {
        Resource resource = new ClassPathResource(resourceConfig.getUrl());
        ObjectMapper objectMapper = new CsvMapper();
        CsvSchema csvSchema = CsvSchema.builder()
                .setSkipFirstDataRow(true)
                .addColumn("id")
                .addColumn("text")
                .addColumn("answer.text")
                .addArrayColumn("variants")
                .build();
        MappingIterator<Question> questionMappingIterator;
        List<Question> questions;
        try {
            questionMappingIterator = objectMapper
                    .reader(csvSchema)
                    .forType(Question.class)
                    .readValues(resource.getInputStream());
            questions = questionMappingIterator.readAll();
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e);
            return List.of();
        }
        return questions;
    }
}
