package ru.otus.service;

import ru.otus.domain.Question;

import java.util.List;

public interface QuestionService {
    Question getQuestionById(long id);

    List<Question> getAllQuestions();
}
