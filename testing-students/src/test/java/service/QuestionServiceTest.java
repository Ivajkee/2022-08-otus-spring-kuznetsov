package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.domain.Question;
import ru.otus.service.QuestionService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionServiceTest {
    private final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
    private final QuestionService questionService = applicationContext.getBean(QuestionService.class);

    @Test
    @DisplayName("Find all questions")
    void findAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        assertEquals(5, questions.size());
    }
}