package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String firstname;
    private String lastname;

    public String getFullName() {
        return String.format("%s %s", firstname, lastname);
    }
}
