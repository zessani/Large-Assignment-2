package LA1.Model;

public class Song {
	private String name;
	private String artist;
	private String album;
	private String genre;
	
	public Song(String song, String artist, String album, String genre){
		this.name = song;
		this.artist = artist;
		this.album = album;
		this.genre = genre;
	}
	
	public String getSongTitle(){
		return this.name;
	}
	
	public String getArtistName(){
		return this.artist;
	}

	public String getAlbumTitle(){
		return this.album;
	}
	
	public String getGenre(){
		return this.genre;
	}
}
