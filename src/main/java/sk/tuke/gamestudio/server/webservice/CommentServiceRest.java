package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.server.entity.Comment;
import sk.tuke.gamestudio.server.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{game}")
    public List<Comment> getAllComments(@PathVariable String game) {
        return commentService.getComments(game);
    }
    @PostMapping
    public void addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
    }


}
