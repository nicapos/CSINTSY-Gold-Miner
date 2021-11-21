import java.util.Random;

public class Miner {
    private Direction front;
    private int row; // = y
    private int col; // = x

    private static int nRotates, nScans, nMoves;

    public Miner(){
        Random rnd = new Random();
        row = 0;
        col = 0;
        //front = rnd.nextBoolean() ? Direction.SOUTH : Direction.EAST; // commented out temporarily for driver test lang
        front = Direction.EAST;

        nRotates = 0;
        nScans = 0;
        nMoves = 0;
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
    }

    public char scan(Grid grid) {
        // based sa front, scan there
        char closest = '0';
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
                for(int i = row; i < n; i++){
                    if(grid.tileIsEmpty(col, i))
                        ;
                    else if(grid.getTerrain(col, i) != 0){
                        closest = grid.getTerrain(col, i);
                        break;
                    }
                }
                break;
            case WEST: 
                for(int i = n; i > col; i--){
                    if(grid.tileIsEmpty(i, row))
                        ;
                    else if(grid.getTerrain(i, row) != 0){
                        closest = grid.getTerrain(i, row);
                        break;
                    }
                }
                break;
            case EAST: 
                for(int i = col; i < n; i++){
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
    }
}
