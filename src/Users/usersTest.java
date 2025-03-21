package Users;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

import LA1.MusicStore;

class usersTest {
	
	

	@Test
	void testLogin() throws IOException, NoSuchAlgorithmException {
		
		File usersFile = new File("users.txt");
	    if (usersFile.exists()) {
	        usersFile.delete();
	    }
  
		
		MusicStore store = new MusicStore() { 	
		};
		
		users allUsers = new users(store);
		 
		assertTrue(allUsers.registerUser("Zayyan", "password123"));
		assertFalse(allUsers.registerUser("Zayyan", "password123"));
		
		
		assertTrue(allUsers.login("Zayyan", "password123"));
		assertEquals("Zayyan", allUsers.getCurrentUsername());
        assertNotNull(allUsers.getCurrentLibrary());
		
		allUsers.logout();
		assertFalse(allUsers.login("Zayyan", "password321"));
		
		assertFalse(allUsers.login("Haris", "anypassword"));
		
		allUsers.logout();  
        allUsers.logout(); 
	}
	
	@Test
	void testMultipleUsers() throws IOException, NoSuchAlgorithmException {
		
		File usersFile = new File("users.txt");
        if (usersFile.exists()) {
            usersFile.delete();
        }
		
		MusicStore store = new MusicStore() {};
		users allUsers = new users(store);
		
		// multiple users
		
		assertTrue(allUsers.registerUser("Zayyan", "password1"));
        assertTrue(allUsers.registerUser("Anthony", "password2"));
        assertTrue(allUsers.registerUser("Blake", "password3"));
        
        assertTrue(allUsers.login("Zayyan", "password1"));
        assertEquals("Zayyan", allUsers.getCurrentUsername());
        assertNotNull(allUsers.getCurrentLibrary());
        
        allUsers.logout();
         
        assertTrue(allUsers.login("Anthony", "password2"));
        assertEquals("Anthony", allUsers.getCurrentUsername());
        
        allUsers.logout();
        
        MusicStore store2 = new MusicStore() {};
        users second = new users(store2);
        
        assertTrue(second.login("Blake", "password3"));
        assertEquals("Blake", second.getCurrentUsername());
        
        
        
	}

}
