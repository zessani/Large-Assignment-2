package LA1.Model;

import java.util.ArrayList;
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
	private Queue<Song> recentSongs;
	private MusicStore store; 
	
	public LibraryModel(MusicStore store) {
		this.store = store;
		songs = new Hashtable<>(); 
		ratings = new Hashtable<>();
		albums = new ArrayList<>();  
		playlists = new ArrayList<>();
		recentSongs = new LinkedList<>();
		recentPlaylist = new AutoPlaylist("Recently Played");
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
                updateGenreCount();
                return true;
            }
        }
        return false;
    }

    public boolean addSong(Song toAddSong, boolean force){
    	if (force){
    		songs.put(toAddSong, 0);
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
                    songs.put(storeSong, 0);
                }
                albums.add(newAlbum);
                return true;
            }
        }
        
        return false;
    }
    
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
                    songs.put(storeSong, 0);
                }
                albums.add(newAlbum);
                return true;
            }
        }
        
        return false;
    }
    
    public void playSong(String title, String artist){
    	Song song = findSong(title,artist);
    	if (song != null){}
        	int plays = songs.get(song);
        	songs.remove(song);
        	songs.put(song, plays+1);
        	if (recentSongs.contains(song)){
        		recentSongs.remove(song);
        		recentSongs.add(song);
        		}
        	else {
        		if (recentSongs.size() < 10) {
        			recentSongs.add(song);
        		}
        		else {
        			recentSongs.remove();
        			recentSongs.add(song);
        		}
        	}
    	updateAutomaticPlaylist();
    }
    
    private void updateAutomaticPlaylist(){
    	updateRecentlyPlayedPlaylist();
    	updateFrequentlyPlayedPlaylist();
    }
    
    private void updateRecentlyPlayedPlaylist() {
    	recentPlaylist.wipe();
    	for (int i = 0; i < recentSongs.size(); i++){
    		recentPlaylist.addSongs(recentSongs.element());
    	}
    }
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
    	for (Song song: keys) {
    		frequentPlayedPlaylist.addSongs(song);
    	}
    }
    
    public AutoPlaylist getRecentlyPlayedPlaylist(){
    	return recentPlaylist;
    }
    
    public AutoPlaylist getFrequentlyPlayedPlaylist(){
    	return frequentPlayedPlaylist;
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
	
	private void updateGenreCount(){
		
	}
	
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
	
	public void test(){
		Enumeration<Song> songList = songs.keys();
        while (songList.hasMoreElements()) {
        	Song song = songList.nextElement();
        	System.out.println(song.getSongTitle());
        }
	}
}

