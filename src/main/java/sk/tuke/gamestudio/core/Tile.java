package sk.tuke.gamestudio.core;

import java.util.ArrayList;
import java.util.List;

public class Tile {

    private TileState tileState;
    private int number;
    private int x;
    private int y;
    private int group;
    private List<Integer> marks;

    public Tile(int number, int y, int x, int group) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.group = group;
        tileState = TileState.EMPTY;
        marks = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTileState(TileState tileState) {
        this.tileState = tileState;
    }

    public TileState getTileState() {
        return tileState;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public List<Integer> getMarks() {
        return marks;
    }

}
