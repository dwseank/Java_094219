/**
 * Represents a single tile.
 * A tile contains the number that is written on it.
 * This number cannot be changed after the creation of the tile.
 */
public class Tile {

    private final int value;

    /**
     * Constructor for Tile object.
     *
     * @param value the number that will be on the tile
     */
    public Tile(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the tile.
     *
     * @return integer value of tile
     */
    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Tile)) {
            return false;
        }
        Tile tile = (Tile) other;
        return value == tile.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}