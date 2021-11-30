package Model;
import java.util.Random;
import java.util.ArrayList;

public class Miner {
    protected Direction front;
    protected int row; // = y
    protected int col; // = x

    private static int nRotates, nScans, nMoves;

    private ArrayList<Action> states;

    public Miner(){
        Random rnd = new Random();
        row = 0;
        col = 0;
        front = rnd.nextBoolean() ? Direction.SOUTH : Direction.EAST;

        nRotates = 0;
        nScans = 0;
        nMoves = 0;

        states = new ArrayList<Action>(); 
        addCurrentState(); 
    }

    public void set_row(int row){
        this.row = row;
    }

    public void set_col(int col){
        this.col = col;
    }

    public int get_row(){
        return row;
    }

    public int get_col(){
        return col;
    }

    public Direction getFront() {
        return front;
    }

    public ArrayList<Action> getStates() { return states; }

    private void addCurrentState() {
        Action newState = new Action(this.row, this.col, this.front);
        newState.updateStats(nRotates, nScans, nMoves);
        this.states.add(newState);
    }

    public int getRotates() { return nRotates; }
    public int getScans() { return nScans; }
    public int getMoves() { return nMoves; }

    public void move() {
        switch(front){
            case EAST: col++; break;
            case SOUTH: row++; break;
            case WEST: col--; break;
            case NORTH: row--; break;
        }
        nMoves++;
        addCurrentState();
    }

    public char scan(Grid grid) {
        // based sa front, scan there
        char closest = '0';
        int n = grid.getSize();
        switch(front){
            case NORTH: 
                for (int i = 0; i < row; i++) {
                    if(grid.tileIsEmpty(col, i))
                        ;
                    else if(grid.getTerrain(col, i) != 0){
                        closest = grid.getTerrain(col, i);
                    }
                }
                break;
            case SOUTH: 
                for(int i = row+1; i < n; i++){
                    if(grid.tileIsEmpty(col, i))
                        ;
                    else if(grid.getTerrain(col, i) != 0){
                        closest = grid.getTerrain(col, i);
                        break;
                    }
                }
                break;
            case WEST: 
                for (int i = 0; i < col; i++) {
                    if(grid.tileIsEmpty(i, row))
                        ;
                    else if(grid.getTerrain(i, row) != 0){
                        closest = grid.getTerrain(i, row);
                    }
                }
                break;
            case EAST: 
                for(int i = col+1; i < n; i++){
                    if(grid.tileIsEmpty(i, row))
                        ;
                    else if(grid.getTerrain(i, row) != 0){
                        closest = grid.getTerrain(i, row);
                        break;
                    }
                }
                break;
        }
        nScans++;

        Action newState = new Action(this.row, this.col, this.front, true);
        newState.updateStats(nRotates, nScans, nMoves);
        this.states.add(newState);

        return closest; // no pot, gold, pit, beacon in the front -> returns 0
    }

    public void rotate(){ 
        switch(front){
            case EAST: front = Direction.SOUTH; break;
            case SOUTH: front = Direction.WEST; break;
            case WEST: front = Direction.NORTH; break;
            case NORTH: front = Direction.EAST; break;
        }
        nRotates++;
        addCurrentState();
    }
}
