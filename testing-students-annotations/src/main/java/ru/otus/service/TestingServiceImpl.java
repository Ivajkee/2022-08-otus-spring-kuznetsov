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
        ioService.outputString("Welcome to Testing Students Application!");
        Student student = initAndGetStudent();
        ioService.outputString(student.getFullName() + ", please answer the following questions");
        List<AnswerPage> answerPages = startTestingAndGetAnswers();
        TestingResult testingResult = answerAnalyser.analyze(student, answerPages);
        printTestingResult(testingResult);
    }

    private Student initAndGetStudent() {
        String firstname = ioService.readStringWithPrompt("Enter your firstname");
        String lastname = ioService.readStringWithPrompt("Enter your lastname");
        return new Student(firstname, lastname);
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

    private void printTestingResult(TestingResult testingResult) {
        ioService.outputString("\n****************RESULTS****************");
        String result = "Student: " + testingResult.getStudent().getFullName() + "\n" + "Total questions: " + testingResult.getTotalQuestions() +
                "\nRight answers: " + testingResult.getRightAnswers() + "\nPercent right answers: " +
                testingResult.getPercentRightAnswers();
        ioService.outputString(result);
    }
}
