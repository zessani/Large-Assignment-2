package LA1.Model;

public class Song {
	private String name;
	private String artist;
	private String album;
	private int userRating;
	private boolean favorite;
	
	public Song(String song, String artist, String album){
		this.name = song;
		this.artist = artist;
		this.album = album;
		this.favorite = false;
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
	// sets the rating between 1 and 5, and if rating is 5 auto set to favorite
	public void setRating(int rating) {
		if (rating >= 1 && rating <= 5) {
			this.userRating = rating;
			if (rating == 5) {
				this.favorite = true;
			}
		}
	}
	 
	public int getRating(){
		return this.userRating;
	}
	
	public boolean isFavorite() {
		return this.favorite;
	}
	
	public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
	
	
}
