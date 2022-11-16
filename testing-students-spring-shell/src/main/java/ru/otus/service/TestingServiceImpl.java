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
    private final MessageLocaleService messageLocaleService;

    @Override
    public void start() {
        ioService.outputString(messageLocaleService.getMessage("greeting"));
        Student student = initAndGetStudent();
        ioService.outputString(student.getFullName() + messageLocaleService.getMessage("answerTheQuestions"));
        List<AnswerPage> answerPages = startTestingAndGetAnswers();
        TestingResult testingResult = answerAnalyser.analyze(student, answerPages);
        printTestingResult(testingResult);
    }

    private Student initAndGetStudent() {
        String firstname = ioService.readStringWithPrompt(messageLocaleService.getMessage("inputFirstname"));
        String lastname = ioService.readStringWithPrompt(messageLocaleService.getMessage("inputLastname"));
        return new Student(firstname, lastname);
    }

    private List<AnswerPage> startTestingAndGetAnswers() {
        ioService.outputString(messageLocaleService.getMessage("startTesting"));
        List<AnswerPage> answerPages = new ArrayList<>();
        questionService.getAllQuestions().forEach(question -> {
            String questionText = question.getId() + ". " + question.getText() + "\n" + String.join(" | ",
                    question.getVariants().stream().map(Answer::getText).toList());
            String answer = ioService.readStringWithPrompt(questionText);
            answerPages.add(new AnswerPage(question.getAnswer(), new Answer(answer)));
            ioService.outputString(messageLocaleService.getMessage("yourAnswer") + " " + answer);
        });
        ioService.outputString(messageLocaleService.getMessage("endTesting"));
        return answerPages;
    }

    private void printTestingResult(TestingResult testingResult) {
        ioService.outputString(messageLocaleService.getMessage("results"));
        String result = messageLocaleService.getMessage("student") + " " + testingResult.getStudent().getFullName() +
                "\n" + messageLocaleService.getMessage("totalQuestions") + " " + testingResult.getTotalQuestions() +
                "\n" + messageLocaleService.getMessage("rightAnswers") + " " + testingResult.getRightAnswers() +
                "\n" + messageLocaleService.getMessage("percentRightAnswers") + " " + testingResult.getPercentRightAnswers();
        ioService.outputString(result);
    }
}
