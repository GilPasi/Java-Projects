package ac.sapir.job_matching_app_test_demo;
import java.util.HashMap;

public interface App {
	
	public User createPublisher(String userName);
	public void deletePublisher(String userName);
	public Job createJob();
	public boolean publishJob(Job jobToPublish, User publisher);
	public void removeJob(int jobNunmber);
	public Job[] searchJob(HashMap<String, Object> parameters);
}
