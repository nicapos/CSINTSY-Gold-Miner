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

    public char scan(Grid grid, int n){
        // based sa front, scan there
        char closest = '0';
        int pos = n;
        switch(front){
            case NORTH: 
                for(int i = n; i > col; i--){
                    if(grid.tileIsEmpty(row, i))
                        ;
                    else if(grid.getTerrain(row, i) != 0){
                        if(col - i < pos){
                            pos = col - i;
                            closest = grid.getTerrain(row, i);
                        }
                    }
                }
                break;
            case SOUTH: 
                for(int i = col; i < n; i++){
                    if(grid.tileIsEmpty(row, i))
                        ;
                    else if(grid.getTerrain(row, i) != 0){
                        closest = grid.getTerrain(row, i);
                        break;
                    }
                }
                break;
            case WEST: 
                for(int i = n; i > row; i--){
                    if(grid.tileIsEmpty(row, i))
                        ;
                    else if(grid.getTerrain(row, i) != 0){
                        if(col - i < pos){
                            pos = col - i;
                            closest = grid.getTerrain(i, col);
                        }
                    }
                }
                break;
            case EAST: 
                for(int i = row; i < n; i++){
                    if(grid.tileIsEmpty(row, i))
                        ;
                    else if(grid.getTerrain(row, i) != 0){
                        closest = grid.getTerrain(i, col);
                        break;
                    }
                }
                break;
        }
        return closest; // no pot, gold, pit, beacon in the front
    }

    public void rotate(){ 
        switch(front){
            case EAST: front = Direction.SOUTH; break;
            case SOUTH: front = Direction.WEST; break;
            case WEST: front = Direction.NORTH; break;
            case NORTH: front = Direction.EAST; break;
        }
    }
}
