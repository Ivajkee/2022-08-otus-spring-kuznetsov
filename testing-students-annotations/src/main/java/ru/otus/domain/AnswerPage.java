package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerPage {
    private Answer answer;
    private Answer answered;

    public boolean isCorrect() {
        return answer.getText().equalsIgnoreCase(answered.getText());
    }
}
