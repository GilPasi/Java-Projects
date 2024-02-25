package development;

public class Filter {
	
	private String value;
	private Category category;
	
	public Filter (){}
	
	public Filter (Category category)
	{
		this.category = category;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		if(category.getPossibleValues().contains(value))
			this.value = value;
		else throw new IllegalArgumentException(
				"The value %s does not exists in the %s category"
				.formatted(value, category.getName()));	
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		if(category == null)
		{
			throw new NullPointerException("Could not use null as a category");
		}
		
		this.category = category;
	}
}
