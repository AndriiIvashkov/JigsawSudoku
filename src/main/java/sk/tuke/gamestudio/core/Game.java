package sk.tuke.gamestudio.core;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.server.service.CommentService;
import sk.tuke.gamestudio.server.service.RatingService;
import sk.tuke.gamestudio.server.service.ScoreService;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Game {

    private final Solver solver;
    private String command;
    private int x, y, number;
    private int attemps;
    private Prompt prompt;
    private GameField gameField;
    private TilesField tilesField;
    private int bonus = 0;
    private int score;
    private String name;
    private String comment;
    private int rating;
    private Date date;
    Scanner scanner = new Scanner(System.in);

    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private ScoreService scoreService;

    public Game(Solver solver, TilesField tilesField) {
        this.solver = solver;
        this.tilesField = tilesField;
    }

    private void getData() {
        scanner = new Scanner(System.in);
        this.command = scanner.next();
        this.x = scanner.nextInt();
        this.y = scanner.nextInt();
        this.number = scanner.nextInt();
    }



    public void playGame() {
        System.out.println("Enter your name:");
        name = scanner.nextLine();
        solver.solve();
        gameField = new GameField(new Field(9, tilesField));
        gameField.makeGameField(solver.getFieldSolve());
        prompt = new Prompt(2);


        //solver.getFieldSolve().printField();
        attemps = 3;
        score = gameField.getScore();

        printCommands();

        do {
            this.getData();

            if (!checkData() && !this.command.toLowerCase().equals("exit")) {
                System.out.println("Invalid data! Try to enter again: ");
                continue;
            }
            x--;
            y--;
            switch (command.toLowerCase()) {
                case "mark":
                    mark(x, y, number);
                    break;
                case "unmark":
                    unmark(x, y, number);
                    break;
                case "viewmarks":
                    System.out.println("Marks: " + Arrays.toString(gameField.getField().getTile(x, y).getMarks().toArray()));
                    printCommands();
                    System.out.println("Enter your command:");
                    break;
                case "input":
                    input(x, y, number);
                    break;
                case "prompt":
                    prompt.usePrompt(gameField.getField(), solver.getFieldSolve(), x, y);
                    printCommands();
                    System.out.println("Enter your command:");
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Enter the correct command: ");
                    break;
            }
            if (attemps == 0) {
                gameField.setGameState(GameState.LOSE);
            } else if (gameField.getField().isGameWon()) {
                gameField.setGameState(GameState.WIN);
            }
        } while (!this.command.toLowerCase().equals("exit") && gameField.getGameState() != GameState.LOSE && gameField.getGameState() != GameState.WIN);

        if (gameField.getField().isGameWon()) {
            System.out.println("Congatultaions!!! You win this game.");
            score *= 3;
        } else if (attemps == 0) {
            System.out.println("You lose this game!");
        } else {
            System.out.println("Thanks for game! Bye");
        }
        date = new Timestamp(new Date().getTime());
        scanner = new Scanner(System.in);
        System.out.println("Do you want rate this game? Yes / No");
        if ((scanner.nextLine()).toLowerCase().equals("yes")) {
            System.out.println("Rating from 1 to 5: ");
            rating = scanner.nextInt();

            if (rating > 5)
                rating = 5;

            if (rating < 1)
                rating = 1;

        }
        scanner = new Scanner(System.in);
        System.out.println("Do you want to write a comment to this game? Yes / No");
        if ((scanner.nextLine()).toLowerCase().equals("yes")) {
            System.out.println("Comment: ");
            comment = scanner.nextLine();
        }
    }

    public void mark(int x, int y, int number) {
        Tile tile = gameField.getField().getTile(x, y);
        if (tile.getTileState() == TileState.FULL) {
            if (tile.getTileState() == TileState.FULL) {
                tile.getMarks().removeAll(tile.getMarks());
                System.out.println("The tile is full!");
                System.out.println("Enter the correct command: ");
                return;
            }
            return;
        }
        int exist = 0;
        if (gameField.getField().getTile(x, y).getMarks().contains(number)) {
            tile.setTileState(TileState.MARKED);
            tile.getMarks().add(number);
            exist = 1;
        }
        if (exist == 0)
            System.out.println("This number already exits in marks!");
        printCommands();
        System.out.println("Enter your command:");
    }

    public void unmark(int x, int y, int number) {
        Tile tile = gameField.getField().getTile(x, y);
        if (tile.getTileState() == TileState.FULL) {
            tile.getMarks().removeAll(tile.getMarks());
            System.out.println("The tile is full!");
            System.out.println("Enter the correct command: ");
            return;
        }
        int exist = 0;

        if (gameField.getField().getTile(x, y).getMarks().contains(number)) {
            tile.getMarks().remove(number);
            exist = 1;
        }

        if (exist == 0)
            System.out.println("This number doesnt exits in marks!");
        else if (tile.getMarks().isEmpty())
            tile.setTileState(TileState.EMPTY);
        else
            tile.setTileState(TileState.MARKED);

        printCommands();
        System.out.println("Enter your command:");
    }

    public void input(int x, int y, int number) {
        Tile tile = gameField.getField().getTile(x, y);
        if (tile.getTileState() == TileState.FULL) {
            System.out.println("The tile is full!");
            System.out.println("Enter the correct command: ");
            return;
        }
        if (number != solver.getFieldSolve().getTile(x, y).getNumber()) {
            attemps--;
            printCommands();
            System.out.println("Invalid input!");
            bonus = 0;
            return;
        }
        score += 10 + 10 * bonus;
        bonus++;
        gameField.getField().getTile(x, y).setNumber(number);
        gameField.getField().getTile(x, y).setTileState(TileState.FULL);
        gameField.setScore(score);
        printCommands();
        System.out.println("Enter your command:");
    }

    public boolean checkData() {
        return this.x <= 9 && this.x > 0 && this.y <= 9 && this.y > 0 && this.number <= 9 && this.number >= 0;
    }

    public void printCommands() {
        gameField.getField().printField();
        System.out.println("You have " + attemps + " attemps, " + prompt.getAmount() + " prompts\n");
        System.out.println("Your score: " + score + "\n");
        System.out.println("Input <row> <col> <number>");
        System.out.println("Mark <row> <col> <number>");
        System.out.println("Unmark <row> <col> <number>");
        System.out.println("Viewmarks <row> <col> 0");
        System.out.println("Prompt <row> <col> 0");
        System.out.println("Exit 0 0 0");

    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public int getAttemps() {
        return attemps;
    }

    public Prompt getPrompt() {
        return prompt;
    }

}
