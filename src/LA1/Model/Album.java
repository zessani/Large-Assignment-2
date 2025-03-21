package LA1.Model;

import java.util.ArrayList;

public class Album {

	private String title;
	private String artist;
	private String genre;
	private int year;
	private ArrayList<Song> songs;
	
	
	public Album (String title, String artist, String genre, int year){
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
		this.songs = new ArrayList<>();
		
		
	}
	
	public String getTitle() {
		return title;
	} 
	
	public String getArtist() {
		return artist;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public int getYear() {
		return year;
	}
	
	public ArrayList<Song> getSongs(){
		return new ArrayList<>(songs);
	}
	
	public Integer countSongs() {
		return songs.size()
;	}
	
	// gets song by title from the Song class
	public Song getSong(String title) {
		for (Song song : songs) {
	        if (song.getSongTitle().equals(title)) {   
	            return song;
	        }
	    }
	    return null;
		
	}
		
	// adds a song to the album
	public void addSong (String songTitle) {
		songs.add(new Song(songTitle, this.artist, this.title));
	}

}
