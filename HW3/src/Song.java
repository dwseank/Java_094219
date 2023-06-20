/** A song that is defined by: name, artist, genre and duration in seconds. */
public class Song implements Cloneable {
    private final String name;
    private final String artist;
    private final Genre genre;
    private int duration;

    /** Defines possible genres to classify songs by. */
    public enum Genre {
        POP,
        ROCK,
        HIP_HOP,
        COUNTRY,
        JAZZ,
        DISCO
    }

    /**
     * Constructs a Song using the given parameters.
     *
     * @param name the name of the song
     * @param artist the artist of the song
     * @param genre the genre of the song
     * @param duration the duration (in seconds) of the song
     */
    public Song(String name, String artist, Genre genre, int duration) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
    }

    /**
     * Returns the name of the song.
     *
     * @return the name of the song
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the artist of the song.
     *
     * @return the artist of the song
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Returns the genre of the song.
     *
     * @return the genre of the song
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Returns the duration of the song.
     *
     * @return the duration (in seconds) of the song
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns a string representation of the song in the following format:
     * name, artist, genre, m:ss
     *
     * @return song in string format
     */
    @Override
    public String toString() {
        return String.format("%s, %s, %s, %d:%02d", name, artist, genre, duration / 60, duration % 60);
    }

    /**
     * Checks if two songs are equal.
     * Songs are equal if they have the same name and artist.
     *
     * @param other the other song
     * @return true if the songs are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Song)) {
            return false;
        }
        Song otherSong = (Song) other;
        return name.equals(otherSong.name) && artist.equals(otherSong.artist);
    }

    /**
     * Creates a hash code based on the name and artist of the song.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return (name + artist).hashCode();
    }

    /**
     * Performs a deep copy on the song.
     *
     * @return a clone of the song
     */
    @Override
    public Song clone() {
        try {
            return (Song) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Sets the duration of the song.
     *
     * @param duration new duration (in seconds)
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
