package LA1.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SongTest {

	@Test
	void test() {
		Song song = new Song("Test Song", "Test Artist", "Test Album");
		
		assertEquals("Test Artist", song.getArtistName());
		assertEquals("Test Song", song.getSongTitle());
		assertEquals("Test Album", song.getAlbumTitle());
		
		song.setRating(5);
		assertTrue(song.isFavorite());
		assertEquals(5, song.getRating());
		
		song.setRating(3);
		song.setFavorite(true); 
		assertTrue(song.isFavorite());
		
		song.setFavorite(false);
		assertFalse(song.isFavorite());
		
		song.setRating(7);
		
		song.setRating(0);
		
		
	}

}
