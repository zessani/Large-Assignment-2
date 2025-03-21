package LA1.Model;

import java.util.ArrayList;

import LA1.MusicStore;


//LibraryModel.java

public class LibraryModel {

	ArrayList<Song> songs;
	ArrayList<Album> albums;
	ArrayList<Playlist> playlists;
	private MusicStore store; 
	
	public LibraryModel(MusicStore store) {
		this.store = store;
		songs = new ArrayList<>(); 
		albums = new ArrayList<>();  
		playlists = new ArrayList<>();
		  
	}    
	// search song by title
	public ArrayList<Song> searchSongByTitle(String title) {
		ArrayList<Song> result = new ArrayList<>();
		for ( Song song : songs) {
			if (song.getSongTitle().equals(title)) {
				result.add(song); 
			}
		}
		return result;
	}
	// search song by artist name
	public ArrayList<Song> searchSongByArtist(String artist) {
		ArrayList<Song> result = new ArrayList<>();
		for ( Song song : songs) {
			if (song.getArtistName().equals(artist)) {
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
        for (Song song : songs) {
            titles.add(song.getSongTitle());
        }
        return titles;
    }
     
    public ArrayList<String> getAllArtists() {
        ArrayList<String> artists = new ArrayList<>();
        for (Song song : songs) {
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
        for (Song song : songs) {
            if (song.isFavorite()) {
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
        for (Song song : songs) {
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
        for (Song song : songs) {
            if (song.getSongTitle().equals(songTitle) && song.getArtistName().equals(artist)) {
                playlist.removeSong(song);
                return true;
            }
        }
        return false;
    }  
    
    
    // mark a song as “favorite”
    
    public boolean markFavorite(String title, String artist, boolean favorite) {
        for (Song song : songs) {
            if (song.getSongTitle().equals(title) && song.getArtistName().equals(artist)) {
                song.setFavorite(favorite);
                return true;
            }
        }
        return false;
    }
    
    // rate a song  (name and artist)
    
    public boolean rateSong(String title, String artist, int rating) {
        for (Song song : songs) {
            if (song.getSongTitle().equals(title) && song.getArtistName().equals(artist)) {
                song.setRating(rating);
                return true;
            }
        }
        return false;
    }
    
    // add a song from the store
    
    public boolean addSong(String title, String artist) {
        for (Song song : songs) {
            if (song.getSongTitle().equals(title) && song.getArtistName().equals(artist)) {
                return false;
            }
        }
        
        ArrayList<Song> storeSongs = store.searchSongByTitle(title);
        for (Song storeSong : storeSongs) {
            if (storeSong.getArtistName().equals(artist)) {
                Song newSong = new Song(title, artist, storeSong.getAlbumTitle());
                songs.add(newSong);
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
        for (Album storeAlbum : storeAlbums) {
            if (storeAlbum.getArtist().equals(artist)) {
                Album newAlbum = new Album(title, artist, storeAlbum.getGenre(), storeAlbum.getYear());
                
                for (Song storeSong : storeAlbum.getSongs()) {
                    newAlbum.addSong(storeSong.getSongTitle());
                    songs.add(new Song(storeSong.getSongTitle(), artist, title));
                }
                
                albums.add(newAlbum);
                return true;
            }
        }
        
        return false;
    }
    
    
    
    
    
    
  
	
	
	
}

