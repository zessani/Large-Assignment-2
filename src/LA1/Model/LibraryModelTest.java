package LA1.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import LA1.MusicStore;



class LibraryModelTest {

	@Test
	void test() { 
		MusicStore store = new MusicStore() {
			@Override
			public void initializeMusicStore() {} 
		};  
		LibraryModel library = new LibraryModel(store);
		
		
		 
		Song song1 = new Song("NOKIA", "Drake", "New Drake");
        Song song2 = new Song("Distance", "AP", "New AP");
        
        library.songs.add(song1);
        library.songs.add(song2);
        
        Album album1 = new Album("New Drake", "Drake", "Hip Hop", 2025);
        Album album2 = new Album("New AP", "AP", "Pop", 2020);
        
        library.albums.add(album1);
        library.albums.add(album2);
        
        // test search methods
        
        ArrayList<Song> result = library.searchSongByTitle("NOKIA");
        System.out.println("Result: " + result);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("NOKIA", result.get(0).getSongTitle());
        
        ArrayList<Song> artistResult = library.searchSongByArtist("Drake");
        assertNotNull(artistResult);
        assertEquals(1, artistResult.size());
        
        ArrayList<Album> albumResult = library.searchAlbumByArtist("Drake");
        assertNotNull(albumResult); 
        assertEquals(1, albumResult.size()); 
        assertEquals("Drake", albumResult.get(0).getArtist());
        
        ArrayList<Album> albumResult2 = library.searchAlbumByTitle("New Drake");
        assertNotNull(albumResult2); 
        assertEquals(1, albumResult2.size()); 
        
        // test playlist methods
        
        library.newPlaylist("New Playlist");
        
        Playlist playlist = library.searchPlaylistByName("New Playlist");
        assertEquals("New Playlist", playlist.getName());
        
        Playlist resultPlaylist = library.searchPlaylistByName("hello");
        assertNull(resultPlaylist, "null"); 
         
        boolean added = library.addToPlaylist("New Playlist", "NOKIA", "Drake");
        assertTrue(added); 
        
        boolean falseAdd = library.addToPlaylist("New Playlist", "FLY", "Drake");
        assertFalse(falseAdd);
        
        boolean removed = library.removeFromPlaylist("New Playlist", "NOKIA", "Drake");
        assertTrue(removed);
        
        boolean nullTest = library.addToPlaylist("null", "NOKIA", "Drake");
        assertFalse(nullTest);
        
        boolean nullRemove = library.removeFromPlaylist("null", "NOKIA", "Drake");
        assertFalse(nullRemove);
        
        boolean falseRemove = library.removeFromPlaylist("New Playlist", "FLY", "Drake");
        assertFalse(falseRemove);
        
        
        // testing get all lists methods
        
        ArrayList<String> songTitles = library.getAllSongTitles();
        assertEquals(2, songTitles.size());
        assertTrue(songTitles.contains("NOKIA"));
        
        ArrayList<String> artists = library.getAllArtists();
        assertEquals(2, artists.size()); 
        
        ArrayList<String> albumTitles = library.getAllAlbumTitles();
        assertEquals(2, albumTitles.size());
        
        ArrayList<String> playlistTitles = library.getAllPlaylists();
        assertEquals(1, playlistTitles.size());
        
        // test favourites
        
        boolean marked = library.markFavorite("NOKIA", "Drake", true);
        assertTrue(marked);
        assertTrue(song1.isFavorite());
        ArrayList<Song> allFavourites = library.getFavorites();
        assertEquals(1, allFavourites.size());
        
        boolean unMarked = library.markFavorite("NOKIA", "Drake", false);
        assertTrue(unMarked);
        assertFalse(song1.isFavorite());
        
        boolean mark2 = library.markFavorite("Flight", "Drake", true);
        assertFalse(mark2);
          
        
        // test ratings
        
        boolean rated = library.rateSong("NOKIA","Drake", 2);
        assertTrue(rated);
        assertEquals(2, song1.getRating());
        
        boolean rated2 = library.rateSong("Distance", "AP", 5);
        assertTrue(rated2);
        assertEquals(5, song2.getRating());
        assertTrue(song2.isFavorite());
        
        boolean rated3 = library.rateSong("FLY","Drake", 9);
        assertFalse(rated3);
 
        // test add song/album
 
      
         
        
     
	} 
	 
	

}
 