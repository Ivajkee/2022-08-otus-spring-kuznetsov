package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.config.ResourceConfig;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionDaoSimple;
import ru.otus.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionServiceTest {
    @Test
    @DisplayName("Find all questions")
    void findAllQuestions() {
        QuestionDao questionDao = new QuestionDaoSimple(new ResourceConfig("questions.csv"));
        QuestionService questionService = new QuestionServiceImpl(questionDao);
        List<Question> questions = questionService.getAllQuestions();
        assertEquals(5, questions.size());
    }
}