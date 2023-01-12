package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.dto.CommentDto;
import ru.otus.service.CommentService;
import ru.otus.service.io.OutputService;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {
    private final CommentService commentService;
    private final OutputService outputService;

    @ShellMethod(value = "Show all comments.", key = {"all-c"})
    public void showComments() {
        commentService.findAllComments().forEach(commentDto -> outputService.output(String.format("%d: %s", commentDto.getId(),
                commentDto.getText())));
    }

    @ShellMethod(value = "Show comment.", key = {"c"})
    public void showComment(@ShellOption long id) {
        CommentDto commentDto = commentService.findCommentById(id);
        outputService.output(String.format("%d: %s", commentDto.getId(), commentDto.getText()));
    }

    @ShellMethod(value = "Show count of comments.", key = {"count-c"})
    public void showCountOfComments() {
        long count = commentService.getCountOfComments();
        outputService.output(count);
    }

    @ShellMethod(value = "Add comment.", key = {"add-c"})
    public void addComment(@ShellOption(arity = 10) String text) {
        CommentDto commentDto = new CommentDto(text);
        CommentDto addedComment = commentService.saveComment(commentDto);
        outputService.output(String.format("%d: %s", addedComment.getId(), addedComment.getText()));
    }

    @ShellMethod(value = "Edit comment.", key = {"edit-c"})
    public void editComment(@ShellOption long id, @ShellOption(arity = 10) String text) {
        CommentDto commentDto = new CommentDto(id, text);
        CommentDto updatedComment = commentService.updateComment(commentDto);
        outputService.output(String.format("%d: %s", updatedComment.getId(), updatedComment.getText()));
    }

    @ShellMethod(value = "Delete comment.", key = {"del-c"})
    public void deleteComment(@ShellOption long id) {
        commentService.deleteCommentById(id);
    }
}