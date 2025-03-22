package LA1.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SongTest {

	@Test
	void test() {
		Song song = new Song("Test Song", "Test Artist", "Test Album","Test Genre");
		
		assertEquals("Test Artist", song.getArtistName());
		assertEquals("Test Song", song.getSongTitle());
		assertEquals("Test Album", song.getAlbumTitle());
		assertEquals("Test Genre", song.getGenre());
	}

}
 