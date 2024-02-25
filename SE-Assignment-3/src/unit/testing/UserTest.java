package unit.testing;

import static org.junit.Assert.assertTrue;

import javax.management.openmbean.KeyAlreadyExistsException;

import org.junit.Test;

import development.User;

public class UserTest {
	
	@Test
	public void shouldFailCreatingNoneUniqueUser()
	{
		try {
			User u1 = new User("shmoolik", "kipod");
			User u2 = new User("shmoolik", "kipod");
			
		} catch (KeyAlreadyExistsException e) {
			assertTrue(true);
			return;
		}

		assertTrue(false);
	}
}
