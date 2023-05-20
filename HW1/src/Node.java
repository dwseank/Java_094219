/**
 * Represents a single Node.
 * Consists of information about the state, parent node and previous action.
 */
public class Node {

    private final State state;
    private final Action action;
    private final Node parent;

    /**
     * Constructor for the root node.
     *
     * @param state initial state
     */
    public Node(State state) {
        this(state, null, null);
    }

    /**
     * Constructor for expanded nodes.
     *
     * @param state new state
     * @param action previous action
     * @param parent parent node
     */
    private Node(State state, Action action, Node parent) {
        this.state = state;
        this.action = action;
        this.parent = parent;
    }

    /**
     * Returns the state in the node.
     *
     * @return state
     */
    public State getState() {
        return state;
    }

    /**
     * Returns the action performed to get from the previous state to the current state.
     *
     * @return previous action
     */
    public Action getAction() {
        return action;
    }

    /**
     * Returns the parent node.
     *
     * @return parent node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Expands the current node.
     *
     * @return array of all nodes resulted from expansion
     */
    public Node[] expand() {
        Action[] actions = state.actions();
        Node[] expandedNodes = new Node[actions.length];
        for (int i = 0; i < expandedNodes.length; i++) {
            expandedNodes[i] = new Node(state.result(actions[i]), actions[i], this);
        }
        return expandedNodes;
    }

    /**
     * Returns the heuristic value of the current state in the node.
     * The heuristic value estimates the distance from the current state to the goal state.
     * To be used in the search algorithm.
     *
     * @return heuristic value of current node
     */
    public int heuristicValue() {
        return state.getHeuristicValue();
    }
}
