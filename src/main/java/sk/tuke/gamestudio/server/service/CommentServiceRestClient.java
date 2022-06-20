package sk.tuke.gamestudio.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.server.entity.Comment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommentServiceRestClient implements CommentService {
    private static final String url = "http://localhost:8080/api/comment";
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void addComment(Comment comment) throws CommentException {
        restTemplate.postForEntity(url, comment, Comment.class);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(url + "/" + game, Comment[].class).getBody()));
    }

    @Override
    public void reset() throws CommentException {
        throw new UnsupportedOperationException("Not supported via web service");
    }

}
