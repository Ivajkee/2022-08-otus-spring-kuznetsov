package ru.otus.domain;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private long id;
    private String text;
    private String answer;
    private List<String> variants;
}
