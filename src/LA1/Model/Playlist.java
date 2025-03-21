package LA1.Model;

import java.util.ArrayList;


public class Playlist {
	private String name;
	private ArrayList<Song> songs;
	
	public Playlist(String name){
		this.name = name;
		this.songs = new ArrayList<Song>();
	}
	
	public void addSongs(Song song){
		this.songs.add(song);
	}
	
	public void removeSong(Song song){
		this.songs.remove(song);
	}
	
	public int getPlaylistSize(){
		return this.songs.size();
	}
	
	public String getName(){
		return this.name;
	}
	
	public ArrayList<Song> getSongs() {
	    return new ArrayList<>(songs);
	}
	
}

