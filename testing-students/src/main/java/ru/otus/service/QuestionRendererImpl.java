package ru.otus.service;

import ru.otus.domain.Answer;
import ru.otus.domain.Question;

public class QuestionRendererImpl implements QuestionRenderer {
    @Override
    public void render(Question question) {
        System.out.println(question.getId() + ". " + question.getText() + "\n" +
                String.join(" | ", question.getVariants().stream().map(Answer::getText).toList()));
    }
}
