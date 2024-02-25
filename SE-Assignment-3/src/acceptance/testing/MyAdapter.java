package acceptance.testing;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.LinkedList;

import development.Ad;
import development.MyImplementation;
import development.User;

public class MyAdapter implements DrushimBridge {
	
	MyImplementation imp = new MyImplementation();
	
	@Override
	public void addArea(String string) {
		throw new UnsupportedOperationException();
		// TODO Auto-generated method stub
	}

	@Override
	public String addCompany(String name, String user, String pass, int numOfAds) {
		throw new UnsupportedOperationException();
		// TODO Auto-generated method stub
	}

	@Override
	public void addDomain(String string) {
		throw new UnsupportedOperationException();
		// TODO Auto-generated method stub
	}

	@Override
	public String addNewAd(String user, String pass, String domain, String role, String desc, String area) {
		Ad newAd = null;
		
		try{	
			User publisher = User.getUserByName(user);
			if(!(publisher.getPassword().equals(pass))) return null;
			newAd = new Ad(role, domain, publisher);
			newAd.setFreeTextFilter(desc);
			imp.publishAd(newAd);
		}
		catch (IllegalArgumentException|KeyException ex ) {
			return null;
		}
		
		return newAd.toString();
	}

	@Override
	public void addRole(String string) {
		throw new UnsupportedOperationException();
		// TODO Auto-generated method stub
	}

	@Override
	public boolean closeAd(String user, String pass, String id) {
		throw new UnsupportedOperationException();
		// TODO Auto-generated method stub
	}

	@Override
	public String[] getAd(String id) {
		throw new UnsupportedOperationException();
		// TODO Auto-generated method stub
	}

	@Override
	public String[] getAds(String domain, String role, String area) {
		LinkedList<Ad> filteredAds = imp.searchAd(domain, role, area);
		ArrayList<String> adsIds = new ArrayList<>(filteredAds.size());
		for(Ad ad: filteredAds) {
			adsIds.add(ad.toString());
		}
		return (String[]) adsIds.toArray() ;
	}

	@Override
	public String getCompanyId(String name) {
		throw new UnsupportedOperationException();
		// TODO Auto-generated method stub
	}

	@Override
	public String[] getMyAdsIds(String user, String pass) {
		throw new UnsupportedOperationException();
		// TODO Auto-generated method stub
	}

	@Override
	public int getMyCredit(String user, String pass) {
		throw new UnsupportedOperationException();
		// TODO Auto-generated method stub
	}

}
