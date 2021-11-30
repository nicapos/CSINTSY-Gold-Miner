package Model;

public class Action {
    private int row, col; // current row & col of miner
    private Direction front;
    private boolean bScanned;

    private int nRotates, nScans, nMoves;

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

    public void updateStats(int nRotates, int nScans, int nMoves) {
        this.nRotates = nRotates;
        this.nScans = nScans;
        this.nMoves = nMoves;
    }

    public int getRotates() { return nRotates; }

    public int getScans() { return nScans; }

    public int getMoves() { return nMoves; }

    @Override
    public String toString() {
        return "row: " + row + ", col: " + col + ", front: " + front;
    }
}
