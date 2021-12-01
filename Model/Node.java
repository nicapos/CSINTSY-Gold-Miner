package Model;
public class Node {
    private Node parent;
    
    private int col;
    private int row;
    private Direction moveFront;

    private char cScan;

    private boolean isVisited;

    public Node (int col, int row, Direction moveFront, Node parent) {
        this.col = col;
        this.row = row;
        this.moveFront = moveFront;
        cScan = '0';
        this.parent = parent;
    }

    public int getCol() { return col; }

    public int getRow() { return row; }

    public Direction getFront() { return moveFront; }

    public char getScan() { return cScan; }

    public Node getParent() { return parent; }

    public boolean isVisited() {
        return isVisited;
    }

    public void markAsVisted() {
        isVisited = true;
    }

    /**
     * Sets the scan result in this node's direction. This also updates the parent's terrain if this node's
     * direction is the same as the parent node's direction and their scan results are different.
     * @param scanResult
     */
    public void setScan(char scanResult) {
        this.cScan = scanResult;
    }

    /**
     * Creates a child to this node, with its parent set as this node. Returns null if the child
     * in the given direction is out of bounds.
     * @param gridSize  size of the grid
     * @return a Node
     */
    public Node createChild (Direction direction) {
        switch (direction) {    // get the col & row of next tile in the given direction
            case EAST:  return new Node(col+1, row, direction, this);
            case WEST:  return new Node(col-1, row, direction, this);
            case SOUTH: return new Node(col, row+1, direction, this);
            case NORTH: return new Node(col, row-1, direction, this);
            default:    return null;  // added to prevent compilation error
        }
    }

    @Override
    public String toString() {
        if (this.getScan() == 0)         return moveFront + "=0";
        if (this.getScan() == '0')       return moveFront + "='0'";
        return moveFront + "=" + cScan;
    }

    /**
     * Evaluates an integer from 1-16 corresponding to the importance of this node. Priority
     * of direction breaks the ties for priority of scans.
     * Priority of scans (from best to worst): 'G', 'B', 0, 'P'.
     * Priority of directions (from best to worst): EAST, SOUTH, WEST, NORTH.
     * @return this node's priority value
     */
    public int getPriorityValue () {
        int scanPriority = 0, directionPriority = 0;
        switch ( getScan() ) {
            case 'G':   scanPriority = 3; break;
            case 'B':   scanPriority = 2; break;
            case '0':   scanPriority = 1; break;
            case 0:     scanPriority = 1; break;
            case 'P':   scanPriority = 0; break;
            default:    return -1;
        }
        switch ( getFront() ) {
            case EAST:  directionPriority = 4; break;
            case SOUTH: directionPriority = 3; break;
            case WEST:  directionPriority = 2; break;
            case NORTH: directionPriority = 1; break;
        }
        return (4 * scanPriority) + directionPriority;
    }

    public boolean isGoalNode (Miner miner, Grid environment) {
        return environment.getTerrain(miner.get_col(), miner.get_row()) == 'G';
    }
}
