package LA1.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlaylistTest {

	@Test
	void test() {
		Playlist playlist = new Playlist("Test Playlist");
		
		assertEquals("Test Playlist", playlist.getName());
		
		Song song1 = new Song("Blockbuster", "Manu", "Album");
		playlist.addSongs(song1);
		
		assertEquals(1, playlist.getPlaylistSize()); 
	}

}
 