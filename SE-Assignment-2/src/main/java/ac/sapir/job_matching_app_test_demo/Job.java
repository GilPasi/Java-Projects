package ac.sapir.job_matching_app_test_demo;

public class Job {
	private String title;
	private String location;
	private float salary;
	private final int jobNumber; //Read Only
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}
	
	public Job(int jobNumber)
	{
		this.jobNumber = jobNumber;
	}
		
	public int getJobNumber()
	{
		return jobNumber;
	}
	
	public String getTitle() {
		return title;
	}

	public String getLocation() {
		return location;
	}

	public float getSalary() {
		return salary;
	}

}
