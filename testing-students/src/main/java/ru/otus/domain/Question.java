package ru.otus.domain;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private long id;
    private String text;
    @JsonUnwrapped(prefix = "answer.")
    private Answer answer;
    @JsonUnwrapped(prefix = "variants")
    private List<Answer> variants;
}
