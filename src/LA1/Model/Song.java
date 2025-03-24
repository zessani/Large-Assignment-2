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
	
	public String getGenre() {
		return this.genre;
	}
	
	public boolean equals(Song song){
		if (this.name.equalsIgnoreCase(song.getSongTitle())){
			if (this.artist.equalsIgnoreCase(song.getArtistName())){
				if (this.album.equalsIgnoreCase(song.getAlbumTitle())){
					if (this.genre.equalsIgnoreCase(song.getGenre())){
						return true;
					}
				}
			}
		}
		return false;
	}
}
