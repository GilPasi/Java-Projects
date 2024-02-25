package unit.testing;

public class MasterTest {
	protected void name() {
		
	} boolean isExceptionThrown(Runnable functionToTest, Exception exceptionToCatch)
	{
		try {
			functionToTest.run();
		} catch (Exception ex) {
			return ex.getClass() == exceptionToCatch.getClass();
			}
		
		return false;
	}
}
