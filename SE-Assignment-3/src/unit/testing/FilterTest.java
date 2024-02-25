package unit.testing;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import development.Category;
import development.Filter;

public class FilterTest extends MasterTest {
	
	@Test 
	public void shouldFailCreatingFilterUsingNullCategory(){	
		Filter filter = new  Filter();
		Runnable setCategoryRunnable = ()->{filter.setCategory(null);};
		assertTrue(
				isExceptionThrown(setCategoryRunnable, new NullPointerException()));
	}
	
	@Test 
	public void shouldFailCreatingFilterThatIsNotInPossibleValues(){
		
		Filter filter = produceFilter();

		Runnable setValueRunnable = ()->{filter.setValue("Bar");};
		assertTrue(
				isExceptionThrown(setValueRunnable, new IllegalArgumentException()));
			
	}
	
	@Test
	public void shouldSucceedCreatingFilterThatIsInPossibleValues(){
		Filter filter = produceFilter();
		Runnable setValueRunnable = ()->{filter.setValue("Foo1");};
		assertFalse(
				isExceptionThrown(setValueRunnable, new IllegalArgumentException()));
	}

	private Filter produceFilter() {
		Filter filter = new Filter();
		filter.setCategory(produceFooCategory());
		return filter;
	}
	
	private Category produceFooCategory() {
		Category fooCategory = new Category("Foos"); 
		fooCategory.getPossibleValues().add("Foo1");
		fooCategory.getPossibleValues().add("Foo2");
		fooCategory.getPossibleValues().add("Foo");
		fooCategory.getPossibleValues().add("Foo4");
		return fooCategory;
	}
	
}

	
	



