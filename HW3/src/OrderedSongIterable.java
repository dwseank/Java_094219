/**
 * An interface for ordering a collection of songs.
 * Implementations of this interface provide a method to set the order of the songs in the collection.
 */
public interface OrderedSongIterable extends Iterable<Song> {
    /**
     * Sets the order in which to scan the songs in the collection.
     *
     * @param order a ScanningOrder enum to specify the order
     */
    void setScanningOrder(ScanningOrder order);
}
