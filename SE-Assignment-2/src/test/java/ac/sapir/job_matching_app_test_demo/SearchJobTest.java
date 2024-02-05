package ac.sapir.job_matching_app_test_demo;
import static org.junit.Assert.assertTrue;

import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SearchJobTest extends MasterTest
{	
    @Before
    private void promoteState(){
    	mockPublisher = forceCreateUser("NirGa");
    	mockJob = app.createJob();
    	mockJob.setLocation("Metula");
    	mockJob.setSalary(7000);
    	mockJob.setTitle("Chef");
    	app.publishJob(mockJob, mockPublisher);
    }
    
    @Test
    public void testSearch() {
    	HashMap<String, Object> searchArgs = new HashMap();
    	searchArgs.put("Title", "House Cleaner");
    	searchArgs.put("Location", "Metula");
    	searchArgs.put("Salary", 700000);
    	
    	Job[] searchResult = app.searchJob(searchArgs);
    	assertFalse("This job does match some results while it should not",
    			searchResult.length > 0);
    	
    	searchArgs.clear();
    	searchArgs.put("Title", "Chef");
    	searchArgs.put("Location", "Jerusalem");
    	searchArgs.put("Salary", 7000);
    	
        assertTrue("This job does not match any results while it should",
        		searchResult.length > 0);
	}
        
    @After
    private void demoteState() {
        app.deletePublisher(mockPublisher.getUserName());
        app.removeJob(mockJob.getJobNumber()); 
    }
}

