package sk.tuke.gamestudio.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.core.*;
import sk.tuke.gamestudio.server.entity.Comment;
import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.entity.Score;
import sk.tuke.gamestudio.server.service.CommentService;
import sk.tuke.gamestudio.server.service.RatingService;
import sk.tuke.gamestudio.server.service.ScoreService;

import java.util.Date;
import java.util.Random;


@Controller
@RequestMapping("/game")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GameController {


    @Autowired
    private UserController userController;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;

    Random random = new Random();
    private int tiles = random.nextInt(4);
    private TilesField tilesField = new TilesField(tiles);
    private Solver solver = new Solver(new Field(9, new TilesField(tiles)));;
    private GameField gameField = new GameField(new Field(9, tilesField));
    private int row = 1, column = 1, number, attemps = 3, score = 0, bonus = 0;
    private Prompt prompt = new Prompt(2);;
    private boolean isCommented = false;
    private boolean isRated = false;
    private boolean isLose = false;
    private boolean isWon = false;
    private boolean isMistake = false;
    private GameState gameState = GameState.PLAYING;

    @RequestMapping("/new")
    public String newGame() {
        isWon = false;
        isLose = false;
        gameState = GameState.PLAYING;
        score = 0;
        attemps = 3;
        prompt = new Prompt(2);
        tilesField = new TilesField(tiles);
        solver = new Solver(new Field(9, new TilesField(tiles)));
        solver.solve();
        gameField = new GameField(new Field(9, tilesField));
        gameField.makeGameField(solver.getFieldSolve());
        gameField.getField().printField();
        solver.getFieldSolve().printField();
        tiles = random.nextInt(4);
        return "game";
    }

    @RequestMapping(value = "/usePrompt")
    public String usePrompt() {
        if (!isLose || !isWon)
            prompt.usePrompt(gameField.getField(), solver.getFieldSolve(), row, column);
        return "game";
    }

    @RequestMapping(value = "/press")
    public String press(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column) {
        this.row = row - 1;
        this.column = column - 1;
        System.out.println("Row: " + this.row + " Column: " + this.column);
        return "game";
    }
    @RequestMapping(value = "/getValue")
    public String getValue(@RequestParam(required = false) Integer number) {
        isMistake = false;
        this.number = number;
        Tile tile = gameField.getField().getTile(row, column);
        if (tile.getTileState() == TileState.FULL) {
            return "game";
        }
        if (number != solver.getFieldSolve().getTile(row, column).getNumber()) {
            attemps--;
            if (attemps != 0) {
                isMistake = true;
            }
            bonus = 0;
            if (attemps == 0) {
                isLose = true;
            }
            return "game";
        }
        score += 10 + 10 * bonus;
        bonus++;
        gameField.getField().getTile(row, column).setNumber(number);
        gameField.getField().getTile(row, column).setTileState(TileState.FULL);
        gameField.setScore(score);
        setGameState();
        return "game";
    }


    public String getHtmlField() {

        StringBuilder builder = new StringBuilder();
        int i, j;

        builder.append("<table>\n");

        for (i = 0; i < 9; i++) {
            // number row
            builder.append("<tr>\n");
            for (j = 0; j < 9; j++) {
                int number = gameField.getField().getTile(i, j).getNumber();

                if (i != 8 && tilesField.getField()[i][j] != tilesField.getField()[i + 1][j]
                        && (j != 8 && tilesField.getField()[i][j] != tilesField.getField()[i][j + 1])) {

                    builder.append("<td>\n");
                    builder.append("<a href='/game/press?row=" + (i + 1) + "&column=" + (j + 1) + "'>\n");
                    builder.append("<img style='border-bottom: black solid; border-right: " +
                            "black solid;' src='/images/" + number + ".png'");
                    builder.append("</a>\n");
                    builder.append("</td>\n");
                }
                else if (i != 8 && tilesField.getField()[i][j] != tilesField.getField()[i + 1][j]) {
                    builder.append("<td>\n");
                    builder.append("<a href='/game/press?row=" + (i + 1) + "&column=" + (j + 1) + "'>\n");
                    builder.append("<img style='border-bottom: black solid;' src='/images/" + number + ".png'");
                    builder.append("</a>\n");
                    builder.append("</td>\n");
                }

                else if (j != 8 && tilesField.getField()[i][j] != tilesField.getField()[i][j + 1]) {
                    builder.append("<td>\n");
                    builder.append("<a href='/game/press?row=" + (i + 1) + "&column=" + (j + 1) + "'>\n");
                    builder.append("<img style='border-right: black solid;' src='/images/" + number + ".png'");
                    builder.append("</a>\n");
                    builder.append("</td>\n");
                }
                else {
                    builder.append("<td>\n");
                    builder.append("<a href='/game/press?row=" + (i + 1) + "&column=" + (j + 1) + "'>\n");
                    builder.append("<img src='/images/" + number + ".png'");
                    builder.append("</a>\n");
                    builder.append("</td>\n");
                }
            }
            builder.append("\n");
            builder.append("</tr>\n");
        }
        builder.append("</table>\n");
        return builder.toString();
    }
    public String getButtons() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= 9; i++) {
            builder.append("<td>\n");
            builder.append("<a href='/game/getValue?number=" + i + "'>\n");
            builder.append("<img src='/images/" + i + ".png'");
            builder.append("</a>\n");
            builder.append("</td>\n");
        }
        return builder.toString();
    }

    @PostMapping("/api/comment")
    public String comment(@ModelAttribute("comment") String comment) {
        if (!isCommented) {
            commentService.addComment(new Comment(userController.getLoggedUser().getUsername(),
                    "game",
                    comment,
                    new Date()));
            isCommented = true;
        }
        return "redirect:/game";
    }
    @PostMapping("/api/rating")
    public String rating(@ModelAttribute("rating") int rating) {
        if (!isRated) {
            ratingService.setRating(new Rating("game",
                    userController.getLoggedUser().getUsername(),
                    rating,
                    new Date()));
            isRated = true;
        }
        return "redirect:/game";
    }

    public void setGameState() {
        if (attemps == 0) {
            isLose = true;
            gameState = GameState.LOSE;
            addNewScore();
        } else if (attemps > 0 && !gameField.getField().isGameWon()) {
            gameState = GameState.PLAYING;
        } else if (gameField.getField().isGameWon()) {
            isWon = true;
            gameState = GameState.WIN;
            addNewScore();
        }
    }
    public boolean isLose() {
        return isLose;
    }

    public boolean isWon() {
        return isWon;
    }

    public boolean isMistake() {
        return isMistake;
    }

    public void addNewScore() {
        if (userController.isLogged()) {
            scoreService.addScore(new Score(
                    "game",
                    userController.getLoggedUser().getUsername(),
                    score,
                    new Date()));
        }
    }
    public String printLose() {
        StringBuilder builder = new StringBuilder();
        builder.append("<td>\n");
        builder.append("<label class='error'>You lose this game!<br></label>");
        builder.append("<label class='error'>You have a " + score + " points<br></label>");
        builder.append("</td>\n");
        return builder.toString();
    }
    public String printWon() {
        StringBuilder builder = new StringBuilder();
        builder.append("<td>\n");
        builder.append("<label>Congratulations! You win this game!<br></label>");
        builder.append("<label>You have a " + score + " points<br></label>");
        builder.append("</td>\n");
        return builder.toString();
    }

    public String getRating() {
        return String.format("%.3f",ratingService.getAverageRating("game"));
    }
    public boolean isCommented() {
        return isCommented;
    }
    public boolean isRated() {
        return isRated;
    }
    public String printComments() {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Comment comment : commentService.getComments("game")) {
            if (i > 9)
                break;
            if (comment.getComment() == null)
                continue;
            builder.append("<td>\n");
            builder.append("<label>\nPlayer: ["+ comment.getPlayer() + "]\tComment: {" + comment.getComment() + "}Time: " +
                    comment.getCommentedOn() + "<br></label>");
            builder.append("</td>\n");
            i++;
        }
        return builder.toString();
    }
    public String printRatings() {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Rating rating : ratingService.getRatings()) {
            if (i > 9)
                break;
            builder.append("<td>\n");
            builder.append("<label>Player: ["+ rating.getPlayer() + "]\tRating: {" + rating.getRating() + "}Time: " +
                    rating.getRatedOn() + "<br></label>");
            builder.append("</td>\n");
            i++;
        }
        return builder.toString();
    }
    public String printScores() {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Score score : scoreService.getTopScores("game")) {
            if (i > 9)
                break;
            builder.append("<td>\n");
            builder.append("<label>Player: ["+ score.getPlayer() + "]\tPoints: {" + score.getPoints() + "}Time: " +
                    score.getPlayedOn() + "<br></label>");
            builder.append("</td>\n");
            i++;
        }
        return builder.toString();
    }


    public String gameStatus() {
        StringBuilder builder = new StringBuilder();
        setGameState();
        builder.append("<td>\n");
        builder.append("<label>Scores: " + score + "<br></label>");
        builder.append("<label>Attemps: " + attemps + "<br></label>");
        builder.append("<label>Prompts: " + prompt.getAmount() + "<br></label>");
        builder.append("<label>Game status: " + gameState + "<br></label>");
        builder.append("</td>\n");
        return builder.toString();
    }


}
