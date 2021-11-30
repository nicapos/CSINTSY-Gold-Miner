package Model;
import java.util.ArrayList;
import java.util.Stack;

public class SmartMiner extends Miner {
    private Grid envGrid; // = environment, will only be used for scan; not the miner's copy of what it knows abt the environment
    private ArrayList<Node> moves;
    private boolean bMetGoal;

    public SmartMiner (Grid env) {
        moves = new ArrayList<Node>();
        bMetGoal = false;
        this.envGrid = env;
    }

    public ArrayList<Node> getSearchMoves() { return moves; }

    /**
     * Starts the miner's search for the gold and returns a search message depending on the result of the search.
     * @return the result of the search via a search message
     */
    public String startSearch() { // automatic search until miner finds gold;
        Stack<Node> fringe = new Stack<Node>();
        int nVisited = 0;

        fringe.add( new Node(0, 0, getFront(), null) );
        envGrid.printGrid(this);
        System.out.println();

        while ( !fringe.isEmpty() && !bMetGoal && nVisited < envGrid.getSize() * envGrid.getSize() ) {
            System.out.println(fringe);
            Node v = fringe.pop();
            if (!v.isVisited()) {
                if ( v.getParent() != null )
                    moveTo(v);
                v.markAsVisted();
                nVisited++;

                if ( envGrid.getTerrain(get_col(), get_row()) == 'G' || envGrid.getTerrain(get_col(), get_row()) == 'P' )
                    bMetGoal = true;
                else {
                    for (Node successor : createSuccessors(v))
                        fringe.push(successor);
                }
            }

            envGrid.printGrid(this);
            System.out.println();
        }

        String searchMsg;
        if ( envGrid.getTerrain(get_col(), get_row()) == 'G' )
            searchMsg = "Search successful";
        else if ( envGrid.getTerrain(get_col(), get_row()) == 'P' )
            searchMsg = "Game over";
        else
            searchMsg = "Ended search";
        
        return searchMsg;
    }

    // returns true if move is successful
    public boolean moveTo(Node targetNode) {
        Direction moveDirection = null;
        
        if (targetNode.getCol() == col) {
            if (targetNode.getRow() == row-1)
                moveDirection = Direction.NORTH;
            else if (targetNode.getRow() == row+1)
                moveDirection = Direction.SOUTH;
        } else if (targetNode.getRow() == row) {
            if (targetNode.getCol() == col-1)
                moveDirection = Direction.WEST;
            else if (targetNode.getCol() == col+1)
                moveDirection = Direction.EAST;
        }

        if (moveDirection == null)  return false;

        // rotate until facing moveDirection then move
        while ( getFront() != moveDirection )
            rotate();
        move();
        return true;
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

                    /* stop scanning if found gold already or parent node is already in gold tile and return only the node which scanned gold */
                    if (cScan == 'G' || parent.getTerrain() == 'G') {
                        successors.clear();
                        successors.add(child);
                        return successors;
                    }
                }
            } 
            this.rotate();
        }

        /* sort successors from least priority to highest priority */
        if (successors.size() > 1) {
            for (int i = 0; i < successors.size(); i++) {
                int index = i;
                for (int j = i+1; j < successors.size(); j++) {
                    if (successors.get(j).getPriorityValue() < successors.get(index).getPriorityValue())
                        index = j;
                }
                Node temp = successors.get(index);
                successors.set(index, successors.get(i));
                successors.set(i, temp);
            } 
        }

        return successors;
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