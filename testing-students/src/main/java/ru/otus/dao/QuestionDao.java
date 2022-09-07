package ru.otus.dao;

import ru.otus.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionDao {
    Optional<Question> findById(long id);

    List<Question> findAll();
}
