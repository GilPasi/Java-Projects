package ac.sapir.job_matching_app_test_demo;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PublishAdTest extends MasterTest
{
	@Before
    public void promoteState(){
    	mockPublisher = forceCreateUser("AviRon");
    	mockJob = app.createJob();
	}
	
    @Test
    public void shouldPublishOnlyJobsWithTitles()
    {    	
    	mockJob.setTitle(null);
    	assertFalse(app.publishJob(mockJob, mockPublisher));
    	
    	mockJob.setTitle("");
    	assertFalse(app.publishJob(mockJob, mockPublisher));
    	
    	mockJob.setTitle("Cook");
    	assertTrue(app.publishJob(mockJob, mockPublisher));
    }
    
    @After
    public void demoteState()
    {
    	app.deletePublisher(mockPublisher.getUserName());
    	app.removeJob(mockJob.getJobNumber());
    }
}
