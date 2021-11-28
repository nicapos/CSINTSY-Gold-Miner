package Model;

public class Action {
    private int row, col; // current row & col of miner
    private Direction front;
    private boolean bScanned;

    public Action (int row, int col, Direction front) {
        this.row = row;
        this.col = col;
        this.front = front;
        this.bScanned = false;
    }

    public Action (int row, int col, Direction front, boolean bScanned) {
        this.row = row;
        this.col = col;
        this.front = front;
        this.bScanned = bScanned;
    }

    public int getRow() { return this.row; }

    public int getCol() { return this.col; }

    public Direction getFront() { return this.front; }

    public boolean didScan() { return this.bScanned; }

    @Override
    public String toString() {
        return "row: " + row + ", col: " + col + ", front: " + front;
    }
}
