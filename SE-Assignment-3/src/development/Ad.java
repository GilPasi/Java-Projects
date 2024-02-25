package development;
import java.util.LinkedList;
import java.util.List;

public class Ad implements Comparable<Ad>{

	private static Category areas = new Category("area");
	private static Category titles = new Category("titles");
	private static Category scopes = new Category("scopes");
	private static Category educationLevels = new Category("education");
	private static Category jobTypes = new Category("job-types");
	private static List<Filter> fields = new LinkedList<Filter>();
	private Filter area;
	private Filter title;
	private Filter scope;
	private Filter jobType;
	private Filter education;
	private String freeTextFilter;
	private int salaryFilter;
	private User publisher ;
	private int id ;
	private static int idGenerator=1;
		
	public Ad(String titleValue, String jobTypeValue, User user) {
		
		initializeFilters();

		title.setValue(titleValue);
		jobType.setValue(jobTypeValue);
		
		publisher = user;
		this.id = idGenerator++;
	}
	
	private void initializeFilters()
	{
		area = new Filter();
		area.setCategory(areas);
		title = new Filter();
		title.setCategory(titles);
		scope = new Filter();
		scope.setCategory(scopes);
		jobType = new Filter();
		jobType.setCategory(jobTypes);
	}
	
	public int getAdID() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return  ""+this.id ;
	}
	
	public static Category getAreas() {
		return areas;
	}

	public static Category getTitles() {
		return titles;
	}

	public static Category getScopes() {
		return scopes;
	}

	public static Category getEducationLevels() {
		return educationLevels;
	}

	public static Category getJobTypes() {
		return jobTypes;
	}
	
	public Filter getEducation() {
		return education;
	}

	public Filter getArea() {
		return area;
	}

	public String getTitle() {
		return title.getValue();
	}

	public Filter getScope() {
		return scope;
	}

	public Filter getJobType() {
		return jobType;
	}

	public void setJobType(Filter jobType) {
		this.jobType = jobType;
	}

	public String getFreeTextFilter() {
		return freeTextFilter;
	}

	public void setFreeTextFilter(String freeTextFilter) {
		this.freeTextFilter = freeTextFilter;
	}

	public int getSalaryFilter() {
		return salaryFilter;
	}

	public void setSalaryFilter(int salaryFilter) {
		this.salaryFilter = salaryFilter;
	}

	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}

	@Override
	public int compareTo(Ad other) {
		return salaryFilter - other.getSalaryFilter();
	}
		
	
	
}
