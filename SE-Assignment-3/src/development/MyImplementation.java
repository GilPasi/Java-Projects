package development;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MyImplementation {
	
	private ArrayList<Ad> publishedAds = new ArrayList();
	
	public MyImplementation()
	{
		Ad.getAreas().setPossibleValuest(
				Arrays.asList(new String[] 
				{"Tel-Aviv", "Jerusalem", "Beer-Shava", "Raanana"}));
		
		Ad.getTitles().setPossibleValuest(
				Arrays.asList(new String[] 
				{"Engineer", "Doctor", "Frontend Developer","Nurse"}));
		
		Ad.getScopes().setPossibleValuest(
				Arrays.asList(new String[] 
				{"Full", "Patial", "Student", "Freelance" ,"Shifts"}));
		
		Ad.getEducationLevels().setPossibleValuest(
				Arrays.asList(new String[] 
				{null,"Undergraduate", "High School", "BSc/BA", "MSc", "PhD"}));
		
		Ad.getJobTypes().setPossibleValuest(
				Arrays.asList(new String[] 
				{null, "Software", "Medical", "Art", "Security", "Psychology"}));
	}

	public boolean publishAd (Ad adToPublish) {
		publishedAds.add(adToPublish);
		return true;
	}

	public LinkedList<Ad> searchAd (String jobType, String title, String area){
		LinkedList<Ad> resultAds = new LinkedList<>(publishedAds);
		if(jobType != null) {
			resultAds= serachAdsByJobType(jobType, resultAds);
		}
		if(title != null) {
			resultAds= serachAdsByTitle(title, resultAds);
		}if(area != null) {
			resultAds= serachAdsByArea(area, resultAds);
		}
		
		sortAdsBySalary(resultAds);
		
		return resultAds;
	}
	
	public void sortAdsBySalary (List<Ad> ads){
		Collections.sort(ads);
	}
	
	private LinkedList<Ad> serachAdsByJobType(String jobType, LinkedList<Ad> listToFilter){
		LinkedList<Ad> resultAds = new LinkedList<>();
		for(Ad ad : listToFilter) {
			if(ad.getJobType().equals(jobType)) {
				resultAds.add(ad);
			}
		}
		return resultAds;	
	}
	
	private LinkedList<Ad> serachAdsByArea(String area,LinkedList<Ad> listToFilter){
		LinkedList<Ad> resultAds = new LinkedList<>();
		for(Ad ad : listToFilter) {
			if(ad.getArea().equals(area)) {
				resultAds.add(ad);
			}
		}
		return resultAds;	
	}
	
	private LinkedList<Ad> serachAdsByTitle(String title,LinkedList<Ad> listToFilter){
		LinkedList<Ad> resultAds = new LinkedList<>();
		for(Ad ad : listToFilter) {
			if(ad.getTitle().equals(title)) {
				resultAds.add(ad);
			}
		}
		return resultAds;	
	}
	

}
