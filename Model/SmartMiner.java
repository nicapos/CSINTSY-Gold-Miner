package Model;
import java.util.ArrayList;

public class SmartMiner extends Miner {
    private Grid envGrid; // = environment, will only be used for scan; not the miner's copy of what it knows abt the environment

    private ArrayList<Node> moves;
    private boolean bMetGoal;

    public SmartMiner (Grid environment) {
        envGrid = environment;
        moves = new ArrayList<Node>();
        bMetGoal = false;
    }

    public ArrayList<Node> getSearchMoves() { return moves; }

    public void startSearch() { // automatic search until miner finds gold;
        Node search;
        Node previous = new Node(0, 0, this.getFront());
        int MAX_DEPTH = envGrid.getSize() * (envGrid.getSize()/2); // EDIT NOTE: temporary, just to prevent infinite loop (formula is random lang lol change if necessary)

        envGrid.printGrid(this); // print current state
        System.out.println();

        while (!bMetGoal && MAX_DEPTH > 0) {
            search = getNextMove(previous);
            // rotate to the direction of search then move
            while ( this.getFront() != search.getFront() )
                this.rotate();
            this.move();

            bMetGoal = previous.getTerrain() == 'G' || previous.getTerrain() == 'P';
            if (!bMetGoal) {
                previous = search;
                envGrid.printGrid(this); // print current state
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

    public Node getNextMove(Node previous) {
        if (bMetGoal)   return null;

        ArrayList<Node> possibleMoves = createSuccessors(previous);
        Node nextMove;
        System.out.println("1: " + possibleMoves);

        /* if there's more than one candidate for next move, only keep the most optimal move. (best case: 1 left, worst case: 3 left) */
        if (possibleMoves.size() > 1)
            possibleMoves = filterByScan(possibleMoves);
        System.out.println("2: " + possibleMoves);
        
        /* if there's more than one candidate for next move, only keep the most optimal direction (best case: 1 left, worst case: 0 left) */
        if (possibleMoves.size() > 1)
            nextMove = filterByDirection(possibleMoves);
        else
            nextMove = possibleMoves.get(0);
        System.out.println("3: " + nextMove);
        return nextMove;
    }

    /**
     * Creates an ArrayList of 1-3 child nodes (1 for each direction minus for the direction opposite to
     * the parent Node's direction). If the next tile in the direction is out of bounds, no child will be
     * create in that direction. Maximum size of resulting ArrayList is 3. (b = 3)
     * @param parent 
     * @return an ArrayList of possible moves based on the parent Node
     */
    private ArrayList<Node> createSuccessors(Node parent) {
        ArrayList<Node> successors = new ArrayList<Node>();

        for (int i = 0; i < 4; i++) {
            Node child = parent.createChild(this.getFront());

            /* scan and add to possible moves only if possible move is in bounds AND is not in the direction opposite to the parent Node's front */
            if ( envGrid.tileIsInBounds(child.getRow(), child.getCol()) ) {
                if (child.getFront() != getOpposite(parent.getFront()) ) {
                    char cScan = this.scan(envGrid);
                    child.setScan(cScan);
                    successors.add(child);
                }
            } else if ( child.getFront() == parent.getFront() ) {
                parent.setTerrain(parent.getScan());
            }
            this.rotate();
        }

        return successors;
    }

    /**
     * Filters the ArrayList of nodes passed in the parameter and returns an ArrayList of the best nodes 
     * based on scan. Priority of scans (from best to worst): 'G', 'B', 0, 'P'.
     * @param candidates an ArrayList of nodes to filter
     * @return an ArrayList of the best nodes based on scan
     */
    private ArrayList<Node> filterByScan(ArrayList<Node> candidates) {
        char cBest = 'P';   // scan w/ least priority
        for (Node candidate: candidates)
            if ( getPriority( candidate.getScan() ) >= getPriority(cBest) )
                cBest = candidate.getScan();

        ArrayList<Node> bestRemaining = new ArrayList<>();
        for (Node candidate: candidates)
            if (candidate.getScan() == cBest)
                bestRemaining.add(candidate);
        return bestRemaining;
    }

    /**
     * Filters the ArrayList of nodes passed in the parameter and returns the best Node based on the 
     * Directions. Priority of Directions (from best to worst): EAST, SOUTH, WEST, NORTH.
     * @param candidates an ArrayList of nodes to filter
     * @return the best node based on direction
     */
    private Node filterByDirection(ArrayList<Node> candidates) {
        Direction dBest = null;
        for (Node candidate: candidates)
            if ( getPriority( candidate.getFront() ) > getPriority(dBest) )
                dBest = candidate.getFront();

        for (Node candidate: candidates)
            if (candidate.getFront() == dBest)
                return candidate;
        return null; // added to prevent compilation errors
    }

    private int getPriority (Direction direction) {
        if (direction == null)  return 0;
        switch (direction) {
            case EAST:  return 4;
            case SOUTH: return 3;
            case WEST:  return 2;
            case NORTH: return 1;
            default:    return 0;   // added to prevent compilation error
        }
    }

    private int getPriority (char cScan) {
        switch (cScan) {
            case 'G':   return 4;
            case 'B':   return 3;
            case 0:     return 2;
            case 'P':   return 0;
            default:    return 1;   // added to prevent compilation error
        }
    }

    private Direction getOpposite (Direction direction) {
        switch (direction) {
            case EAST:  return Direction.WEST;
            case SOUTH: return Direction.NORTH;
            case WEST:  return Direction.EAST;
            case NORTH: return Direction.SOUTH;
            default:    return Direction.SOUTH; // added to prevent compilation error
        }
    }
}