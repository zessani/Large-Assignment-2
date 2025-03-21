package LA1;

// MusicStore.java


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import LA1.Model.Album;
import LA1.Model.Song;


public class MusicStore {
	private ArrayList<Song> songList;
	private ArrayList<Album> albumList;
	
	public MusicStore(){
		this.songList = new ArrayList<Song>();
		this.albumList = new ArrayList<Album>();
		initializeMusicStore();
	}

	public void addSong(Song song){
		songList.add(song); 
	}
	
	public void addAlbum(Album album){
		albumList.add(album);
	}
	
	public ArrayList<Song> searchSongByTitle(String title){
		title = title.toLowerCase();
		String songTitle = "";
		ArrayList<Song> result = new ArrayList<>();
		for ( Song song : this.songList) {
			songTitle = song.getSongTitle();
			if (songTitle.toLowerCase().equals(title)) {
				result.add(song);
			}
		}
		if (result.size() == 0) {
			System.out.println("Song titled " + title + " cannot be found.\n");
			return null;	
		}
		return result;

		}
	
	public ArrayList<Song> searchSongByArtist(String artist) {
		artist = artist.toLowerCase();
		String songArtist = "";
		ArrayList<Song> result = new ArrayList<>();
		for ( Song song : this.songList) {
			songArtist = song.getArtistName();
			if (songArtist.toLowerCase().equals(artist)) {
				result.add(song);
			}
		}
		if (result.size() == 0) {
			System.out.println("Song made by " + artist + " cannot be found.\n");
			return null;	
		}
		return result;
		}
	
	public ArrayList<Album> searchAlbumByTitle(String title){
		title = title.toLowerCase();
		String albumTitle = "";
		ArrayList<Album> result = new ArrayList<>();
		for ( Album album : this.albumList) {
			albumTitle = album.getTitle();
			if (albumTitle.toLowerCase().equals(title)) {
				result.add(album);
			}
		}
		if (result.size() == 0) {
			System.out.println("Album titled " + title + " cannot be found.\n");
			return null;	
		}
		return result;
		}
	
	public ArrayList<Album> searchAlbumByArtist(String artist){
		artist = artist.toLowerCase();
		String albumArtist = "";
		ArrayList<Album> result = new ArrayList<>();
		for ( Album album : this.albumList) {
			albumArtist = album.getArtist();
			if (albumArtist.toLowerCase().equals(artist)) {
				result.add(album);
			}
		}
		if (result.size() == 0) {
			System.out.println("Album made by " + artist + " cannot be found.\n");
			return null;	
		}
		return result;
		}
	
	public int getNumberOfSongs(){
		return this.songList.size();
	}
	
	public int getNumberOfAlbums(){
		return this.albumList.size();
	}
	

	public void initializeMusicStore(){
		String folderPath = "src/LA1/Resources";


		File folder = new File(folderPath);
		File files[] = folder.listFiles();	
		String[] albumInfoSplit = new String[4];
		String albumInfo, album, artist, genre, year, songsInfo;
		int yearInt;
		for (File file: files){
			if (!file.getName().equals("albums.txt")){
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					albumInfo = br.readLine();
					albumInfoSplit = albumInfo.split(",");
					album = albumInfoSplit[0];
					artist = albumInfoSplit[1];
					genre = albumInfoSplit[2];
					year = albumInfoSplit[3];
					yearInt = Integer.parseInt(year);
					Album newAlbum = new Album(album,artist,genre,yearInt);
					while (true){
						songsInfo = br.readLine();
						if (songsInfo == null){
							this.albumList.add(newAlbum);
							br.close();
							break;
						}
						else{
							Song newSong = new Song(songsInfo,artist,album);
							newAlbum.addSong(songsInfo);
							this.addSong(newSong);
						}
					}
				}catch (IOException e) {
			System.out.println("The file does not have the proper format, skipping '\n'");	
			continue;
				}
			}
		}
		reorganizeAlbums();
	}
	
	
	private void reorganizeAlbums(){
		ArrayList<Album> sortedAlbum = new ArrayList<Album>();
		String folderPath = "src/LA1/Resources";
		File albumFile = new File(folderPath, "albums.txt");
		int i = 0;
		String title,artist;
		String[] albumInfoSplit = new String[2];
		String albumInfo = "";
		try (BufferedReader albumBR = new BufferedReader(new FileReader(albumFile));) {
                while ((albumInfo = albumBR.readLine()) != null) {
                	albumInfoSplit = albumInfo.split(",");
                	title = albumInfoSplit[0];
                	artist = albumInfoSplit[1];
                	for (Album album : this.albumList) {
                		if (album.getArtist().equalsIgnoreCase(artist) && album.getTitle().equalsIgnoreCase(title)){
                			sortedAlbum.add(album);
                		}
                	}
                }
		} catch (IOException e) {
			System.out.println("albums.txt not found\n");
		}	
		this.albumList = sortedAlbum; 
	}
}
