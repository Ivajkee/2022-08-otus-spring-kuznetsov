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
    private int rightAnswers;
    private String percentRightAnswers;
}
