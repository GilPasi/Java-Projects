package ac.sapir.job_matching_app_test_demo;

public class MasterTest {
	
	protected User mockPublisher;
	protected Job mockJob;
	protected App app = new AppStub();
	
	
	//Adapting the app's behavior to the test's setup
    public User forceCreateUser(String baseName)
    {
    	User forcedUser = null;
    	
    	do 
    	{
    		forcedUser = app.createPublisher(baseName);
    		baseName += "a";
    	}
    	while(forcedUser != null);
    	
    	return forcedUser;
    }
    
}
