package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Answer;
import ru.otus.domain.AnswerPage;
import ru.otus.domain.Student;
import ru.otus.domain.TestingResult;

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
        Student student = initAndGetStudent();
        List<AnswerPage> answerPages = startTestingAndGetAnswers();
        printResult(student, answerPages);
    }

    private Student initAndGetStudent() {
        ioService.outputString("Welcome to Testing Students Application!");
        String firstname = ioService.readStringWithPrompt("Enter your firstname");
        String lastname = ioService.readStringWithPrompt("Enter your lastname");
        Student student = new Student(firstname, lastname);
        ioService.outputString(student.getFullName() + ", please answer the following questions");
        return student;
    }

    private List<AnswerPage> startTestingAndGetAnswers() {
        ioService.outputString("****************START TESTING****************");
        List<AnswerPage> answerPages = new ArrayList<>();
        questionService.getAllQuestions().forEach(question -> {
            String answer = ioService.readStringWithPrompt(question.print());
            answerPages.add(new AnswerPage(question.getAnswer(), new Answer(answer)));
            ioService.outputString("Your answer: " + answer);
        });
        ioService.outputString("****************END TESTING****************");
        return answerPages;
    }

    private void printResult(Student student, List<AnswerPage> answerPages) {
        ioService.outputString("\n****************RESULTS****************");
        TestingResult testingResult = answerAnalyser.analyze(student, answerPages);
        String result = "Student: " + student.getFullName() + "\n" + "Total questions: " + testingResult.getTotalQuestions() +
                "\nRight answers: " + testingResult.getRightAnswers() + "\nPercent right answers: " +
                testingResult.getPercentRightAnswers();
        ioService.outputString(result);
    }
}
