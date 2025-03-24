package LA1.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

import LA1.MusicStore;


//LibraryModel.java

public class LibraryModel {
	private ArrayList<Album> albums;
	private Dictionary<Song, Integer> songs;
	private Dictionary<Song, Integer> ratings;
	private ArrayList<Playlist> playlists; 
	private AutoPlaylist recentPlaylist;
	private AutoPlaylist frequentPlayedPlaylist;
	private AutoPlaylist favourites;
	private AutoPlaylist topRated;
	private ArrayList<AutoPlaylist> genre;
	private Queue<Song> recentSongs;
	private MusicStore store; 
	
	public LibraryModel(MusicStore store) {
		this.store = store;
		songs = new Hashtable<>(); 
		ratings = new Hashtable<>();
		albums = new ArrayList<>();  
		playlists = new ArrayList<>();
		recentSongs = new LinkedList<>();
		genre = new ArrayList<AutoPlaylist>();
		recentPlaylist = new AutoPlaylist("Recently Played");
		topRated = new AutoPlaylist("Top Rated");
		frequentPlayedPlaylist = new AutoPlaylist("Frequently Played");
		favourites = new AutoPlaylist("Favourites");
	}    
	// search song by title
	public ArrayList<Song> searchSongByTitle(String title) {
		ArrayList<Song> result = new ArrayList<>();
		Enumeration<Song> songList = songs.keys();
		while (songList.hasMoreElements()) {
			Song song = songList.nextElement();
			if (song.getSongTitle().equalsIgnoreCase(title)) {
				result.add(song); 
			}
		}
		return result;
	}
	// search song by artist name
	public ArrayList<Song> searchSongByArtist(String artist) {
		ArrayList<Song> result = new ArrayList<>();
		Enumeration<Song> songList = songs.keys();
		while (songList.hasMoreElements()){
			Song song = songList.nextElement();
			if (song.getArtistName().equalsIgnoreCase(artist)) {
				result.add(song);
			}
		}
		return result;
	}
	public ArrayList<Song> searchSongByGenre(String genre){
		ArrayList<Song> result = new ArrayList<>();
		Enumeration<Song> songList = songs.keys();
		while (songList.hasMoreElements()){
			Song song = songList.nextElement();
			if (song.getGenre().equalsIgnoreCase(genre)) {
				result.add(song);
			}
		}
		return result;
	}
	// search album by title
	public ArrayList<Album> searchAlbumByTitle(String title) {
		ArrayList<Album> result = new ArrayList<>();
		for ( Album album : albums) {
			if (album.getTitle().equals(title)) {
				result.add(album);
			}
		}
		return result;
	}
	// search album by artist name
	public ArrayList<Album> searchAlbumByArtist(String artist) {
		ArrayList<Album> result = new ArrayList<>();
		for ( Album album : albums) {
			if (album.getArtist().equals(artist)) {
				result.add(album);
			}
		}
		return result;
	}
	
	public Playlist searchPlaylistByName(String name) { 
	    for (Playlist playlist : playlists) {
	        if (playlist.getName().equals(name)) {
	            return playlist;
	        }
	    }
	    return null;
	}
	
	
	// get all lists
	
	public ArrayList<String> getAllSongTitles() {
        ArrayList<String> titles = new ArrayList<>();
        Enumeration<Song> songList = songs.keys();
        while (songList.hasMoreElements()) {
        	Song song = songList.nextElement();
            titles.add(song.getSongTitle());
        }
        return titles;
    }
     
    public ArrayList<String> getAllArtists() {
        ArrayList<String> artists = new ArrayList<>();
        Enumeration<Song> songList = songs.keys();
        while (songList.hasMoreElements()) {
        	Song song = songList.nextElement();
            if (!artists.contains(song.getArtistName())) {
                artists.add(song.getArtistName());
            }
        }
        return artists;
    }
    
    public ArrayList<String> getAllAlbumTitles() {
        ArrayList<String> titles = new ArrayList<>();
        for (Album album : albums) {
            titles.add(album.getTitle());
        }
        return titles;
    }
    
    public ArrayList<String> getAllPlaylists() {
        ArrayList<String> names = new ArrayList<>();
        for (Playlist playlist : playlists) {
            names.add(playlist.getName());
        }
        return names;
    }
	
    public ArrayList<Song> getFavorites() {
        ArrayList<Song> favorites = new ArrayList<>();
        Enumeration<Song> songList = songs.keys();
        while (songList.hasMoreElements()) {
        	Song song = songList.nextElement();
            if (inFavourites(song)) {
                favorites.add(song);
            }
        }
        return favorites;
    }
    
     
    // create a playlist and add/remove songs
    // creating a new Playlist
    
    public void newPlaylist(String name) {
        playlists.add(new Playlist(name));
    }
    
    // add songs to playlist
    
    public boolean addToPlaylist(String playlistName, String songTitle, String artist) {
        // Find the playlist
        Playlist playlist = searchPlaylistByName(playlistName);
        if (playlist == null) {
            return false;
        }
        // Find the song
        Enumeration<Song> songList = songs.keys();
        while (songList.hasMoreElements()) {
        	Song song = songList.nextElement();
            if (song.getSongTitle().equals(songTitle) && song.getArtistName().equals(artist)) {
                playlist.addSongs(song);
                return true;
            }
        }
        return false;
    } 
    
    // remove song from playlist
    
    public boolean removeFromPlaylist(String playlistName, String songTitle, String artist) {
        Playlist playlist = searchPlaylistByName(playlistName);
        if (playlist == null) {
            return false;
        }
        Enumeration<Song> songList = songs.keys();
        while (songList.hasMoreElements()) {
        	Song song = songList.nextElement();
            if (song.getSongTitle().equals(songTitle) && song.getArtistName().equals(artist)) {
                playlist.removeSong(song);
                return true;
            }
        }
        return false;
    }  
    
    // rate a song  (name and artist)
    public boolean rateSong(String title, String artist, int rating) {
        Enumeration<Song> songList = songs.keys();
        while (songList.hasMoreElements()) {
        	Song song = songList.nextElement();
            if (song.getSongTitle().equals(title) && song.getArtistName().equals(artist)) {
                setRating(song,rating);
                return true;
            }
        }
        return false;
    }
    
    public void setRating(Song song,int rating) {
		if (rating >= 1 && rating <= 5) {
			Enumeration<Song> ratingList = ratings.keys();
			while (ratingList.hasMoreElements()){
				Song currentSong = ratingList.nextElement();
				if (currentSong == song){
					ratings.remove(currentSong);
				}
	        }
			Enumeration<Song> songList = songs.keys();
			while (songList.hasMoreElements()) {
				Song currentSong = songList.nextElement();
	        	if (currentSong.equals(song)){
	        			ratings.put(song, rating);
	        				if (rating == 5) {
	        					favourites.addSongs(song);
	        				}		
	        	}
	        }
			updateTopRatedPlaylist();
		}
    }
		
	public int getRating(Song song) {
		Enumeration<Song> songList = ratings.keys();
		while (songList.hasMoreElements()) {
			Song currentSong = songList.nextElement();
        	if (currentSong.equals(song)){
        		return ratings.get(song);
        	}	
		}
		return 0;
	}
	
	public boolean inFavourites(Song song){
		for (Song songs : favourites.getSongs()){
			if (song == songs){
				return true;
			}
		}
		return false;
	}
	
	public void addFavourite(Song song){
		favourites.addSongs(song);		
	}
	
	// mark a song as â€œfavoriteâ€�
    public boolean markFavorite(String title, String artist, boolean favorite) {
        Enumeration<Song> songList = songs.keys();
        while (songList.hasMoreElements()) {
        	Song song = songList.nextElement();
            if (song.getSongTitle().equals(title) && song.getArtistName().equals(artist)) {
                if (favorite == true) {
                	favourites.addSongs(song);
                }
                else {
                	favourites.removeSong(song);
                } 
                return true;
            }
        }
        return false;
    }
    
    // add a song from the store
    public boolean addSong(String title, String artist) {
        Enumeration<Song> songList = songs.keys();
        while (songList.hasMoreElements()) {
        	Song song = songList.nextElement();
            if (song.getSongTitle().equals(title) && song.getArtistName().equals(artist)) {
                return false;
            }
        }
        
        ArrayList<Song> storeSongs = store.searchSongByTitle(title);
        for (Song storeSong : storeSongs) {
            if (storeSong.getArtistName().equals(artist)) {
                Song newSong = storeSong;
                songs.put(newSong, 0);
        		partialAlbumAdd(newSong);
                updateGenrePlaylist();
                return true;
            }
        }
        return false;  
    }
    
    //adds an album from the store, but can also be added not from it
    public boolean addSong(Song toAddSong, boolean force){
    	if (force){
    		songs.put(toAddSong, 0);
    		partialAlbumAdd(toAddSong);
    		return true;
    	}
    	String title = toAddSong.getSongTitle();
    	String artist = toAddSong.getArtistName();
    	Enumeration<Song> songList = songs.keys();
        while (songList.hasMoreElements()) {
        	Song song = songList.nextElement();
            if (song.getSongTitle().equals(title) && song.getArtistName().equals(artist)) {
                return false;
            }
        } 
        ArrayList<Song> storeSongs = store.searchSongByTitle(title);
        for (Song storeSong : storeSongs) {
            if (storeSong.getArtistName().equals(artist)) {
                Song newSong = new Song(title, artist, storeSong.getAlbumTitle(), storeSong.getGenre());
                songs.put(newSong, 0);
        		partialAlbumAdd(newSong);
                updateGenrePlaylist();
                return true;
            }
        }
        return false;
     }

    // add an album from the store
    public boolean addAlbum(String title, String artist) {
        for (Album album : albums) {
            if (album.getTitle().equals(title) && album.getArtist().equals(artist)) {
                return false;
            }
        }
        ArrayList<Album> storeAlbums = store.searchAlbumByTitle(title);
        if (storeAlbums == null){
        	return false;
        }
        for (Album storeAlbum : storeAlbums) {
            if (storeAlbum.getArtist().equals(artist)) {
                Album newAlbum = storeAlbum;
                for (Song storeSong : storeAlbum.getSongs()) {
                    addSong(storeSong,false);
                }
                overwritePartialAlbum(newAlbum);
                albums.add(newAlbum);
                return true;
            }
        }
        
        return false;
    }
    
    //adds album to albums, where force is used to inquire whether to check MusicStore
    public boolean addAlbum(Album toAddAlbum, boolean force){
    	if (force){
    		albums.add(toAddAlbum);
    		return true;
    	}
    	String title = toAddAlbum.getTitle();
    	String artist = toAddAlbum.getArtist();
    	for (Album album : albums) {
            if (album.getTitle().equals(title) && album.getArtist().equals(artist)) {
                return false;
            }
        }
        ArrayList<Album> storeAlbums = store.searchAlbumByTitle(title);
        if (storeAlbums == null){
        	return false;
        }
        for (Album storeAlbum : storeAlbums) {
            if (storeAlbum.getArtist().equals(artist)) {
                Album newAlbum = storeAlbum;
                for (Song storeSong : storeAlbum.getSongs()) {
                    addSong(storeSong,false);
                }
                overwritePartialAlbum(newAlbum);
                albums.add(newAlbum);
                return true;
            }
        }
        
        return false;
    }
    
    //adds a partial album to the albums list based on the song's album
    public boolean partialAlbumAdd(Song song){
    	boolean partialAlbum = false;
    	String artist = song.getArtistName();
    	String title = song.getAlbumTitle();
    	ArrayList<Album> album = store.searchAlbumByArtist(artist);
    	if (album == null){
    		return false;
    	}
    	for (Album libraryAlbum : albums){
    		if (libraryAlbum.getTitle().equalsIgnoreCase(title)){
				partialAlbum = true;
    			if (libraryAlbum.getSong(title) == null){
    				libraryAlbum.addSong(title);
    				}
    			}
    		}
    	checkFullAlbum(song);
    	if (partialAlbum){
    		return false;
    	}
    	for (Album storeAlbum : album) {
    		if (storeAlbum.getTitle().equalsIgnoreCase(title)){
    			Album newAlbum = new Album(storeAlbum.getTitle(),storeAlbum.getArtist(),storeAlbum.getGenre(),storeAlbum.getYear());
    			newAlbum.addSong(song.getArtistName());
    			albums.add(storeAlbum);
    			return true;
    		}
    	}
    	return false;
    }
    public void checkFullAlbum(Song song){
    	int i = 0;
    	while (i < albums.size()) {
    		Album libraryAlbum = albums.get(i); 
    		ArrayList<Album> storeAlbums = store.searchAlbumByTitle(libraryAlbum.getTitle());
    		for (Album storeAlbum : storeAlbums){
    			if (storeAlbum.getArtist().equals(libraryAlbum.getArtist())){
    				if (storeAlbum.countSongs() == libraryAlbum.countSongs()){
    					albums.remove(libraryAlbum);
    					albums.add(storeAlbum);
    				}
    			}
    		}
    		i++;
        }
    }
    
    //overwrites any partial albums if possible
    public void overwritePartialAlbum(Album album){
    	String title = album.getTitle();
    	String artist = album.getArtist();
    	String genre = album.getGenre();
    	int year = album.getYear();
    	for (Album libraryAlbum : albums){
    		String libraryTitle = libraryAlbum.getTitle();
        	String libraryArtist = libraryAlbum.getArtist();
        	String libraryGenre = libraryAlbum.getGenre();
        	int libraryYear = libraryAlbum.getYear();
    		if (title.equalsIgnoreCase(libraryTitle) && artist.equalsIgnoreCase(libraryArtist) 
    				&& genre.equalsIgnoreCase(libraryGenre) && (year == libraryYear)){
    			if (album != libraryAlbum){
    				albums.remove(libraryAlbum);
    			}
    		}
    	}
    }
    
    //helper function to call both updates
    private void updateAutomaticPlaylist(){
    	updateRecentlyPlayedPlaylist();
    	updateFrequentlyPlayedPlaylist();
    }
    
    //Updates the recently played playlist
    private void updateRecentlyPlayedPlaylist() {
    	recentPlaylist.wipe();
    	for (int i = 0; i < recentSongs.size(); i++){
    		Song song = recentSongs.remove();
    		recentPlaylist.addSongsToStart(song);
    		recentSongs.add(song);
    	}
    }
    
    //Updates the frequently played playlist
    private void updateFrequentlyPlayedPlaylist(){
		frequentPlayedPlaylist.wipe();
    	Song[] keys = new Song[10];
    	int[] values = new int[10];
    	int i = 0;
    	int index = 0; 
    	Enumeration<Song> songList = songs.keys();
    	Enumeration<Integer> playCount = songs.elements();
    	while (songList.hasMoreElements()){
    		Song song = songList.nextElement();
    		Integer plays = playCount.nextElement();
    		if (i < 10){
    			keys[i] = song;
    			values[i] = plays;
    			i++;
    		}
    		else{
    			int min = values[0];
    			for (int j = 0; j < 10; j++){
    				if (values[j] <= min){
    					min = values[j];
    					index = j;
    				}
    			}
    			if (plays > min){
    				values[index] = songs.get(song);
    				keys[index] = song;
    			}
    		}
    	}
    	int tempInt = 0;
    	Song temp = null;
    	for (i = 0 ; i < values.length;i++) {
            for (int j = i+1 ; j < values.length; j++){
                if (values[i] > values[j]){
                    tempInt = values[i];
                    values[i] = values[j];
                    values[j] = tempInt;
                    
                    temp = keys[i];
                    keys[i] = keys[j];
                    keys[j] = temp;
                }
            }
    		frequentPlayedPlaylist.addSongsToStart(keys[i]);
    	}
    }
    
    //Updates all Genre AutoPlaylist
	private void updateGenrePlaylist(){
		boolean found;
		for (AutoPlaylist pl : genre) {
			pl.wipe();
		}
		Enumeration<Song> songList = songs.keys();
        while (songList.hasMoreElements()) {
        	found = false;
        	Song song = songList.nextElement();
    		if (genre.size() == 0){
    			AutoPlaylist newPl = new AutoPlaylist(song.getGenre());
    			newPl.addSongs(song);
    			genre.add(newPl);
    		}
        	for (AutoPlaylist pl : genre){
        		if (song.getGenre().equalsIgnoreCase(pl.getName())){
        			pl.addSongs(song);
        			found = true;
        		}
        	}
        	if (found == false){
        		AutoPlaylist newPl = new AutoPlaylist(song.getGenre());
        		newPl.addSongs(song);
        		genre.add(newPl);
        	}
        }		
	}
	
	private void updateTopRatedPlaylist(){
		topRated.wipe();
		Enumeration<Song> songList = ratings.keys();
        while (songList.hasMoreElements()) {
        	Song song = songList.nextElement();
        	if (ratings.get(song) >= 4){
        		topRated.addSongs(song);
        	}
        }
	}
	
    public AutoPlaylist getRecentlyPlayedPlaylist(){
    	return recentPlaylist;
    }
    
    public AutoPlaylist getFrequentlyPlayedPlaylist() {
    	return frequentPlayedPlaylist;
    }
    
    public ArrayList<AutoPlaylist> getGenrePlaylist(){
    	return genre;
    }
    
    //plays a song, and updates AutoPlaylists
    public void playSong(String title, String artist){
    	Song song = findSong(title,artist);
    	if (song != null){
        	int plays = songs.get(song);
        	songs.remove(song);
        	songs.put(song, plays+1);
        	if (recentSongs.contains(song)){
        		recentSongs.remove(song);
        		recentSongs.add(song);
        	}
        	else {
        		if (recentSongs.size() < 10){
        			recentSongs.add(song);
        		}
        		else {
        			recentSongs.remove();
        			recentSongs.add(song);
        		}
        	}
    	updateAutomaticPlaylist();
    	}
    }
    
    // get the amount of times a song is played
	public int getPlays(String title, String artist){
		Song song = findSong(title,artist);
		if (song == null){
			System.out.println("song not in library");
			return 0;
		}
		Enumeration<Song> songList = songs.keys();
		while (songList.hasMoreElements()) {
			Song currentSong = songList.nextElement();
			if (currentSong == song){
				return songs.get(currentSong);
			}
		}
		return 0;
	}
	
	public int getPlays(Song song){
		if (song == null){
			System.out.println("song not in library");
			return 0;
		}
		Enumeration<Song> songList = songs.keys();
		while (songList.hasMoreElements()) {
			Song currentSong = songList.nextElement();
			if (currentSong.equals(song)){
				return songs.get(currentSong);
			}
		}
		return 0;
	}
	// finds song given title and artist
	private Song findSong(String title, String artist){
    	Enumeration<Song> songList = songs.keys();
        while (songList.hasMoreElements()) {
        	Song song = songList.nextElement();
        	if (song.getSongTitle().equalsIgnoreCase(title) && song.getArtistName().equalsIgnoreCase(artist)) {
        		return song;
        	}
        }
		return null;
	}
	
	// sort songs by title
	public ArrayList<Song> getSongsSortedByTitle() {
		    // Get all songs from dictionary
		    ArrayList<Song> songList = new ArrayList<>();
		    Enumeration<Song> songsEnum = songs.keys();
		    
		    while (songsEnum.hasMoreElements()) {
		        songList.add(songsEnum.nextElement());
		    }
		    // sort by title
		    for (int i = 0; i < songList.size() - 1; i++) {
		        for (int j = 0; j < songList.size() - i - 1; j++) {
		            Song song1 = songList.get(j);
		            Song song2 = songList.get(j + 1);
		            
		            String title1 = song1.getSongTitle();
		            String title2 = song2.getSongTitle();
		            if (title1.compareTo(title2) > 0) {
		                songList.set(j, song2);
		                songList.set(j + 1, song1);
		            }
		        }
		    }   
		    return songList;
		}
	// sort by artist
	
	public ArrayList<Song> getSongsSortedByArtist() {

	    ArrayList<Song> songList = new ArrayList<>();
	    Enumeration<Song> songsEnum = songs.keys();
	    
	    while (songsEnum.hasMoreElements()) {
	        songList.add(songsEnum.nextElement());
	    }
	    for (int i = 0; i < songList.size() - 1; i++) {
	        for (int j = 0; j < songList.size() - i - 1; j++) {
	            Song song1 = songList.get(j);
	            Song song2 = songList.get(j + 1);
	            
	            String artist1 = song1.getArtistName();
	            String artist2 = song2.getArtistName();
	            
	            if (artist1.compareTo(artist2) > 0) {
	           
	                songList.set(j, song2);
	                songList.set(j + 1, song1);
	            }
	        }
	    }
	    
	    return songList;
	}
	
	// sort by rating (high to low)
	
	public ArrayList<Song> getSongsSortedByRating() {
	 
	    ArrayList<Song> songList = new ArrayList<>();
	    Enumeration<Song> songsEnum = songs.keys();
	    
	    while (songsEnum.hasMoreElements()) {
	        songList.add(songsEnum.nextElement());
	    }
	    for (int i = 0; i < songList.size() - 1; i++) {
	        for (int j = 0; j < songList.size() - i - 1; j++) {
	            Song song1 = songList.get(j);
	            Song song2 = songList.get(j + 1);
	            
	            int rating1 = getRating(song1);
	            int rating2 = getRating(song2);
	            
	            if (rating1 < rating2) {
	                songList.set(j, song2);
	                songList.set(j + 1, song1);
	            } 
	        }
	    }
	    
	    return songList;
	}
	// remove song from library
	public boolean removeSong(String title, String artist) {
	    // Find the song to remove
	    Song songToRemove = null;
	    Enumeration<Song> songList = songs.keys();
	    
	    while (songList.hasMoreElements()) {
	        Song song = songList.nextElement();
	        if (song.getSongTitle().equals(title) && song.getArtistName().equals(artist)) {
	            songToRemove = song;
	            break;
	        }
	    }
	    // remove from playlists, favourites, ratings
	    songs.remove(songToRemove);
	    ratings.remove(songToRemove);
	    favourites.removeSong(songToRemove);
	    for (Playlist playlist : playlists) {
	        playlist.removeSong(songToRemove);
	    } 
	    return true;
	}
	
	// remove album from library
	
	public boolean removeAlbum(String title, String artist) {
	    // Find the album to remove
	    Album albumToRemove = null;
	    for (Album album : albums) {
	        if (album.getTitle().equals(title) && album.getArtist().equals(artist)) {
	            albumToRemove = album;
	            break;
	        } 
	    }
	    albums.remove(albumToRemove);
	    // Find all songs from this album
	    ArrayList<Song> albumSongs = new ArrayList<>();
	    Enumeration<Song> songList = songs.keys();
	    
	    while (songList.hasMoreElements()) {
	        Song song = songList.nextElement();
	        if (song.getAlbumTitle().equals(title) && song.getArtistName().equals(artist)) {
	            albumSongs.add(song);
	        }
	    }
	    for (Song song : albumSongs) {
	        removeSong(song.getSongTitle(), song.getArtistName());
	    }
	    
	    return true;
	}

	


	
	// shuffle songs
	
	public ArrayList<Song> getShuffledSongs() {
	    // Get all songs
	    ArrayList<Song> songList = new ArrayList<>();
	    Enumeration<Song> songsEnum = songs.keys();
	    
	    while (songsEnum.hasMoreElements()) {
	        songList.add(songsEnum.nextElement()); 
	    }
	    // Shuffle them
	    Collections.shuffle(songList);
	    
	    return songList;
	}
	
	// shuffle songs in playlist
	
	public ArrayList<Song> getShuffledPlaylist(String playlistName) {

	    Playlist playlist = searchPlaylistByName(playlistName);
	    if (playlist == null) {
	        return null;  
	    }  
	    ArrayList<Song> playlistSongs = new ArrayList<>(playlist.getSongs());
	    
	    Collections.shuffle(playlistSongs);
	    
	    return playlistSongs;
	}
	
	// C part d: check album info from song
	public Album getAlbumInfo(String songTitle, String artist) {
	    Song song = findSong(songTitle, artist);	
	    if (song == null) {
	        return null;
	    }
	    String albumTitle = song.getAlbumTitle();
	    // First check in user's library
	    for (Album album : albums) {
	        if (album.getTitle().equals(albumTitle) && album.getArtist().equals(artist)) {
	            return album;
	        }
	    }
	    
	    // Check if album is in store
	    ArrayList<Album> storeAlbums = store.searchAlbumByTitle(albumTitle);
	    if (storeAlbums != null) {
	        for (Album album : storeAlbums) {
	            if (album.getArtist().equals(artist)) {
	                return album;
	            }
	        }
	    }
	    return null;
	}
	
	// check if album is in library
	
	public boolean isAlbumInLibrary(String albumTitle, String artist) {
	    for (Album album : albums) {
	        if (album.getTitle().equals(albumTitle) && 
	            album.getArtist().equals(artist)) {
	            return true;
	        }
	    }
	    return false;
	}
 
	
}

