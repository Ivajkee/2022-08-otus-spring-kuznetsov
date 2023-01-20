package ru.otus.service.out;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class OutputServiceImpl implements OutputService {
    private final PrintStream output;

    public OutputServiceImpl(@Value("#{T(java.lang.System).out}") PrintStream outputStream) {
        output = outputStream;
    }

    @Override
    public void output(Object o) {
        output.println(o);
    }
}
