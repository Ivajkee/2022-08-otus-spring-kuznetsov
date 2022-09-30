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
        int rightAnswers = (int) answerPages.stream()
                .filter(answerPage -> answerPage.getRightAnswer().getText().equalsIgnoreCase(answerPage.getAnswer().getText()))
                .count();
        String percentRightAnswers = new DecimalFormat("###.##%").format((double) rightAnswers / (double) totalQuestions);
        return new TestingResult(student, totalQuestions, rightAnswers, percentRightAnswers);
    }
}
