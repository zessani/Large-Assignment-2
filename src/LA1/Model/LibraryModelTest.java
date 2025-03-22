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
        
        library.addSong(song1,true);
        library.addSong(song2,true);
        
        Album album1 = new Album("New Drake", "Drake", "Hip Hop", 2025);
        Album album2 = new Album("New AP", "AP", "Pop", 2020);
        
        library.addAlbum(album1,true);
        library.addAlbum(album2,true);
        
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
        assertTrue(library.inFavourites(song1));
        ArrayList<Song> allFavourites = library.getFavorites();
        assertEquals(1, allFavourites.size());
        
        boolean unMarked = library.markFavorite("NOKIA", "Drake", false);
        assertTrue(unMarked);
        assertFalse(library.inFavourites(song1));
        
        boolean mark2 = library.markFavorite("Flight", "Drake", true);
        assertFalse(mark2);
          
        
        // test ratings
        
        boolean rated = library.rateSong("NOKIA","Drake", 2);
        assertTrue(rated);
        assertEquals(2, library.getRating(song1));
        
        boolean rated2 = library.rateSong("Distance", "AP", 5);
        assertTrue(rated2);
        assertEquals(5, library.getRating(song2));
        assertTrue(library.inFavourites(song2));
        
        boolean rated3 = library.rateSong("FLY","Drake", 9);
        assertFalse(rated3);
 
        // test add song/album
        
        Song testRating = new Song("meow", "meow", "meow");
        library.addSong(testRating, true);
        library.setRating(testRating, 5);
		assertTrue(library.inFavourites(testRating));
		assertEquals(5, library.getRating(testRating));
		
		Song testRating2 = new Song("Thick of It", "KSI", "Everybody knows");
        library.addSong(testRating2, true);
		library.setRating(testRating2, 3);
		library.addFavourite(testRating2);
		assertTrue(library.inFavourites(testRating2));
		
		
		library.markFavorite("Thick of It", "KSI", false);
		assertFalse(library.inFavourites(testRating2));
 
		Song testRating3 = new Song("My Way","Frank Sinatraa","Album");
        library.addSong(testRating3, true);
		library.setRating(testRating3, 7);
		assertFalse(library.inFavourites(testRating3));
		
		Song testRating4 = new Song("Beat It","Michael Jackson","Thriller");
		library.setRating(testRating4, 5);
		assertFalse(library.inFavourites(testRating3));
         
        
     
	} 
	
	@Test
	void testSorting() {
		MusicStore store = new MusicStore() {
			@Override
			public void initializeMusicStore() {} 
		};  
		LibraryModel library = new LibraryModel(store);
		
		Song song1 = new Song("NOKIA", "Drake", "New Drake");
        Song song2 = new Song("Distance", "AP", "New AP");
        Song song3 = new Song("Charge Me", "Opium", "New OP");
		
		// add to library
        
        library.addSong(song1, true);
        library.addSong(song2, true);
        library.addSong(song3, true);
        
        // Set ratings for the songs
        library.setRating(song1, 3);
        library.setRating(song2, 5);
        library.setRating(song3, 1);
        
        // Test sort by title
        ArrayList<Song> titleSorted = library.getSongsSortedByTitle();
        assertEquals("Charge Me", titleSorted.get(0).getSongTitle());
        assertEquals("Distance", titleSorted.get(1).getSongTitle());
        assertEquals("NOKIA", titleSorted.get(2).getSongTitle());  
        
        // test by artist
        ArrayList<Song> artistSorted = library.getSongsSortedByArtist();
        assertEquals("AP", artistSorted.get(0).getArtistName());
        assertEquals("Drake", artistSorted.get(1).getArtistName());
        assertEquals("Opium", artistSorted.get(2).getArtistName());
        
        // by rating
        ArrayList<Song> ratingSorted = library.getSongsSortedByRating();
        assertEquals("Distance", ratingSorted.get(0).getSongTitle()); 
        assertEquals("NOKIA", ratingSorted.get(1).getSongTitle());   
        assertEquals("Charge Me", ratingSorted.get(2).getSongTitle());
        
        // Test removing a song
        
        library.setRating(song1, 5);
        assertTrue(library.removeSong("NOKIA", "Drake"));
        assertFalse(library.inFavourites(song1));
        
        ArrayList<Song> results = library.searchSongByTitle("NOKIA");
        assertTrue(results.isEmpty());
        
        Album album = new Album("New Album", "Drake", "Pop", 2023);
        library.addAlbum(album, true);
        assertTrue(library.removeAlbum("New Album", "Drake"));
        
        // All songs from album should be gone
        results = library.searchSongByTitle("NOKIA");
        assertTrue(results.isEmpty());
        
	}  
	 
	

}
 