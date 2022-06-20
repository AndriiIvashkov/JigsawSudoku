package sk.tuke.gamestudio.core;

import java.util.*;

public class Field {

    private final Tile[][] tiles;
    private final int size;

    private TilesField tilesField;
    Solver solver = new Solver(this);
    Random rand = new Random();

    public Field(int size, TilesField tilesField) {
        this.size = size;
        tiles = new Tile[size][size];
        this.tilesField = tilesField;
        generate();
    }

    //generate Tiles
    public void generate() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.tiles[i][j] = new Tile(0, i, j, tilesField.getField()[i][j]);
                this.tiles[i][j].setGroup(tilesField.getField()[i][j]);
            }
        }
    }

    //generate nums in tiles
    public boolean generateSolveField(List<Integer> numbers) {

        int group, num;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (getTile(i, j).getNumber() == 0) {
                    for (int k = 0; k < 9; k++) {
                        num = numbers.get(k);
                        group = getTile(i, j).getGroup();
                        if (checkNumber(num, i, j, group)) {
                            getTile(i, j).setNumber(num);
                            if (generateSolveField(numbers)) {
                                getTile(i, j).setTileState(TileState.FULL);
                                return true;
                            } else {
                                getTile(i, j).setNumber(0);
                                getTile(i, j).setTileState(TileState.EMPTY);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // all checks
    public boolean checkNumber(int number, int row, int col, int group) {
        return checkRow(number, row) && checkCol(number, col) && checkGroup(number, group);
    }

    // check number in row
    public boolean checkRow(int number, int row) {
        for (int i = 0; i < 9; i++) {
            if (tiles[row][i].getNumber() == number) {
                return false;
            }
        }
        return true;
    }

    // check number in coloumn
    public boolean checkCol(int number, int col) {
        for (int i = 0; i < 9; i++) {
            if (tiles[i][col].getNumber() == number) {
                return false;
            }
        }
        return true;
    }

    // check number in group of tile
    public boolean checkGroup(int number, int group) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tiles[i][j].getNumber() == number && tilesField.getField()[i][j] == group) {
                    return false;
                }
            }
        }
        return true;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    // print field
    public void printField() {
        StringBuilder builder = new StringBuilder();
        int i, j;

        for (i = 0; i < 9; i++) {
            builder.append(" -").append(i + 1).append("-");
        }
        builder.append("\n");

        for (i = 0; i < 9; i++) {
            builder.append(" ---");
        }
        builder.append("\n");

        for (i = 0; i < 9; i++) {
            // number row
            builder.append("|");
            for (j = 0; j < 9; j++) {
                int number = tiles[i][j].getNumber();
                if (number > 0)
                    builder.append(" ").append(number).append(" ");
                else
                    builder.append("   ");
                if (j != 8 && tilesField.getField()[i][j] == tilesField.getField()[i][j + 1])
                    builder.append(" ");
                else if (j == 8)
                    builder.append("|-").append(i + 1).append('-');
                else
                    builder.append("|");
            }
            builder.append("\n");

            builder.append(" ");
            for (j = 0; j < 9; j++) {
                if (i != 8 && tilesField.getField()[i][j] == tilesField.getField()[i + 1][j])
                    builder.append("    ");
                else
                    builder.append("--- ");
            }
            builder.append("\n");
        }
        System.out.println(builder.toString());
    }

    // check is game won
    public boolean isGameWon() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tiles[i][j].getNumber() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

}