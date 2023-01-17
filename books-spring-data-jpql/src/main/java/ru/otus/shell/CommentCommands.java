package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.dto.CommentDto;
import ru.otus.service.CommentService;
import ru.otus.service.out.OutputService;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {
    private final CommentService commentService;
    private final OutputService outputService;

    @ShellMethod(value = "Show comment.", key = {"c"})
    public void showComment(@ShellOption long id) {
        CommentDto commentDto = commentService.findCommentById(id);
        printComment(commentDto);
    }

    @ShellMethod(value = "Edit comment.", key = {"edit-c"})
    public void editComment(@ShellOption long id, @ShellOption(arity = 10) String text) {
        CommentDto commentDto = new CommentDto(id, text.trim());
        CommentDto updatedComment = commentService.updateComment(commentDto);
        printComment(updatedComment);
    }

    @ShellMethod(value = "Delete comment.", key = {"del-c"})
    public void deleteComment(@ShellOption long id) {
        commentService.deleteCommentById(id);
    }

    private void printComment(CommentDto commentDto) {
        outputService.output(String.format("%d: %s", commentDto.getId(), commentDto.getText()));
    }
}
