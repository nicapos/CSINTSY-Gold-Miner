import java.util.Random;

public class Miner {
    private Direction front;
    private int row;
    private int col;

    public Miner(){
        Random rnd = new Random();
        row = 0;
        col = 0;
        front = rnd.nextBoolean() ? Direction.SOUTH : Direction.EAST;
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

    public char scan(){
        // based sa front, scan there
        return '\0'; // no pot, gold, pit, beacon in the front
    }

    public void rotate(){ 
        switch(front){
            case EAST: front = Direction.SOUTH; break;
            case SOUTH: front = Direction.WEST; break;
            case WEST: front = Direction.NORTH; break;
            case NORTH: front = Direction.EAST; break;
            default: break;
        }
    }
}
