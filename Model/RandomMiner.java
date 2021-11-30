package Model;
import java.util.Random;

public class RandomMiner extends Miner{
    private Grid envGrid;
    private boolean bAlive;
    private boolean bMetGoal;

    public RandomMiner(Grid env){
        envGrid = env;
        bAlive = true;
        bMetGoal = false;
    }

    public String startSearch(){
        Random rand = new Random();
        Node previous = new Node(0, 0, this.getFront());
        int MAX_DEPTH = envGrid.getSize() * envGrid.getSize();
        if(MAX_DEPTH > 100)
            MAX_DEPTH = 100;
        String searchMsg = null;
        
        while (bAlive && !bMetGoal && MAX_DEPTH > 0 && searchMsg == null){
            int action = rand.nextInt() % 3;
            switch(action){
                case 0: if ( isMoveValid(front) ) {
                            move();
                            if(envGrid.getTerrain(col, row) == 'P')
                                searchMsg = "Game-over!";
                            else if(envGrid.getTerrain(col, row) == 'G')
                                searchMsg = "Search successful";
                            break;
                        }
                case 1: rotate(); break;
                case 2: scan(envGrid); break;
            }
            bMetGoal = previous.getTerrain() == 'G';
            bAlive = !(previous.getTerrain() == 'P');
            if(!bMetGoal && bAlive){
                envGrid.printGrid(this);
                System.out.println();
            }
            MAX_DEPTH--;

        }
        System.out.println("Rotates: "+this.getRotates()+", Scans: "+this.getScans()+", Moves: "+this.getMoves());
        
        if (previous.getTerrain() == 'G')
            searchMsg = "Search successful";
        else if (previous.getTerrain() == 'P')
            searchMsg = "Game-over!";
        else // default message if search ended without miner landing in gold or pit
            searchMsg = "Ended search.";
        System.out.println(searchMsg);
        return searchMsg;
    }

    private boolean isMoveValid(Direction moveDirection) {
        switch (moveDirection) {
            case EAST:  return (col + 1 < envGrid.getSize());
            case SOUTH: return (row + 1 < envGrid.getSize()); 
            case WEST:  return (col - 1 >= 0);
            case NORTH: return (row - 1 >= 0);
            default:    return false; // added to prevent compilation error
        }
    }
}
