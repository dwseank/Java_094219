/** Represents a single action that can be performed in the game. */
public class Action {

    private final Tile tile;
    private final Direction direction;

    /**
     * Constructs an action object using a tile and a direction to move in.
     *
     * @param tile the tile to move
     * @param direction the direction to move the tile in
     */
    public Action(Tile tile, Direction direction) {
        this.tile = tile;
        this.direction = direction;
    }

    /**
     * Returns the tile that the action is performed on.
     *
     * @return tile
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Returns the direction that the tile will be moved in.
     *
     * @return direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Formats an action into a string.
     *
     * @return action in string format
     */
    @Override
    public String toString() {
        return "Move " + tile.getValue() + " " + direction.toString().toLowerCase();
    }
}