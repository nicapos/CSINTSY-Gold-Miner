import java.util.ArrayList;

public class Node {
    private Grid state; // assumes Miner is in Grid

    private ArrayList<Node> children;
    private Node parent;

    public Node (Grid grid) {
        state = grid;
    }

    public Node (Grid grid, Node parentNode) {
        state = grid;
        parent = parentNode;
    }

    public Grid getState() { return state; }

    public ArrayList<Node> getChildren() { return children; }

    public Node getParent() { return parent; }

    public void setParent(Node parentNode) {
        parent = parentNode;
    }

    public void createChildren() {
        /* TODO */
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        Node otherNode = (Node) obj;
        return state.equals(otherNode.getState());
    }
}
