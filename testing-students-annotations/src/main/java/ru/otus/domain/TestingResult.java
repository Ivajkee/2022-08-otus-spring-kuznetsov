package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestingResult {
    private Student student;
    private int totalQuestions;
    private int correctAnswers;
    private String percentCorrectAnswers;

    public String getResult() {
        return "Student: " + student.getFullName() + "\n" + "Total questions: " + totalQuestions + "\nCorrect answers: "
                + correctAnswers + "\nPercent correct answers: " + percentCorrectAnswers;
    }
}
