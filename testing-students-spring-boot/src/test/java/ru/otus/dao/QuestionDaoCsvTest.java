package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.config.ResourceConfig;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionDaoCsvTest {
    @Test
    @DisplayName("Should load expected question list from given resource")
    void shouldLoadExpectedQuestionListFromGivenResource() {
        QuestionDao questionDao = new QuestionDaoCsv(new ResourceConfig("questions-test.csv"));
        List<Question> questions = questionDao.findAll();
        Question expectedQuestion1 = new Question(1, "Which country hosted the 2018 FIFA World Cup?",
                new Answer("Russia"), List.of(new Answer("France"), new Answer("Germany"),
                new Answer("Russia"), new Answer("Qatar")));
        Question expectedQuestion2 = new Question(2, "Who said meow?", new Answer("Cat"),
                List.of(new Answer("Dog"), new Answer("Frog"), new Answer("Cat"), new Answer("Bear")));
        assertEquals(2, questions.size());
        assertEquals(expectedQuestion1, questions.get(0));
        assertEquals(expectedQuestion2, questions.get(1));
    }
}