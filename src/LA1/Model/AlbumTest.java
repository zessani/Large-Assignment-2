package LA1.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class AlbumTest {

	@Test
	void test() {
		Album album = new Album("Test Album", "Test Artist", "Pop", 2019);
		
		assertEquals("Test Album", album.getTitle());
		assertEquals("Test Artist", album.getArtist());
		assertEquals("Pop", album.getGenre());
		assertEquals(2019, album.getYear());
		
		album.addSong("Blockbuster");
		album.addSong("Flight's Booked");
		
		Song song = album.getSong("Blockbuster");
		
		assertEquals("Blockbuster", song.getSongTitle());
		
		ArrayList<Song> songs = album.getSongs();
        assertEquals(2, songs.size());
        
        assertNull(album.getSong("Flashing Lights")); 
	}

}
