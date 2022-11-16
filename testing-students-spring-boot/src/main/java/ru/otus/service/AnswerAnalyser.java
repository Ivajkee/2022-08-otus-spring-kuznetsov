package ru.otus.service;

import ru.otus.domain.AnswerPage;
import ru.otus.domain.Student;
import ru.otus.domain.TestingResult;

import java.util.List;

public interface AnswerAnalyser {
    TestingResult analyze(Student student, List<AnswerPage> answerPages);
}
