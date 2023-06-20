import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/** A collection of songs that can be filtered, re-ordered and iterated upon, implemented using an ArrayList. */
public class Playlist implements Cloneable, FilteredSongIterable, OrderedSongIterable {
    private ArrayList<Song> songs;
    private Filter filter;
    private ScanningOrder order;

    /** Constructs a Playlist with default values. */
    public Playlist() {
        songs = new ArrayList<>();
        filter = new Filter();
        order = ScanningOrder.ADDING;
    }

    /**
     * Adds a song to the playlist.
     *
     * @param song the song to add
     * @throws SongAlreadyExistsException if the playlist already contains the song
     */
    public void addSong(Song song) {
        if (songs.contains(song)) {
            throw new SongAlreadyExistsException();
        }
        songs.add(song);
    }

    /**
     * Removes a song from the playlist.
     *
     * @param song the song to remove
     * @return true if removed, false if the song was not in the playlist
     */
    public boolean removeSong(Song song) {
        return songs.remove(song);
    }


    /**
     * Returns a string representation of the playlist in the following format:
     * [(song), ... , (song)]
     *
     * @return playlist in string format
     */
    @Override
    public String toString() {
        if (songs.size() == 0) {
            return "[]";
        }
        String str = "[";
        for (Song song : songs) {
            str += "(" + song + ")" + ", ";
        }
        str = str.substring(0, str.length() - 2); // Remove last ", "
        str += "]";
        return str;
    }

    /**
     * Checks if two playlists are equal.
     * Playlists are equal if they contain the same songs.
     *
     * @param other the other playlist
     * @return true if the playlists are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Playlist)) {
            return false;
        }
        Playlist otherPlaylist = (Playlist) other;
        if (this.songs.size() != otherPlaylist.songs.size()) {
            return false;
        }
        for (Song song : songs) {
            if (!otherPlaylist.songs.contains(song)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a hash code based on the hash code of the songs in the playlist.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (Song song : songs) {
            hash += song.hashCode();
        }
        return hash;
    }

    /**
     * Performs a deep copy on the playlist.
     *
     * @return a clone of the playlist
     */
    @Override
    public Playlist clone() {
        try {
            Playlist playlistClone = (Playlist) super.clone();
            playlistClone.songs = (ArrayList<Song>) songs.clone();
            playlistClone.filter = filter.clone();
            int size = songs.size();
            for (int i = 0; i < size; i++) {
                playlistClone.songs.set(i, songs.get(i).clone());
            }
            return playlistClone;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public void filterArtist(String artist) {
        filter.setArtist(artist);
    }

    @Override
    public void filterGenre(Song.Genre genre) {
        filter.setGenre(genre);
    }

    @Override
    public void filterDuration(int maxDuration) {
        filter.setDuration(maxDuration);
    }

    @Override
    public void setScanningOrder(ScanningOrder order) {
        this.order = order;
    }

    
    /**
     * Returns an iterator over elements of type Song.
     *
     * @return an iterator.
     */
    @Override
    public PlaylistIterator iterator() {
        return new PlaylistIterator();
    }

    /** Iterator for Playlist */
    public class PlaylistIterator implements Iterator<Song> {
        private final ArrayList<Song> filteredSongs;
        private int index;

        /**
         * Constructs a PlaylistIterator.
         * Creates a filtered and sorted song list based on the filter and order parameters in the playlist.
         * This list is used to iterate on select songs in the playlist in a specific order in an enhanced for loop.
         */
        private PlaylistIterator() {
            filteredSongs = new ArrayList<>();
            index = 0;
            // Filter songs
            for (Song song: songs) {
                if (filter.match(song)) {
                    filteredSongs.add(song);
                }
            }
            // Sort songs
            switch(order) {
                case ADDING: // The playlist is in order ADDING by default
                    break;
                case NAME:
                    filteredSongs.sort(Comparator.comparing(Song::getName)
                            .thenComparing(Song::getArtist));
                    break;
                case DURATION:
                    filteredSongs.sort(Comparator.comparing(Song::getDuration)
                            .thenComparing(Song::getName)
                            .thenComparing(Song::getArtist));
                    break;
            }
        }

        @Override
        public boolean hasNext() {
            return index < filteredSongs.size();
        }

        @Override
        public Song next() {
            return filteredSongs.get(index++);
        }
    }
}
