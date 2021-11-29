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

    public void startSearch(){
        Random rand = new Random();
        Node previous = new Node(0, 0, this.getFront());
        int MAX_DEPTH = envGrid.getSize() * (envGrid.getSize()/2); // EDIT NOTE: temporary, just to prevent infinite loop (formula is random lang lol change if necessary)
        while(bAlive && !bMetGoal && MAX_DEPTH > 0){
            int action = rand.nextInt() % 3;
            switch(action){
                case 0: switch(front){
                            case EAST: if(col+1 < envGrid.getSize()); 
                                        move(); 
                                        if(envGrid.getTerrain(col, row) == 'P') // temp for explain
                                            System.out.println("DEATH");
                                        else if(envGrid.getTerrain(col, row) == 'G')
                                            System.out.println("Miner WON");
                                        break;
                            case SOUTH: if(row+1 < envGrid.getSize()); 
                                        move(); 
                                        if(envGrid.getTerrain(col, row) == 'P') // temp for explain
                                            System.out.println("DEATH");
                                        else if(envGrid.getTerrain(col, row) == 'G')
                                            System.out.println("Miner WON");
                                        break;
                            case WEST: if(col-1 >= 0)
                                        move(); 
                                        if(envGrid.getTerrain(col, row) == 'P') // temp for explain
                                            System.out.println("DEATH");
                                        else if(envGrid.getTerrain(col, row) == 'G')
                                            System.out.println("Miner WON");
                                        break;
                            case NORTH: if(row-1 >= 0)
                                        move(); 
                                        if(envGrid.getTerrain(col, row) == 'P') // temp for explain
                                            System.out.println("DEATH");
                                        else if(envGrid.getTerrain(col, row) == 'G')
                                            System.out.println("Miner WON");
                                        break;
                            default: break;
                        }
                        break;
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
            System.out.println("Search successful");
        else if (previous.getTerrain() == 'P')
            System.out.println("Game-over!");
        else // somehow didn't end up in gold or pit
            System.out.println("Ended search.");
    }
}
