import java.util.Arrays;

/**
 * Represents the game board.
 * Contains the tiles and goal positions.
 */
public class Board {

    private final Tile[][] tiles;
    private static int rows, columns; // All boards are the same size in a single game
    private int emptyTileRow, emptyTileColumn; // Used to store the empty tile location
    private static int[][] goalPositions; // Used to store desired positions

    /**
     * Constructs Board object based on a string.
     * This is the initial board and also determines the size of all boards in the game.
     * Additionally, initializes goalPositions array.
     *
     * @param boardString string representation of the board
     */
    public Board(String boardString) {
        //Extract values from string
        String[] boardValues = boardString.replace("|", " | ").replace('_', '0').split(" ");

        // Count rows and columns
        rows = 1;
        columns = 0;
        for (String value : boardValues) {
            if (value.equals("|")) {
                rows++;
            }
            if (rows == 1) {
                columns++;
            }
        }
        tiles = new Tile[rows][columns];

        // Fill tiles array
        int rowIndex = 0;
        int columnIndex = 0;
        for (String value : boardValues) {
            if (value.equals("|")) {
                rowIndex++;
                columnIndex = 0;
            } else {
                tiles[rowIndex][columnIndex] = new Tile(Integer.parseInt(value));
                // Save empty tile location
                if (tiles[rowIndex][columnIndex].getValue() == 0) {
                    emptyTileRow = rowIndex;
                    emptyTileColumn = columnIndex;
                }
                columnIndex++;
            }
        }

        // Initialize goal positions
        goalPositions = new int[rows * columns][2];
        for (int value = 1; value <= rows * columns; value++) {
            goalPositions[value - 1] = new int[2];
            goalPositions[value - 1][0] = (value - 1) / columns; // goalRow
            goalPositions[value - 1][1] = (value - 1) % columns; // goalColumn
        }
    }

    /**
     * Constructor used to make a copy of a board.
     *
     * @param board the board to copy
     */
    public Board(Board board) {
        // New tiles array
        this.tiles = new Tile[rows][columns];
        // Copy empty tile location
        this.emptyTileRow = board.emptyTileRow;
        this.emptyTileColumn = board.emptyTileColumn;
        // Copy tiles
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.tiles[i][j] = board.tiles[i][j];
            }
        }
    }

    /**
     * Checks if the tiles on the board are in ascending order.
     * (Tiles start from 1 and last tile should be 0).
     *
     * @return true if tiles in order, false otherwise
     */
    public boolean isInOrder() {
        if (emptyTileRow != rows - 1 || emptyTileColumn != columns - 1) {
            return false;
        }
        int currentValue;
        int previousValue = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                currentValue = tiles[i][j].getValue();
                if (currentValue > previousValue) {
                    previousValue = currentValue;
                } else {
                    return i == rows - 1 && j == columns - 1; // true if last tile
                }
            }
        }
        return false;
    }

    /**
     * Returns the tile at a given location.
     * If given location is outside the bounds of the board then returns null.
     *
     * @param row row index of tile
     * @param column column index of tile
     * @return tile at location or null if given location is out of bounds
     */
    public Tile getTile(int row, int column) {
        if (row >= 0 && row < rows && column >= 0 && column < columns) {
            return tiles[row][column];
        }
        return null;
    }

    /**
     * Set the tile at a given location.
     *
     * @param row row index for location
     * @param column column index for location
     * @param tile tile to set at location
     */
    public void setTile(int row, int column, Tile tile) {
        tiles[row][column] = tile;
    }

    /**
     * Returns the row that the empty tile is on.
     *
     * @return row index
     */
    public int getEmptyTileRow() {
        return emptyTileRow;
    }

    /**
     * Returns the column that the empty tile is on.
     *
     * @return column index
     */
    public int getEmptyTileColumn() {
        return emptyTileColumn;
    }

    /**
     * Updates the new location of the empty tile.
     * Should be used after setTile.
     *
     * @param newRow new row index for empty tile
     * @param newColumn new column index for empty tile
     */
    public void updateEmptyTileLocation(int newRow, int newColumn) {
        emptyTileRow = newRow;
        emptyTileColumn = newColumn;
    }

    /**
     * Calculate the estimated number of moves remaining to get to the goal board.
     * This number represents the heuristic value.
     *
     * @return heuristic value
     */
    public int calculateHeuristicValue() {
        int totalDistance = 0;
        int conflicts = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                // Check distance from goal position
                int value = tiles[i][j].getValue();
                if (value == 0) // Empty tile
                    continue;
                int goalRow = goalPositions[value - 1][0];
                int goalColumn = goalPositions[value - 1][1];
                int distance = Helper.absolute(i - goalRow) + Helper.absolute(j - goalColumn);

                totalDistance += distance;

                // Check for conflicts in the same row
                if (i == goalRow) {
                    for (int k = j + 1; k < columns; k++) {
                        int nextValue = tiles[i][k].getValue();
                        if (nextValue == 0) // Empty tile
                            continue;
                        int nextGoalRow = goalPositions[nextValue - 1][0];
                        int nextGoalColumn = goalPositions[nextValue - 1][1];

                        if (i == nextGoalRow && j > nextGoalColumn) {
                            conflicts++;
                        }
                    }
                }

                // Check for conflicts in the same column
                if (j == goalColumn) {
                    for (int k = i + 1; k < rows; k++) {
                        int nextValue = tiles[k][j].getValue();
                        if (nextValue == 0) // Empty tile
                            continue;
                        int[] nextGoalPos = goalPositions[nextValue - 1];
                        int nextGoalRow = nextGoalPos[0];
                        int nextGoalColumn = nextGoalPos[1];

                        if (j == nextGoalColumn && i > nextGoalRow) {
                            conflicts++;
                        }
                    }
                }
            }
        }
        return totalDistance + (2 * conflicts);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Board)) {
            return false;
        }
        Board board = (Board) other;
        return Arrays.deepEquals(tiles, board.tiles);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }
}
