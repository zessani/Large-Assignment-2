package Users;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

class UserSecurityTest {

	@Test
	void testSecurity() throws NoSuchAlgorithmException {
		String username = "testUser";
        String password = "testPassword";
        UserSecurity newUser = new UserSecurity(username, password);
        
        assertEquals(username, newUser.getUsername());
        assertNotNull(newUser.getSalt());
        assertNotNull(newUser.getPasswordHash());
        
        assertTrue(newUser.verifyPassword(password));
        assertFalse(newUser.verifyPassword("wrongPassword"));
        
        String salt = newUser.getSalt();
        String passwordHash = newUser.getPasswordHash();
        UserSecurity existingUser = new UserSecurity(username, salt, passwordHash);
        
        assertEquals(username, existingUser.getUsername());
        assertEquals(salt, existingUser.getSalt());
        assertEquals(passwordHash, existingUser.getPasswordHash());
	}

}
