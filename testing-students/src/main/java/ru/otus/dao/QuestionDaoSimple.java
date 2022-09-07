package ru.otus.dao;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.config.Config;
import ru.otus.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionDaoSimple implements QuestionDao {
    private final List<Question> questions;

    @SneakyThrows
    public QuestionDaoSimple(Config config) {
        Resource resource = new ClassPathResource(config.getResourceUrl());
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
        ObjectReader objectReader = csvMapper.readerFor(Question.class).with(csvSchema);
        MappingIterator<Question> questionMappingIterator = objectReader.readValues(resource.getInputStream());
        questions = new ArrayList<>();
        questions.addAll(questionMappingIterator.readAll());
    }

    @Override
    public Optional<Question> findById(long id) {
        return questions.stream()
                .filter(question -> question.getId() == id)
                .findFirst();
    }

    @Override
    public List<Question> findAll() {
        return questions;
    }
}
