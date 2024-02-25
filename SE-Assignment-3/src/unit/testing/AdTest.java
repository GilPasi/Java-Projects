package unit.testing;
import static org.junit.Assert.assertTrue;
import javax.management.openmbean.KeyAlreadyExistsException;
import org.junit.Test;
import development.Ad;
import development.User;

public class AdTest extends MasterTest{
	
	private static User exampleValidUser = produceValidUser(); 
	private static final String VALID_TITLE = "Test Title";
	private static final String VALID_JOB_TYPE = "Test Job Type";
	private static final String INVALID_FIELD = "Blah";

	static 
	{
		Ad.getTitles().getPossibleValues().add(VALID_TITLE);
		Ad.getJobTypes().getPossibleValues().add(VALID_JOB_TYPE);
	}
	
	@Test
	public void shouldDefaultCategoriesBeInitialized()
	{
		String areasAsString = Ad.getAreas().toString();
		String expectedString = "[\"Tel-Aviv\", \"Jerusalem\", \"Beer-Shava\", \"Raanana\"]";
		assertTrue(areasAsString.equals(areasAsString));

	}
	
	@Test
	public void shouldNotAllowValuesOutOfAllPossiabilites(){		
		shouldFailCreatingAd(INVALID_FIELD,VALID_JOB_TYPE,exampleValidUser);
		shouldFailCreatingAd(VALID_TITLE,INVALID_FIELD,exampleValidUser);
		shouldFailCreatingAd(INVALID_FIELD,INVALID_FIELD,exampleValidUser);
	}
	
	@Test
	public void shouldIdbeUnique(){
		Ad ad1 = produceValidAd();
		Ad ad2 = produceValidAd();
		Ad ad3 = produceValidAd();

		int id1 = ad1.getAdID();
		int id2 = ad2.getAdID();
		int id3 = ad3.getAdID();

		assertTrue(id1 != id2 && id1 != id3 && id2 != id3);
	}
	
	private void shouldFailCreatingAd(String title, String jobType, User publisher) {	
		Runnable adCtorRunnable = ()->{Ad ad = new Ad(title, jobType, publisher);};
		assertTrue(isExceptionThrown(adCtorRunnable, new IllegalArgumentException()));
	}
	
	private static User produceValidUser(){
		User validUser = null;
		String userName = "user";
		boolean isValidUser = false;
		while (!isValidUser)
		{
			try {
				validUser = new User(userName, "password");
				isValidUser = true;
			} catch (KeyAlreadyExistsException e){
				e.printStackTrace();
				userName += "1" ;
			}
		}

		return validUser;
	}
	
	private Ad produceValidAd(){	
		return new Ad(VALID_TITLE , VALID_JOB_TYPE, exampleValidUser);
	}
	
}
