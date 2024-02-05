package ac.sapir.job_matching_app_test_demo;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class AppStub implements App
{
	private App realApp = null;
	
	//----Mutators & Accessors-----
	public void setRealApp(App realApp) {
		this.realApp = realApp;
	}
	
	public boolean userExists(String userName) {
		return true;
	}
	
	public boolean adExists(int adSerialNumber) {
		return true;
	}
	
	private boolean realAppExists()
	{
		return realApp != null;
	}
	
	//----Operations----
	
	@Override
	public User createPublisher(String userName) {
		
		if(realAppExists())
		{
			return realApp.createPublisher(userName);
		}
		
		
		User newUser = new User();
		if(userExists(userName))
		{
			return null;
		}
		
		newUser.setUserName(userName);
		newUser.setPassword("password");
		
		return newUser;
	}

	@Override
	public void deletePublisher(String userName) {
		if(realAppExists())
		{
			realApp.deletePublisher(userName);
		}		
	}

	@Override
	public Job createJob() {
		
		if(realAppExists())
		{
			return realApp.createJob();
		}
		
		return new Job (produceAdSerialNumber());
	}

	@Override
	public boolean publishJob(Job jobToPublish, User publisher){
		if(realAppExists())
		{
			return realApp.publishJob(jobToPublish, publisher);
		}
		
		String title = jobToPublish.getTitle();
		return title != null && !title.isBlank();
	}

	@Override
	public void removeJob(int jobNunmber) {
		if(realAppExists())
		{
			realApp.removeJob(jobNunmber);
		}
	}

	@Override
	public Job[] searchJob(HashMap<String,Object> parameters) {
		
		if(realAppExists())
		{
			return realApp.searchJob(parameters);
		}
		
		List<Job> resultedJobs = new ArrayList<Job>(3);
		Job job1 = new Job(produceAdSerialNumber());
		Job job2 = new Job(produceAdSerialNumber());
		Job job3 = new Job(produceAdSerialNumber());
		
		job1.setTitle("Cook");
		job2.setTitle("Waiter");
		job3.setTitle("Cashier");
		
		job1.setLocation("Tel-Aviv");
		job2.setLocation("Jerusalem");
		job3.setLocation("Sderot");
		
		job1.setSalary(4000);
		job2.setSalary(2000);
		job3.setSalary(3500);
		
		return new Job[]{ job1, job2, job3};		
	}
	
	//----Helpers----
	private int produceAdSerialNumber()
	{
		//Should produce a unique serial number
		return 1;
	}
}
