package LA1;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import LA1.Model.Album;
import LA1.Model.Song;



public class MusicStoreTest {
	@Test
	public void testMusicStoreGetters(){
		MusicStore ms = new MusicStore();
		assertTrue(ms.getNumberOfAlbums() == 15);
		assertTrue(ms.getNumberOfSongs() == 163);
	}
	
	@Test
	public void testAddSongs(){
		MusicStore ms = new MusicStore();
		Song song = new Song("My Way", "Frank Sinatraa", "single", "genre");
		int songListSize = ms.getNumberOfSongs();
		ms.addSong(song);
		assertTrue(songListSize+1 == ms.getNumberOfSongs());
	}
	
	@Test
	public void testAddAlbums(){
		MusicStore ms = new MusicStore();
		Album album = new Album("Thriller","Michael Jackson", "Pop", 1982);
		int albumListSize = ms.getNumberOfAlbums();
		ms.addAlbum(album);
		assertTrue(albumListSize+1 == ms.getNumberOfAlbums());
	}
	
	@Test
	public void testSearchSongByTitle(){
		MusicStore ms = new MusicStore();
		ArrayList<Song> songList = ms.searchSongByTitle("lullaby");
		assertTrue(songList.size() == 2);
	}
	
	@Test
	public void testSearchSongByArtist(){
		MusicStore ms = new MusicStore();
		ArrayList<Song> songList = ms.searchSongByArtist("OneRepublic");
		assertTrue(songList.size() == 11);
	}
	
	@Test
	public void testSearchAlbumByTitle(){
		MusicStore ms = new MusicStore();
		Album testAlbum = new Album("Waking Up", "a", "a", 2025);
		ms.addAlbum(testAlbum);
		ArrayList<Album> albumList = ms.searchAlbumByTitle("Waking Up");
		assertTrue(albumList.size() == 2);
	}
	
	@Test
	public void testSearchAlbumByArtist(){
		MusicStore ms = new MusicStore();
		Album testAlbum = new Album("20","Adele","Pop",2025);
		ArrayList<Album> albumList = ms.searchAlbumByArtist("Adele");
		assertTrue(albumList.size() == 2);
		ms.addAlbum(testAlbum);
		albumList = ms.searchAlbumByArtist("Adele");
		assertTrue(albumList.size() == 3);
	}
	
	 
	/*
	@Test
	public void testAlbumOrder(){
		String[] goodOrder = {"19","21","Begin Again","Boys & Girls","Cuando Los Angeles Lloran","Don't Mess With the Dragon",
				"Fight for Your Mind", "Mission Bell","Old Ideas","Sigh No More","Waking Up","A Rush of Blood to the Head",
				"Coat of Many Colors","Tapestry","Sons"};
		MusicStore ms = new MusicStore();
		int i = 0;
		for (Album album: ms.albumList){
			assertTrue(album.getTitle().equalsIgnoreCase(goodOrder[i]));
			i++;
		}		
	}
	*/
}
