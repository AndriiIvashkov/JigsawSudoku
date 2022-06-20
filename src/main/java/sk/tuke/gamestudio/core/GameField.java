package sk.tuke.gamestudio.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class GameField {

    private Field field;
    Random random = new Random();
    private int score = 0;
    private GameState gameState;
    public GameField(Field field) {
        this.field = field;
        gameState = GameState.PLAYING;
    }

    public void makeGameField(Field field) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.field.getTile(i, j).setNumber(field.getTile(i, j).getNumber());
            }
        }
        int countOfEmptyTiles;

        for (int i = 0; i < 9; i++) {
            countOfEmptyTiles = random.nextInt(3, 8);
            makeEmptyTile(countOfEmptyTiles, i);
        }
    }

    public void makeEmptyTile(int countOfEmptyTiles, int row) {

        List<Integer> numbers = new ArrayList<>(asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        int number, index, check;

        for (int i = 0; i < countOfEmptyTiles; i++) {
            check = 0;
            index = 0;
            number = random.nextInt(9);
            for (int x : numbers) {
                if (x == number) {
                    check = 1;
                    numbers.remove(index);
                    break;
                }
                index++;
            }
            if (check == 0) {
                i--;
                continue;
            }


            this.field.getTile(row, index).setNumber(0);
            this.field.getTile(row, index).setTileState(TileState.EMPTY);
        }
    }

    public Field getField() {
        return field;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
