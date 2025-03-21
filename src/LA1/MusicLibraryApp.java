package LA1;

import LA1.Model.LibraryModel;
import LA1.View.View;

public class MusicLibraryApp {
    public static void main(String[] args) {
        // Create the music store
        MusicStore store = new MusicStore();
        
        // Create the library model
        LibraryModel model = new LibraryModel(store);
         
        // Create the view and start the application
        View view = new View(store);
        view.start();
    }
} 