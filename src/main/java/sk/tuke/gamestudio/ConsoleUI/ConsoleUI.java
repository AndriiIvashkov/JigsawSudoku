package sk.tuke.gamestudio.ConsoleUI;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.core.Game;
import sk.tuke.gamestudio.core.TilesField;
import sk.tuke.gamestudio.server.entity.Comment;
import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.entity.Score;
import sk.tuke.gamestudio.server.service.CommentService;
import sk.tuke.gamestudio.server.service.RatingService;
import sk.tuke.gamestudio.server.service.ScoreService;

import java.util.Random;
import java.util.Scanner;

public class ConsoleUI {
    @Autowired
    private Game game;

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;

    public ConsoleUI(){

    }

    public void startMenu() throws Exception {

        Random random = new Random();
        Scanner in = new Scanner(System.in);
        String input;
        printAll();
        System.out.println("\nHello, welcome to Jigsaw sudoku!\n");
        System.out.println("Play");
        System.out.println("Exit");
        TilesField tilesField = new TilesField(random.nextInt(4));
        do {
            input = in.nextLine();
            switch (input.toLowerCase()) {
                case "play":
                    game.playGame();
                    scoreService.addScore(new Score("game", game.getName(), game.getScore(), game.getDate()));
                    ratingService.setRating(new Rating("game", game.getName(), game.getRating(), game.getDate()));
                    commentService.addComment(new Comment("game", game.getName(), game.getComment(), game.getDate()));
                    System.exit(0);
                    break;
                case "exit":
                    System.out.println("Thanks for game! Bye");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Enter the correct command: ");
                    break;
            }
        } while (!input.equals("exit"));

    }

    public void printAll() {
        System.out.println("Comments: ");
        for (Comment comment : commentService.getComments("game")) {
            System.out.println(comment.getComment() + " " + comment.getPlayer() + "   " + comment.getCommentedOn());
        }
        System.out.println("\nAverage rating of game: " + String.format("%.2f", ratingService.getAverageRating("game")));
        System.out.println("\nScores: ");
        for (Score score : scoreService.getTopScores("game")) {
            System.out.println(score.getPlayer() + ": " + score.getPoints() + "   " + score.getPlayedOn());
        }

    }
}
