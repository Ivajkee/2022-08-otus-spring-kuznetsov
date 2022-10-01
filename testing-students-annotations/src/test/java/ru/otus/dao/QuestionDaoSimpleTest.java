package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.config.ResourceConfig;
import ru.otus.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionDaoSimpleTest {

    @Test
    @DisplayName("Find all questions")
    void findAllQuestions() {
        QuestionDao questionDao = new QuestionDaoSimple(new ResourceConfig("questions-test.csv"));
        List<Question> questions = questionDao.findAll();
        assertEquals(5, questions.size());
    }
}