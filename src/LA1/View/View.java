package LA1.View;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import LA1.MusicStore;
import LA1.Model.Album;
import LA1.Model.AutoPlaylist;
import LA1.Model.LibraryModel;
import LA1.Model.Playlist;
import LA1.Model.Song;
import Users.users;

public class View {
    private LibraryModel model;
    private MusicStore store;
    private Scanner scanner;
    private users userManager;
    private boolean loggedIn = false;
    private String currentUsername = null;
    
    // Constants for automatic playlists
    private static final String RECENTLY_PLAYED_PLAYLIST = "Recently Played";
    private static final String MOST_PLAYED_PLAYLIST = "Frequently Played";
    private static final String FAVORITES_PLAYLIST = "Favourites";
    private static final String TOP_RATED_PLAYLIST = "Top Rated";
    
    public View(MusicStore store) {
        this.store = store;
        this.scanner = new Scanner(System.in);  
        
        try {
            this.userManager = new users(store);
        } catch (IOException e) {
            System.out.println("Error initializing user system: " + e.getMessage());
            System.exit(1);
        }
    }
    
    public void start() {
        System.out.println("Welcome to Music Library!");
        
        // Start with login/register screen
        userMenu();
        
        if (loggedIn) {
            mainMenu();
        }
        
        System.out.println("Thanks for using Music Library!");
        scanner.close();
    }
    
    private void userMenu() {
        boolean userMenuRunning = true;
        
        while (userMenuRunning && !loggedIn) {
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("> ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1": 
                    login();
                    if (loggedIn) {
                        userMenuRunning = false;
                    }
                    break;
                case "2": 
                    register(); 
                    break;
                case "0": 
                    userMenuRunning = false; 
                    break;
                default: 
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private void login() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        if (userManager.login(username, password)) {
            loggedIn = true;
            currentUsername = username;
            model = userManager.getCurrentLibrary();
            System.out.println("Login successful! Welcome, " + username + "!");
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }
    
    private void register() {
        System.out.println("\n--- REGISTER ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        try {
            if (userManager.registerUser(username, password)) {
                System.out.println("Registration successful! You can now login.");
            } else {
                System.out.println("Registration failed. Username already exists.");
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Registration error: " + e.getMessage());
        }
    }
    
    private void logout() {
        userManager.logout();
        loggedIn = false;
        currentUsername = null;
        model = null;
        System.out.println("You have been logged out.");
        
        // Return to login screen
        userMenu();
    }
    
    private void mainMenu() {
        boolean running = true;
        
        while (running && loggedIn) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Search Music");
            System.out.println("2. My Library");
            System.out.println("3. Playlists");
            System.out.println("4. Rate & Favorite");
            System.out.println("5. Play Music");
            System.out.println("6. Logout");
            System.out.println("0. Exit");
            System.out.print("> ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1": searchMusic(); break;
                case "2": viewLibrary(); break;
                case "3": managePlaylists(); break;
                case "4": rateAndFavorite(); break;
                case "5": playMusic(); break;
                case "6": 
                    logout();
                    if (!loggedIn) running = false;
                    break;
                case "0": running = false; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }
    
    private void searchMusic() {
        boolean searchRunning = true;
        
        while (searchRunning) {
            System.out.println("\n--- SEARCH ---");
            System.out.println("1. Find song");
            System.out.println("2. Find album");
            System.out.println("3. Search by genre");
            System.out.println("0. Back");
            System.out.print("> ");
            
            String choice = scanner.nextLine().trim();
            
            if (choice.equals("0")) {
                searchRunning = false;
                continue;
            }
            
            System.out.print("Enter search term: ");
            String term = scanner.nextLine().trim();
            
            switch (choice) {
                case "1": 
                    searchSongs(term);
                    break;
                case "2": 
                    searchAlbums(term);
                    break;
                case "3": 
                    searchByGenre(term);
                    break;
                default: 
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private void searchSongs(String term) {
        ArrayList<Song> songs = store.searchSongByTitle(term);
        if (songs != null && !songs.isEmpty()) {
            System.out.println("\nFound songs:");
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                System.out.println((i+1) + ". " + song.getSongTitle() + " - " + song.getArtistName());
            }
            
            System.out.println("\nOptions:");
            System.out.println("1. Add to library");
            System.out.println("2. Get album information");
            System.out.println("0. Back");
            System.out.print("> ");
            
            String option = scanner.nextLine().trim();
            
            if (option.equals("1")) {
                System.out.print("Enter song number to add: ");
                try {
                    int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
                    if (index >= 0 && index < songs.size()) {
                        Song song = songs.get(index);
                        
                        // Add the song to the library
                        boolean success = model.addSong(song.getSongTitle(), song.getArtistName());
                        if (success) {
                            System.out.println("Song added to library!");
                            
                            // The model's addSong method should automatically handle adding the album
                            System.out.println("Album '" + song.getAlbumTitle() + "' has been updated in your library.");
                        } else {
                            System.out.println("Song could not be added or already exists in library.");
                        }
                    } else {
                        System.out.println("Invalid song number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                }
            } else if (option.equals("2")) {
                System.out.print("Enter song number to get album info: ");
                try {
                    int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
                    if (index >= 0 && index < songs.size()) {
                        Song song = songs.get(index);
                        
                        // First check in the store for the album
                        ArrayList<Album> storeAlbums = store.searchAlbumByTitle(song.getAlbumTitle());
                        Album album = null;
                        
                        // Find the matching album by artist
                        if (storeAlbums != null) {
                            for (Album a : storeAlbums) {
                                if (a.getArtist().equals(song.getArtistName())) {
                                    album = a;
                                    break;
                                }
                            }
                        }
                        
                        if (album != null) {
                            displayAlbumInfo(album);
                            boolean albumInLibrary = false;
                            
                            // Check if album is in the library
                            ArrayList<String> libraryAlbums = model.getAllAlbumTitles();
                            if (libraryAlbums != null) {
                                for (String title : libraryAlbums) {
                                    ArrayList<Album> albums = model.searchAlbumByTitle(title);
                                    if (albums != null) {
                                        for (Album a : albums) {
                                            if (a.getTitle().equals(album.getTitle()) && 
                                                a.getArtist().equals(album.getArtist())) {
                                                albumInLibrary = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (albumInLibrary) break;
                                }
                            }
                            
                            System.out.println("\nAlbum is " + 
                                (albumInLibrary ? "already in your library." : "not in your library."));
                            
                            if (!albumInLibrary) {
                                System.out.print("Add album to library? (y/n): ");
                                if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                                    model.addAlbum(album.getTitle(), album.getArtist());
                                    System.out.println("Album added to library!");
                                }
                            }
                        } else {
                            System.out.println("Album information not available.");
                        }
                    } else {
                        System.out.println("Invalid song number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                }
            }
        } else {
            System.out.println("No songs found matching \"" + term + "\".");
        }
    }
    
    private void searchAlbums(String term) {
        ArrayList<Album> albums = store.searchAlbumByTitle(term);
        if (albums != null && !albums.isEmpty()) {
            System.out.println("\nFound albums:");
            for (int i = 0; i < albums.size(); i++) {
                Album album = albums.get(i);
                System.out.println((i+1) + ". " + album.getTitle() + " - " + album.getArtist());
            }
            System.out.print("\nAdd to library? (album #/n): ");
            String answer = scanner.nextLine().trim();
            
            if (!answer.equalsIgnoreCase("n")) {
                try {
                    int index = Integer.parseInt(answer) - 1;
                    if (index >= 0 && index < albums.size()) {
                        Album album = albums.get(index);
                        model.addAlbum(album.getTitle(), album.getArtist());
                        System.out.println("Album added to library!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                }
            }
        } else {
            System.out.println("No albums found matching \"" + term + "\".");
        }
    }
    
    private void searchByGenre(String genre) {
        ArrayList<Song> songs = model.searchSongByGenre(genre);
        if (songs != null && !songs.isEmpty()) {
            System.out.println("\nFound songs in genre \"" + genre + "\":");
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                if (song != null) {
                    System.out.println((i+1) + ". " + song.getSongTitle() + " - " + song.getArtistName());
                }
            }
        } else {
            System.out.println("No songs found in genre \"" + genre + "\".");
        }
    }
    
    private void displayAlbumInfo(Album album) {
        if (album == null) {
            System.out.println("Album information not available.");
            return;
        }
        
        System.out.println("\n--- ALBUM INFORMATION ---");
        System.out.println("Title: " + album.getTitle());
        System.out.println("Artist: " + album.getArtist());
        System.out.println("Genre: " + album.getGenre());
        System.out.println("Year: " + album.getYear());
        System.out.println("Songs: " + album.countSongs());
        
        ArrayList<Song> songs = album.getSongs();
        if (songs != null && !songs.isEmpty()) {
            System.out.println("\nTrack List:");
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                if (song != null) {
                    System.out.println((i+1) + ". " + song.getSongTitle());
                }
            }
        }
    }
    
    private void viewLibrary() {
        boolean libraryRunning = true;
        
        while (libraryRunning) {
            System.out.println("\n--- MY LIBRARY ---");
            System.out.println("Current User: " + currentUsername);
            System.out.println("1. View All Songs");
            System.out.println("2. View Albums");
            System.out.println("3. View Favorites");
            System.out.println("4. Sort Songs");
            System.out.println("5. Remove Song/Album");
            System.out.println("6. Shuffle Library");
            System.out.println("0. Back");
            System.out.print("> ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1": // View All Songs
                    viewAllSongs();
                    break;
                case "2": // View Albums
                    viewAlbums();
                    break;
                case "3": // View Favorites
                    viewFavorites();
                    break;
                case "4": // Sort Songs
                    sortSongs();
                    break;
                case "5": // Remove Song/Album
                    removeFromLibrary();
                    break;
                case "6": // Shuffle Library
                    shuffleLibrary();
                    break;
                case "0": 
                    libraryRunning = false;
                    break;
                default: 
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private void viewAllSongs() {
        ArrayList<String> songTitles = model.getAllSongTitles();
        if (songTitles == null || songTitles.isEmpty()) {
            System.out.println("No songs in library.");
        } else {
            System.out.println("\nYour songs:");
            for (int i = 0; i < songTitles.size(); i++) {
                System.out.println((i+1) + ". " + songTitles.get(i));
            }
        }
    }
    
    private void viewAlbums() {
        ArrayList<String> albumTitles = model.getAllAlbumTitles();
        if (albumTitles == null || albumTitles.isEmpty()) {
            System.out.println("No albums in library.");
        } else {
            System.out.println("\nYour albums:");
            for (int i = 0; i < albumTitles.size(); i++) {
                System.out.println((i+1) + ". " + albumTitles.get(i));
            }
            
            System.out.print("\nView album details? (album #/n): ");
            String answer = scanner.nextLine().trim();
            
            if (!answer.equalsIgnoreCase("n")) {
                try {
                    int index = Integer.parseInt(answer) - 1;
                    if (index >= 0 && index < albumTitles.size()) {
                        String title = albumTitles.get(index);
                        ArrayList<Album> albums = model.searchAlbumByTitle(title);
                        
                        if (albums != null && !albums.isEmpty()) {
                            Album album = albums.get(0);
                            System.out.println("\n--- ALBUM DETAILS ---");
                            System.out.println("Title: " + album.getTitle());
                            System.out.println("Artist: " + album.getArtist());
                            System.out.println("Genre: " + album.getGenre());
                            System.out.println("Year: " + album.getYear());
                            
                            // Get just the songs from this album that are in the user's library
                            ArrayList<Song> userSongs = new ArrayList<>();
                            ArrayList<String> songTitles = model.getAllSongTitles();
                            
                            if (songTitles != null) {
                                for (String songTitle : songTitles) {
                                    ArrayList<Song> songs = model.searchSongByTitle(songTitle);
                                    if (songs != null && !songs.isEmpty()) {
                                        for (Song song : songs) {
                                            if (song != null && 
                                                song.getAlbumTitle().equals(album.getTitle()) && 
                                                song.getArtistName().equals(album.getArtist())) {
                                                userSongs.add(song);
                                            }
                                        }
                                    }
                                }
                            }
                            
                            if (userSongs.isEmpty()) {
                                System.out.println("\nNo songs from this album in your library.");
                            } else {
                                System.out.println("\nSongs from this album in your library:");
                                for (int i = 0; i < userSongs.size(); i++) {
                                    Song song = userSongs.get(i);
                                    System.out.println((i+1) + ". " + song.getSongTitle());
                                }
                            }
                        }
                    } else {
                        System.out.println("Invalid album number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                }
            }
        }
    }
    
    private void viewFavorites() {
        ArrayList<Song> favorites = model.getFavorites();
        if (favorites == null || favorites.isEmpty()) {
            System.out.println("No favorite songs.");
        } else {
            System.out.println("\nYour favorites:");
            for (int i = 0; i < favorites.size(); i++) {
                Song song = favorites.get(i);
                if (song != null) {
                    System.out.println((i+1) + ". " + song.getSongTitle() + " - " + song.getArtistName());
                }
            }
        }
    }
    
    private void sortSongs() {
        System.out.println("\n--- SORT SONGS ---");
        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Artist");
        System.out.println("3. Sort by Rating");
        System.out.println("0. Back");
        System.out.print("> ");
        
        String choice = scanner.nextLine().trim();
        ArrayList<Song> sortedSongs = null;
        
        switch (choice) {
            case "1": // Sort by Title
                sortedSongs = model.getSongsSortedByTitle();
                System.out.println("\nSongs sorted by title:");
                break;
            case "2": // Sort by Artist
                sortedSongs = model.getSongsSortedByArtist();
                System.out.println("\nSongs sorted by artist:");
                break;
            case "3": // Sort by Rating
                sortedSongs = model.getSongsSortedByRating();
                System.out.println("\nSongs sorted by rating (highest first):");
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        if (sortedSongs != null && !sortedSongs.isEmpty()) {
            for (int i = 0; i < sortedSongs.size(); i++) {
                Song song = sortedSongs.get(i);
                if (song != null) {
                    int rating = model.getRating(song);
                    String ratingStr = rating > 0 ? " (Rating: " + rating + ")" : "";
                    System.out.println((i+1) + ". " + song.getSongTitle() + " - " + song.getArtistName() + ratingStr);
                }
            }
        } else {
            System.out.println("No songs in library.");
        }
    }
    
    private void removeFromLibrary() {
        System.out.println("\n--- REMOVE FROM LIBRARY ---");
        System.out.println("1. Remove Song");
        System.out.println("2. Remove Album");
        System.out.println("0. Back");
        System.out.print("> ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1": // Remove Song
                removeSong();
                break;
            case "2": // Remove Album
                removeAlbum();
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void removeSong() {
        ArrayList<String> songTitles = model.getAllSongTitles();
        if (songTitles == null || songTitles.isEmpty()) {
            System.out.println("No songs in library to remove.");
            return;
        }
        
        System.out.println("\nSelect song to remove:");
        for (int i = 0; i < songTitles.size(); i++) {
            System.out.println((i+1) + ". " + songTitles.get(i));
        }
        System.out.print("> ");
        
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index >= 0 && index < songTitles.size()) {
                String title = songTitles.get(index);
                ArrayList<Song> songs = model.searchSongByTitle(title);
                
                if (songs != null && !songs.isEmpty()) {
                    Song song = songs.get(0);
                    if (song != null) {
                        System.out.print("Are you sure you want to remove \"" + song.getSongTitle() + "\"? (y/n): ");
                        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                            model.removeSong(song.getSongTitle(), song.getArtistName());
                            System.out.println("Song removed from library.");
                        } else {
                            System.out.println("Operation cancelled.");
                        }
                    }
                }
            } else {
                System.out.println("Invalid song number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }
    
    private void removeAlbum() {
        ArrayList<String> albumTitles = model.getAllAlbumTitles();
        if (albumTitles == null || albumTitles.isEmpty()) {
            System.out.println("No albums in library to remove.");
            return;
        }
        
        System.out.println("\nSelect album to remove:");
        for (int i = 0; i < albumTitles.size(); i++) {
            System.out.println((i+1) + ". " + albumTitles.get(i));
        }
        System.out.print("> ");
        
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index >= 0 && index < albumTitles.size()) {
                String title = albumTitles.get(index);
                ArrayList<Album> albums = model.searchAlbumByTitle(title);
                
                if (albums != null && !albums.isEmpty()) {
                    Album album = albums.get(0);
                    if (album != null) {
                        System.out.print("Are you sure you want to remove \"" + album.getTitle() + "\"? (y/n): ");
                        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                            model.removeAlbum(album.getTitle(), album.getArtist());
                            System.out.println("Album removed from library.");
                        } else {
                            System.out.println("Operation cancelled.");
                        }
                    }
                }
            } else {
                System.out.println("Invalid album number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }
    
    private void shuffleLibrary() {
        ArrayList<Song> shuffledSongs = model.getShuffledSongs();
        if (shuffledSongs == null || shuffledSongs.isEmpty()) {
            System.out.println("No songs in library to shuffle.");
            return;
        }
        
        System.out.println("\n--- SHUFFLED LIBRARY ---");
        for (int i = 0; i < shuffledSongs.size(); i++) {
            Song song = shuffledSongs.get(i);
            if (song != null) {
                System.out.println((i+1) + ". " + song.getSongTitle() + " - " + song.getArtistName());
            }
        }
    }
    
    private void managePlaylists() {
        boolean playlistsRunning = true;
        
        while (playlistsRunning) {
            System.out.println("\n--- PLAYLISTS ---");
            System.out.println("1. View Playlists");
            System.out.println("2. Create Playlist");
            System.out.println("3. Add Song to Playlist");
            System.out.println("4. View Automatic Playlists");
            System.out.println("5. Shuffle Playlist");
            System.out.println("0. Back");
            System.out.print("> ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1": // View Playlists
                    viewPlaylists();
                    break;
                case "2": // Create Playlist
                    createPlaylist();
                    break;
                case "3": // Add Song to Playlist
                    addToPlaylist();
                    break;
                case "4": // View Automatic Playlists
                    viewAutomaticPlaylists();
                    break;
                case "5": // Shuffle Playlist
                    shufflePlaylist();
                    break;
                case "0": 
                    playlistsRunning = false;
                    break;
                default: 
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private void viewPlaylists() {
        ArrayList<String> playlists = model.getAllPlaylists();
        if (playlists == null || playlists.isEmpty()) {
            System.out.println("No playlists.");
        } else {
            System.out.println("\nYour playlists:");
            for (int i = 0; i < playlists.size(); i++) {
                System.out.println((i+1) + ". " + playlists.get(i));
            }
            
            System.out.print("\nView playlist details? (playlist #/n): ");
            String answer = scanner.nextLine().trim();
            
            if (!answer.equalsIgnoreCase("n")) {
                try {
                    int index = Integer.parseInt(answer) - 1;
                    if (index >= 0 && index < playlists.size()) {
                        String name = playlists.get(index);
                        Playlist playlist = model.searchPlaylistByName(name);
                        if (playlist != null) {
                            System.out.println("\nSongs in '" + name + "':");
                            ArrayList<Song> songs = playlist.getSongs();
                            if (songs == null || songs.isEmpty()) {
                                System.out.println("Playlist is empty.");
                            } else {
                                for (int i = 0; i < songs.size(); i++) {
                                    Song song = songs.get(i);
                                    if (song != null) {
                                        System.out.println((i+1) + ". " + song.getSongTitle() + " - " + song.getArtistName());
                                    }
                                }
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                }
            }
        }
    }
    
    private void createPlaylist() {
        System.out.print("\nEnter playlist name: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            model.newPlaylist(name);
            System.out.println("Playlist '" + name + "' created!");
        } else {
            System.out.println("Playlist name cannot be empty.");
        }
    }
    
    private void addToPlaylist() {
        ArrayList<String> playlists = model.getAllPlaylists();
        if (playlists == null || playlists.isEmpty()) {
            System.out.println("Create a playlist first.");
            return;
        }
        
        System.out.println("\nSelect playlist:");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i+1) + ". " + playlists.get(i));
        }
        System.out.print("> ");
        
        try {
            int playlistIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (playlistIndex >= 0 && playlistIndex < playlists.size()) {
                String playlistName = playlists.get(playlistIndex);
                
                ArrayList<String> songTitles = model.getAllSongTitles();
                if (songTitles == null || songTitles.isEmpty()) {
                    System.out.println("Add songs to your library first.");
                    return;
                }
                
                System.out.println("\nSelect song:");
                for (int i = 0; i < songTitles.size(); i++) {
                    System.out.println((i+1) + ". " + songTitles.get(i));
                }
                System.out.print("> ");
                
                int songIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (songIndex >= 0 && songIndex < songTitles.size()) {
                    String songTitle = songTitles.get(songIndex);
                    ArrayList<Song> songs = model.searchSongByTitle(songTitle);
                    
                    if (songs == null || songs.isEmpty()) {
                        System.out.println("Song not found.");
                    } else {
                        Song song = songs.get(0);
                        if (song != null) {
                            model.addToPlaylist(playlistName, song.getSongTitle(), song.getArtistName());
                            System.out.println("Song added to playlist!");
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }
    
    private void viewAutomaticPlaylists() {
        System.out.println("\n--- AUTOMATIC PLAYLISTS ---");
        System.out.println("1. Recently Played");
        System.out.println("2. Most Frequently Played");
        System.out.println("3. Favorites");
        System.out.println("4. Top Rated (4-5 stars)");
        System.out.println("5. Genre Playlists");
        System.out.println("0. Back");
        System.out.print("> ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1": // Recently Played
                displayAutoPlaylist(model.getRecentlyPlayedPlaylist());
                break;
            case "2": // Most Frequently Played
                displayAutoPlaylist(model.getFrequentlyPlayedPlaylist());
                break;
            case "3": // Favorites
                ArrayList<Song> favorites = model.getFavorites();
                if (favorites == null || favorites.isEmpty()) {
                    System.out.println("No favorite songs.");
                } else {
                    System.out.println("\nYour favorites:");
                    for (int i = 0; i < favorites.size(); i++) {
                        Song song = favorites.get(i);
                        if (song != null) {
                            System.out.println((i+1) + ". " + song.getSongTitle() + " - " + song.getArtistName());
                        } else {
                            System.out.println((i+1) + ". [Invalid song entry]");
                        }
                    }
                }
                break;
            case "4": // Top Rated
                // Displaying songs with rating 4-5
                ArrayList<Song> topRated = new ArrayList<>();
                ArrayList<String> allSongs = model.getAllSongTitles();
                
                if (allSongs != null) {
                    for (String title : allSongs) {
                        ArrayList<Song> songs = model.searchSongByTitle(title);
                        if (songs != null && !songs.isEmpty()) {
                            Song song = songs.get(0);
                            if (song != null) {
                                int rating = model.getRating(song);
                                if (rating >= 4) {
                                    topRated.add(song);
                                }
                            }
                        }
                    }
                }
                
                if (topRated.isEmpty()) {
                    System.out.println("No songs rated 4-5 stars.");
                } else {
                    System.out.println("\nTop rated songs:");
                    for (int i = 0; i < topRated.size(); i++) {
                        Song song = topRated.get(i);
                        if (song != null) {
                            System.out.println((i+1) + ". " + song.getSongTitle() + " - " + song.getArtistName() + 
                                             " (Rating: " + model.getRating(song) + ")");
                        } else {
                            System.out.println((i+1) + ". [Invalid song entry]");
                        }
                    }
                }
                break;
            case "5": // Genre Playlists
                viewGenrePlaylists();
                break;
            case "0": // Back
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
    
    private void displayAutoPlaylist(AutoPlaylist playlist) {
        if (playlist == null) {
            System.out.println("Playlist not available.");
            return;
        }
        
        ArrayList<Song> songs = playlist.getSongs();
        if (songs == null || songs.isEmpty()) {
            System.out.println("No songs in the " + playlist.getName() + " playlist.");
        } else {
            System.out.println("\n" + playlist.getName() + ":");
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                if (song != null) {
                    System.out.println((i+1) + ". " + song.getSongTitle() + " - " + song.getArtistName());
                } else {
                    System.out.println((i+1) + ". [Invalid song entry]");
                }
            }
        }
    }
    
    private void viewGenrePlaylists() {
        // Get all genre playlists from the model
        ArrayList<AutoPlaylist> genrePlaylists = model.getGenrePlaylist();
        
        if (genrePlaylists == null || genrePlaylists.isEmpty()) {
            System.out.println("No genre playlists available.");
            return;
        }
        
        System.out.println("\nGenre Playlists:");
        // List only genres that have at least 10 songs
        int validPlaylistCount = 0;
        for (int i = 0; i < genrePlaylists.size(); i++) {
            AutoPlaylist playlist = genrePlaylists.get(i);
            if (playlist != null && playlist.getPlaylistSize() >= 10) {
                System.out.println((i+1) + ". " + playlist.getName() + " (" + playlist.getPlaylistSize() + " songs)");
                validPlaylistCount++;
            }
        }
        
        if (validPlaylistCount == 0) {
            System.out.println("No genres with 10 or more songs available.");
            return;
        }
        
        System.out.print("\nView genre playlist? (playlist #/n): ");
        String answer = scanner.nextLine().trim();
        
        if (!answer.equalsIgnoreCase("n")) {
            try {
                int index = Integer.parseInt(answer) - 1;
                if (index >= 0 && index < genrePlaylists.size()) {
                    AutoPlaylist playlist = genrePlaylists.get(index);
                    if (playlist != null && playlist.getPlaylistSize() >= 10) {
                        displayAutoPlaylist(playlist);
                    } else {
                        System.out.println("This genre doesn't have enough songs for a playlist (minimum 10 required).");
                    }
                } else {
                    System.out.println("Invalid playlist number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }
    }
    
    private void shufflePlaylist() {
        ArrayList<String> playlists = model.getAllPlaylists();
        if (playlists == null || playlists.isEmpty()) {
            System.out.println("No playlists to shuffle.");
            return;
        }
        
        System.out.println("\nSelect playlist to shuffle:");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i+1) + ". " + playlists.get(i));
        }
        System.out.print("> ");
        
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index >= 0 && index < playlists.size()) {
                String name = playlists.get(index);
                ArrayList<Song> shuffledSongs = model.getShuffledPlaylist(name);
                
                if (shuffledSongs != null && !shuffledSongs.isEmpty()) {
                    System.out.println("\nShuffled playlist '" + name + "':");
                    for (int i = 0; i < shuffledSongs.size(); i++) {
                        Song song = shuffledSongs.get(i);
                        if (song != null) {
                            System.out.println((i+1) + ". " + song.getSongTitle() + " - " + song.getArtistName());
                        }
                    }
                } else {
                    System.out.println("Playlist is empty or not found.");
                }
            } else {
                System.out.println("Invalid playlist number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }
    
    private void rateAndFavorite() {
        ArrayList<String> songTitles = model.getAllSongTitles();
        if (songTitles == null || songTitles.isEmpty()) {
            System.out.println("No songs in library to rate.");
            return;
        }
        
        System.out.println("\n--- RATE & FAVORITE ---");
        System.out.println("Select song:");
        for (int i = 0; i < songTitles.size(); i++) {
            System.out.println((i+1) + ". " + songTitles.get(i));
        }
        System.out.print("> ");
        
        try {
            int songIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (songIndex >= 0 && songIndex < songTitles.size()) {
                String songTitle = songTitles.get(songIndex);
                ArrayList<Song> songs = model.searchSongByTitle(songTitle);
                
                if (songs == null || songs.isEmpty()) {
                    System.out.println("Song not found.");
                } else {
                    Song song = songs.get(0);
                    if (song != null) {
                        System.out.println("\n1. Rate song");
                        System.out.println("2. Toggle favorite");
                        System.out.print("> ");
                        
                        String choice = scanner.nextLine().trim();
                        
                        if (choice.equals("1")) {
                            System.out.print("Rating (1-5): ");
                            try {
                                int rating = Integer.parseInt(scanner.nextLine().trim());
                                if (rating >= 1 && rating <= 5) {
                                    model.rateSong(song.getSongTitle(), song.getArtistName(), rating);
                                    System.out.println("Song rated " + rating + "/5!");
                                    if (rating == 5) {
                                        System.out.println("(Added to favorites)");
                                    }
                                } else {
                                    System.out.println("Rating must be 1-5.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid rating.");
                            }
                        } else if (choice.equals("2")) {
                            boolean newState = !model.inFavourites(song);
                            model.markFavorite(song.getSongTitle(), song.getArtistName(), newState);
                            if (newState) {
                                System.out.println("Added to favorites!");
                            } else {
                                System.out.println("Removed from favorites.");
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }
    
    private void playMusic() {
        ArrayList<String> songTitles = model.getAllSongTitles();
        if (songTitles == null || songTitles.isEmpty()) {
            System.out.println("No songs in library to play.");
            return;
        }
        
        System.out.println("\n--- PLAY MUSIC ---");
        System.out.println("1. Play Song");
        System.out.println("2. Play Playlist");
        System.out.println("3. Play Shuffled Library");
        System.out.println("0. Back");
        System.out.print("> ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1": // Play Song
                playSong();
                break;
            case "2": // Play Playlist
                playPlaylist();
                break;
            case "3": // Play Shuffled Library
                playShuffledLibrary();
                break;
        }
    }
    
    private void playSong() {
        ArrayList<String> songTitles = model.getAllSongTitles();
        if (songTitles == null || songTitles.isEmpty()) {
            System.out.println("No songs in library to play.");
            return;
        }
        
        System.out.println("\nSelect song to play:");
        for (int i = 0; i < songTitles.size(); i++) {
            System.out.println((i+1) + ". " + songTitles.get(i));
        }
        System.out.print("> ");
        
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index >= 0 && index < songTitles.size()) {
                String title = songTitles.get(index);
                ArrayList<Song> songs = model.searchSongByTitle(title);
                
                if (songs != null && !songs.isEmpty()) {
                    Song song = songs.get(0);
                    if (song != null) {
                        model.playSong(song.getSongTitle(), song.getArtistName());
                        int plays = model.getPlays(song.getSongTitle(), song.getArtistName());
                        
                        System.out.println("\nNow playing: " + song.getSongTitle() + " - " + song.getArtistName());
                        System.out.println("From album: " + song.getAlbumTitle());
                        System.out.println("Play count: " + plays);
                    }
                }
            } else {
                System.out.println("Invalid song number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }
    
    private void playPlaylist() {
        ArrayList<String> playlists = model.getAllPlaylists();
        if (playlists == null || playlists.isEmpty()) {
            System.out.println("No playlists available.");
            return;
        }
        
        System.out.println("\nSelect playlist to play:");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i+1) + ". " + playlists.get(i));
        }
        System.out.print("> ");
        
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index >= 0 && index < playlists.size()) {
                String name = playlists.get(index);
                Playlist playlist = model.searchPlaylistByName(name);
                
                if (playlist != null) {
                    ArrayList<Song> songs = playlist.getSongs();
                    if (songs == null || songs.isEmpty()) {
                        System.out.println("Playlist is empty.");
                    } else {
                        System.out.println("\nPlaying playlist '" + name + "':");
                        for (int i = 0; i < songs.size(); i++) {
                            Song song = songs.get(i);
                            if (song != null) {
                                System.out.println((i+1) + ". " + song.getSongTitle() + " - " + song.getArtistName());
                                // Simulate playing the song
                                model.playSong(song.getSongTitle(), song.getArtistName());
                            }
                        }
                    }
                }
            } else {
                System.out.println("Invalid playlist number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }
    
    private void playShuffledLibrary() {
        ArrayList<Song> shuffledSongs = model.getShuffledSongs();
        if (shuffledSongs == null || shuffledSongs.isEmpty()) {
            System.out.println("No songs in library to play.");
            return;
        }
        
        System.out.println("\nPlaying shuffled library:");
        for (int i = 0; i < shuffledSongs.size(); i++) {
            Song song = shuffledSongs.get(i);
            if (song != null) {
                System.out.println((i+1) + ". " + song.getSongTitle() + " - " + song.getArtistName());
                // Simulate playing the song
                model.playSong(song.getSongTitle(), song.getArtistName());
            }
        }
    }
    
    // Main method for testing
    public static void main(String[] args) {
        MusicStore store = new MusicStore();
        View view = new View(store);
        
        // Start application
        view.start();
    }
}