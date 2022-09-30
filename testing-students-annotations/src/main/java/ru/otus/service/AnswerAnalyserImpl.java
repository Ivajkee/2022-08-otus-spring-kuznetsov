package ru.otus.service;

import org.springframework.stereotype.Component;
import ru.otus.domain.AnswerPage;
import ru.otus.domain.Student;
import ru.otus.domain.TestingResult;

import java.text.DecimalFormat;
import java.util.List;

@Component
public class AnswerAnalyserImpl implements AnswerAnalyser {
    @Override
    public TestingResult analyze(Student student, List<AnswerPage> answerPages) {
        int totalQuestions = answerPages.size();
        int correctAnswers = answerPages.stream()
                .filter(answerPage -> answerPage.getRightAnswer().getText().equalsIgnoreCase(answerPage.getAnswer().getText()))
                .toList()
                .size();
        String percentCorrectAnswers = new DecimalFormat("###.##%").format((double) correctAnswers / (double) totalQuestions);
        return new TestingResult(student, totalQuestions, correctAnswers, percentCorrectAnswers);
    }
}
