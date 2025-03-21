package Users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import LA1.Model.LibraryModel;

public class UserSecurity {
	private String username;
    private String passwordHash;
    private String salt;
    private LibraryModel library;

    
    // new user
    public UserSecurity(String username, String password) throws NoSuchAlgorithmException {
        this.username = username;
        this.salt = generateSalt();
        this.passwordHash = hashPassword(password, salt);
  
    }
    
 
	//  existing user
    public UserSecurity(String username, String salt, String passwordHash) {
        this.username = username;
        this.salt = salt;
        this.passwordHash = passwordHash;
     
    } 
    
    
    public String getUsername() {
        return username;
    }
    
    public String getSalt() {
        return salt;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public LibraryModel getLibrary() {
        return library;
    }
    
    public void setLibrary(LibraryModel library) {
        this.library = library;
    }
    

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
    
    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
	

    public boolean verifyPassword(String password) {
        try {
            String hashedPassword = hashPassword(password, this.salt);
            return hashedPassword.equals(this.passwordHash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error:" + e.getMessage());
            return false;
        }
    }

}
