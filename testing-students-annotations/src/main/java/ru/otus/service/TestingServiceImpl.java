package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {
    private final QuestionService questionService;
    private final QuestionRenderer questionRenderer;

    @Override
    public void start() {
        questionService.getAllQuestions().forEach(questionRenderer::render);
    }
}
