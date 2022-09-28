package ru.otus.service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {
    private final QuestionService questionService;
    private final QuestionRenderer questionRenderer;

    @Override
    public void start() {
        questionService.getAllQuestions().forEach(questionRenderer::render);
    }
}
