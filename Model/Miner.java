package Model;
import java.util.Random;
import java.util.ArrayList;

public class Miner {
    private Direction front;
    private int row; // = y
    private int col; // = x

    private static int nRotates, nScans, nMoves;

    private ArrayList<State> states;

    public Miner(){
        Random rnd = new Random();
        row = 0;
        col = 0;
        front = rnd.nextBoolean() ? Direction.SOUTH : Direction.EAST;

        nRotates = 0;
        nScans = 0;
        nMoves = 0;

        states = new ArrayList<State>(); 
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

    public ArrayList<State> getStates() { return states; }

    private void addCurrentState() {
        State newState = new State(this.row, this.col, this.front);
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
        char closest = 0;
        int n = grid.getSize();
        switch(front){
            case NORTH: 
                for(int i = n-1; i > row; i--){
                    if(grid.tileIsEmpty(col, i))
                        ;
                    else if(grid.getTerrain(col, i) != 0){
                        closest = grid.getTerrain(col, i);
                        break;
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
                for(int i = n-1; i > col; i--){
                    if(grid.tileIsEmpty(i, row))
                        ;
                    else if(grid.getTerrain(i, row) != 0){
                        closest = grid.getTerrain(i, row);
                        break;
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
        return closest; // no pot, gold, pit, beacon in the front
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
