package ac.sapir.job_matching_app_test_demo;

import java.sql.Date;

public class User {
	
	private String userName;
	private String password;
	private Date endDate;
	private int leftAdds;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getLeftAds() {
		return leftAdds;
	}
	public void setLeftAds(int leftAdds) {
		this.leftAdds = leftAdds;
	}
	
	

}
