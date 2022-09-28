package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Answer;
import ru.otus.domain.AnswerPage;
import ru.otus.domain.Student;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {
    private final QuestionService questionService;
    private final IOService ioService;
    private final AnswerAnalyser answerAnalyser;

    @Override
    public void start() {
        ioService.outputString("Welcome to Testing Students Application!");
        String firstname = ioService.readStringWithPrompt("Enter your firstname");
        String lastname = ioService.readStringWithPrompt("Enter your lastname");
        Student student = new Student(firstname, lastname);
        ioService.outputString(student.getFullName() + ", please answer the following questions");
        ioService.outputString("****************START TESTING****************");
        List<AnswerPage> answerPages = new ArrayList<>();
        questionService.getAllQuestions().forEach(question -> {
            String answer = ioService.readStringWithPrompt(question.print());
            answerPages.add(new AnswerPage(question.getAnswer(), new Answer(answer)));
            ioService.outputString("Your answer: " + answer);
        });
        ioService.outputString("****************END TESTING****************");
        ioService.outputString("\n****************RESULTS****************");
        ioService.outputString(answerAnalyser.analyze(student, answerPages).getResult());
    }
}
