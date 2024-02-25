package development;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Category{
	
	private String name; 
	private List<String> possibleValues = new LinkedList<String>();
	
	public Category(String name)
	{
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getPossibleValues() {
		return possibleValues;
	}
	
	public void setPossibleValuest(List<String> possibleValues) {
		this.possibleValues = possibleValues;
	}
	
	
}
