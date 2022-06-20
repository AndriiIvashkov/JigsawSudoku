package sk.tuke.gamestudio.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solver {

    private Field fieldSolve;

    public Solver(Field fieldSolve) {
        this.fieldSolve = fieldSolve;
    }

    public void solve() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(numbers);
        fieldSolve.generateSolveField(numbers);
    }

    public Field getFieldSolve() {
        return fieldSolve;
    }
}
