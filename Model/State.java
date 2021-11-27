package Model;

public class State {
    private int row, col;
    private Direction front;

    public State (int row, int col, Direction front) {
        this.row = row;
        this.col = col;
        this.front = front;
    }

    public int getRow() { return this.row; }

    public int getCol() { return this.col; }

    public Direction getFront() { return this.front; }
}
