package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.domain.Question;
import ru.otus.service.QuestionService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService questionService = context.getBean(QuestionService.class);
        List<Question> questions = questionService.getAllQuestions();
        questions.forEach(question -> System.out.println(question.getId() + ". " + question.getText() + "\n" +
                String.join(" | ", question.getVariants())));
    }
}