package ru.otus.service;

import org.springframework.stereotype.Component;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

@Component
public class QuestionRendererImpl implements QuestionRenderer {
    @Override
    public void render(Question question) {
        System.out.println(question.getId() + ". " + question.getText() + "\n" +
                String.join(" | ", question.getVariants().stream().map(Answer::getText).toList()));
    }
}
