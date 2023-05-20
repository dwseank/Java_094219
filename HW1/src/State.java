/**
 * Represents a single state.
 * Contains the game board wih different tile positions.
 */
public class State {

    private final Board board;

    /**
     * Constructs State object based on board input.
     *
     * @param board board representing the state
     */
    public State(Board board) {
        this.board = board;
    }

    /**
     * Checks if current state is the goal state.
     *
     * @return true if goal state, false otherwise
     */
    public boolean isGoal() {
        return board.isInOrder();
    }

    /**
     * Returns an array with all possible actions in the current state.
     * (In order: Up, Down, Right, Left).
     * If an action is not possible it will not be included in the array,
     * then the array will be of a smaller size.
     *
     * @return array of possible actions
     */
    public Action[] actions() {
        // Get empty tile location
        int row = board.getEmptyTileRow();
        int column = board.getEmptyTileColumn();

        // Get the surrounding tiles
        Tile[] tiles = new Tile[4];
        tiles[0] = board.getTile(row + 1, column); // Up
        tiles[1] = board.getTile(row - 1, column); // Down
        tiles[2] = board.getTile(row, column - 1); // Right
        tiles[3] = board.getTile(row, column + 1); // Left

        // Check for valid tiles and determine array size
        int size = 0;
        for (Tile tile : tiles) {
            if (tile != null) {
                size++;
            }
        }

        // Create possible actions array
        Action[] actions = new Action[size];
        int index = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != null) {
                actions[index] = new Action(tiles[i], Direction.values()[i]);
                index++;
            }
        }
        return actions;
    }

    /**
     * Performs the provided action on current state and returns the resulted state.
     *
     * @param action action to perform on current state
     * @return new state as a result from provided action
     */
    public State result(Action action) {
        // Create a copy of current board
        Board newBoard = new Board(board);

        // Get empty tile location
        int row = board.getEmptyTileRow();
        int column = board.getEmptyTileColumn();

        // Store empty tile
        Tile emptyTile = newBoard.getTile(row, column);

        // Move number tile over empty tile
        newBoard.setTile(row, column, action.getTile());

        // Move empty tile over old number tile and update new empty tile location
        switch (action.getDirection()) {
            case UP:
                newBoard.setTile(row + 1, column, emptyTile);
                newBoard.updateEmptyTileLocation(row + 1, column);
                break;
            case DOWN:
                newBoard.setTile(row - 1, column, emptyTile);
                newBoard.updateEmptyTileLocation(row - 1, column);
                break;
            case RIGHT:
                newBoard.setTile(row, column - 1, emptyTile);
                newBoard.updateEmptyTileLocation(row, column - 1);
                break;
            case LEFT:
                newBoard.setTile(row, column + 1, emptyTile);
                newBoard.updateEmptyTileLocation(row, column + 1);
                break;
        }

        // Create new state
        return new State(newBoard);
    }

    /**
     * Gets the estimated number of moves remaining to get to the goal state.
     * This number represents the heuristic value.
     *
     * @return heuristic value
     */
    public int getHeuristicValue() {
        return board.calculateHeuristicValue();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof State)) {
            return false;
        }
        State otherState = (State) other;
        return board.equals(otherState.board);
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }
}