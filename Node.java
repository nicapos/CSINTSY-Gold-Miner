public class Node {
    private Node parent;
    
    private int col;
    private int row;
    private Direction moveFront;

    private char cScan;
    private char cTerrain;

    public Node (int col, int row, Direction moveFront) {
        this.col = col;
        this.row = row;
        this.moveFront = moveFront;
        cScan = '0';
        cTerrain = '0';
    }

    public int getCol() { return col; }

    public int getRow() { return row; }

    public Direction getFront() { return moveFront; }

    public char getScan() { return cScan; }

    public Node getParent() { return parent; }

    public char getTerrain() { return cTerrain; }

    /**
     * Sets the scan result in this node's direction. This also updates the parent's terrain if this node's
     * direction is the same as the parent node's direction and their scan results are different.
     * @param scanResult
     */
    public void setScan(char scanResult) {
        this.cScan = scanResult;

        if (this.moveFront == parent.getFront())
            if (this.cScan != parent.getScan())
                parent.setTerrain(parent.getScan());
    }

    public void setTerrain(char cTerrain) {
        this.cTerrain = cTerrain;
    }

    public void setParent(Node parentNode) {
        parent = parentNode;
    }

    /**
     * Creates a child to this node, with its parent set as this node.
     * @param direction direction to move, relative to this node's x and y coordinates
     * @return a Node
     */
    public Node createChild (Direction direction) {
        int c, r;
        switch (direction) {    // get the col & row of next tile in the given direction
            case EAST:  c = col+1;
                        r = row;
                        break;
            case WEST:  c = col-1;
                        r = row;
                        break;
            case SOUTH: c = col;
                        r = row+1;
                        break;
            case NORTH: c = col;
                        r = row-1;
                        break;
            default:    c = -1; r = -1; // added to prevent compilation error
        }

        Node newNode = new Node(c, r, direction);
        newNode.setParent(this);
        return newNode;
    }

    @Override
    public String toString() {
        if (this.getScan() == 0)         return "Move " + moveFront + " to (" + col + "," + row + "). scan=0";
        if (this.getScan() == '0')       return "Move " + moveFront + " to (" + col + "," + row + "). scan='0'";
        return "Move " + moveFront + " to (" + col + "," + row + "). scan=" + cScan;
    }

}
