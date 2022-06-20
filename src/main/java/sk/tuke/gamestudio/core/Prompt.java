package sk.tuke.gamestudio.core;

public class Prompt {
    private int amount;

    public Prompt(int amount) {
        this.amount = amount;
    }

    public int usePrompt(Field field, Field solveField, int x, int y) {

        if (amount == 0) {
            System.out.println("You have not prompt");
            System.out.println("Enter the correct command:");
            return amount;
        }
        Tile tile = field.getTile(x, y);
        if (tile.getTileState() == TileState.FULL) {
            System.out.println("The tile is full!");
            System.out.println("Enter the correct command: ");
            return amount;
        }
        tile.setNumber(solveField.getTile(x, y).getNumber());
        tile.setTileState(TileState.FULL);
        this.amount--;
        return amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
