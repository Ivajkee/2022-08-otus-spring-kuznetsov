package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.dto.CommentDto;
import ru.otus.service.CommentService;
import ru.otus.service.out.OutputService;

import java.util.List;

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

    @ShellMethod(value = "Show comments for book.", key = {"c-b"})
    public void showComments(@ShellOption long bookId) {
        List<CommentDto> commentsDto = commentService.findCommentsByBookId(bookId);
        commentsDto.forEach(this::printComment);
    }

    @ShellMethod(value = "Add comment to book.", key = {"add-c"})
    public void addComment(@ShellOption long bookId, @ShellOption(arity = 10) String text) {
        CommentDto commentDto = new CommentDto(bookId, text.trim());
        CommentDto savedCommentDto = commentService.saveComment(bookId, commentDto);
        printComment(savedCommentDto);
    }

    @ShellMethod(value = "Edit comment.", key = {"edit-c"})
    public void editComment(@ShellOption long id, @ShellOption(arity = 10) String text) {
        CommentDto commentDto = new CommentDto(id, text.trim());
        CommentDto updatedCommentDto = commentService.updateComment(commentDto);
        printComment(updatedCommentDto);
    }

    @ShellMethod(value = "Delete comment.", key = {"del-c"})
    public void deleteComment(@ShellOption long id) {
        commentService.deleteCommentById(id);
    }

    private void printComment(CommentDto commentDto) {
        outputService.output(String.format("%d: %s", commentDto.getId(), commentDto.getText()));
    }
}
