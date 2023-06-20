/** A filter to be used in song collections. */
public class Filter implements Cloneable {
    private String artist;
    private Song.Genre genre;
    private int maxDuration;

    /** Constructs a Filter with default values. */
    public Filter() {
        artist = null;
        genre = null;
        maxDuration = Integer.MAX_VALUE;
    }

    /**
     * Sets the artist to filter by.
     *
     * @param artist the artist to filter by
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Sets the genre to filter by.
     *
     * @param genre the genre to filter by
     */
    public void setGenre(Song.Genre genre) {
        this.genre = genre;
    }

    /**
     * Sets the maximum duration to filter by.
     *
     * @param maxDuration the maximum duration (in seconds) to filter by
     */
    public void setDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    /**
     * Checks if the given song matches the filter criteria.
     *
     * @param song song to filter
     * @return true if song matches the filter criteria
     */
    public boolean match(Song song) {
        return (artist == null || song.getArtist().equals(artist)) &&
                (genre == null || song.getGenre().equals(genre)) &&
                song.getDuration() <= maxDuration;
    }

    /**
     * Performs a deep copy on the filter.
     *
     * @return a clone of the filter
     */
    @Override
    public Filter clone() {
        try {
            return (Filter) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
