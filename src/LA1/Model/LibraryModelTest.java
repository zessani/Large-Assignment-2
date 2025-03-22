package LA1.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import LA1.MusicStore;



class LibraryModelTest {
	@Test
	void test(){ 
		MusicStore store = new MusicStore() {
			@Override
			public void initializeMusicStore() {} 
		};  
		LibraryModel library = new LibraryModel(store);
		 
		Song song1 = new Song("NOKIA", "Drake", "New Drake","Genre");
        Song song2 = new Song("Distance", "AP", "New AP","Genre");
        
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
        
        Song testRating = new Song("meow", "meow", "meow","meow");
        library.addSong(testRating, true);
        library.setRating(testRating, 5);
		assertTrue(library.inFavourites(testRating));
		assertEquals(5, library.getRating(testRating));
		
		Song testRating2 = new Song("Thick of It", "KSI", "Everybody knows","genre");
        library.addSong(testRating2, true);
		library.setRating(testRating2, 3);
		library.addFavourite(testRating2);
		assertTrue(library.inFavourites(testRating2));
		
		
		library.markFavorite("Thick of It", "KSI", false);
		assertFalse(library.inFavourites(testRating2));
 
		Song testRating3 = new Song("My Way","Frank Sinatraa","Album","genre");
        library.addSong(testRating3, true);
		library.setRating(testRating3, 7);
		assertFalse(library.inFavourites(testRating3));
		library.setRating(testRating3, 5);
		library.setRating(testRating3, 2);
		assertEquals(2,library.getRating(testRating3));
		
		Song testRating4 = new Song("Beat It","Michael Jackson","Thriller","genre");
		library.setRating(testRating4, 5);
		assertFalse(library.inFavourites(testRating4));

		Song testRating5 = new Song("Black or White","Michael Jackson","Album","genre");
		assertEquals(library.getRating(testRating5),0);
	}

		@Test
		void testAddSong(){
			MusicStore store = new MusicStore();
			LibraryModel library = new LibraryModel(store);
			Song song = new Song("Daydreamer","Adele","Pop","2018");
			Song song2 = new Song("Best for Last","Adele","Pop","2018");
			boolean added = library.addSong(song, false);
			assertTrue(added);
			added = library.addSong(song, false);
			assertFalse(added);
			added = library.addSong(song2, false);
			assertTrue(added);
			
		}
		
		@Test
		void testAddAlbum(){
			MusicStore store = new MusicStore();
			LibraryModel library = new LibraryModel(store);
			Album album = new Album("Cuando Los Angeles Lloran","Mana","Latin",1995);
			Album album2 = new Album("19","Adele","Pop",2008);
			boolean added = library.addAlbum(album, false);
			assertTrue(added);
			added = library.addAlbum(album, false);
			assertFalse(added);
			added = library.addAlbum(album2,false);
			assertTrue(added);
			added = library.addAlbum("19", "Adele");
			assertFalse(added);
			added = library.addAlbum("Waking Up","OneRepublic");
			assertTrue(added);
			ArrayList<Song> songs = library.searchSongByArtist("OneRepublic");
			assertEquals(11, songs.size());
			added = library.addAlbum("Thriller", "Michael Jackson");
			assertFalse(added);
		}
		
		@Test
		void testPlays(){
			MusicStore store = new MusicStore();
			LibraryModel library = new LibraryModel(store);
			library.addSong("Daydreamer", "Adele");
			library.addSong("Best for Last", "Adele");
			library.addSong("Chasing Pavements", "Adele");
			library.addSong("Cold Shoulder", "Adele");
			library.addSong("Crazy for You", "Adele");
			library.addSong("Melt My Heart to Stone", "Adele");
			library.addSong("First Love", "Adele");
			library.addSong("Right as Rain", "Adele");
			library.addSong("Make You Feel My Love", "Adele");
			library.addSong("My Same", "Adele");
			library.addSong("Tired", "Adele");
			library.addSong("Hometown Glory", "Adele");
			ArrayList<Song> result =library.searchSongByGenre("Pop");
			assertEquals(12,result.size());
			for (int i = 0; i < 10; i++) {
				if (i < 9){
					library.playSong("Best for Last","Adele");
					library.playSong("Chasing Pavements","Adele");
				}
				library.playSong("Tired","Adele");
			}
			library.playSong("Daydreamer", "Adele");
			library.playSong("Cold Shoulder", "Adele");
			library.playSong("Crazy for You", "Adele");
			library.playSong("Melt My Heart to Stone", "Adele");
			library.playSong("First Love", "Adele");
			library.playSong("Right as Rain", "Adele");
			library.playSong("Make You Feel My Love", "Adele");
			library.playSong("My Same", "Adele");
			library.playSong("Tired", "Adele");
			library.playSong("Hometown Glory", "Adele");
			assertEquals(11,library.getPlays("Tired", "Adele"));
			AutoPlaylist playlist = library.getFrequentlyPlayedPlaylist();
			AutoPlaylist playlist2 = library.getRecentlyPlayedPlaylist();
			ArrayList<Song> pl1Song = playlist.getSongs();
			ArrayList<Song> pl2Song = playlist2.getSongs();
			assertEquals(10, pl1Song.size());
			assertEquals(10, pl2Song.size());
			int plays = library.getPlays("Hey Ya", "Outkast");
			assertEquals(plays, 0);
		}
}
 