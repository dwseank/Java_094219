/**
 * An interface for filtering a collection of songs.
 * Implementations of this interface provide methods to filter the songs based on artist, genre and duration.
 */
public interface FilteredSongIterable extends Iterable<Song> {
    /**
     * Filters the songs by the specified artist.
     *
     * @param artist the name of the artist to filter by
     */
    void filterArtist(String artist);

    /**
     * Filters the songs by the specified genre.
     *
     * @param genre the genre to filter by
     */
    void filterGenre(Song.Genre genre);

    /**
     * Filters the songs by the maximum duration.
     *
     * @param maxDuration the maximum duration (in seconds) to filter by
     */
    void filterDuration(int maxDuration);
}
