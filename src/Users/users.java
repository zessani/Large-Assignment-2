package Users;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import LA1.MusicStore;
import LA1.Model.LibraryModel;

public class users {
	
	 private ArrayList<UserSecurity> users;
	 private UserSecurity currentUser;
	 private MusicStore store;
	 private static final String USERS_FILE = "users.txt";
	 private HashMap<String, LibraryModel> userLibraries;


	public users(MusicStore store) throws IOException {
	    this.users = new ArrayList<>();
	    this.store = store;
	    this.userLibraries = new HashMap<>(); 
      
	    loadUsers();
	    
	}
	
	public LibraryModel getCurrentLibrary() {
        return currentUser.getLibrary();
    }
    
    public String getCurrentUsername() {
        return currentUser.getUsername();
    }
	
	public boolean registerUser(String username, String password) throws NoSuchAlgorithmException {
        // check if user exists
        for (UserSecurity user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        // create new user
        UserSecurity newUser = new UserSecurity(username, password);
        LibraryModel newLibrary = new LibraryModel(store);
        
        newUser.setLibrary(newLibrary);
        users.add(newUser);
        userLibraries.put(username, newLibrary);
        
        saveUsers();
        return true;  
    }
	
	public boolean login(String username, String password) {
        for (UserSecurity user : users) {
            if (user.getUsername().equals(username)) {
                if (user.verifyPassword(password)) {
                    currentUser = user;
                    
                    LibraryModel library = userLibraries.get(username);
                    if (library == null) {
                        library = new LibraryModel(store);
                        userLibraries.put(username, library);
                    }
                    currentUser.setLibrary(library);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
	  
	public void logout() {
        if (currentUser != null) {
        	userLibraries.put(currentUser.getUsername(), currentUser.getLibrary());
            currentUser = null;
        }
    }
	
	private void saveUsers() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE));
            
            for (UserSecurity user : users) {
                // Format: username,salt,passwordHash 
                writer.println(user.getUsername() + "," + user.getSalt() + "," + user.getPasswordHash());
            }
            
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    
	}

	private void loadUsers() {
        File file = new File(USERS_FILE);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    // Format: username,salt,hashedPassword 
                	UserSecurity user = new UserSecurity(parts[0], parts[1], parts[2]);
                    
                    // Create library and add to HashMap
                    LibraryModel library = new LibraryModel(store);
                    user.setLibrary(library);
                    userLibraries.put(parts[0], library);  
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("user cannot be found...");
        }
    }
	
	
}