package development;
import java.security.KeyException;
import java.util.LinkedList;
import java.util.Objects;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.print.attribute.standard.JobHoldUntil;

public class User {
	private static LinkedList<User> users = new LinkedList();
	private String userName ;
	private String password;
	
	public User(String userName,String password) throws KeyAlreadyExistsException {
		if(userAlreadyExists(userName))
		{
			throw new KeyAlreadyExistsException(
				"The user %s is already exists".formatted(userName) );
		}
		
		this.userName = userName;
		this.password = password;
		users.add(this);

	}
	
	private boolean userAlreadyExists(String userName) {
		try {
			getUserByName(userName);
			return true;
		} catch (KeyException ex){
			return false;
		}
	}
	
	public static LinkedList<User> getUsers() {
		return users;
	}

	public void setPass (String password) {
		this.password= password;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(userName, other.userName);
	}

	
	@Override
	public String toString() {
		return "User [userName=" + userName + "]";
	}
	

	public static User getUserByName(String user) throws KeyException {
		for(User user1 : users) {
			if(user1.getUserName().equals(user)) {
				return user1;
			}	 	
		}
			throw new KeyException("the user %s not found".formatted(user));
	}
}

