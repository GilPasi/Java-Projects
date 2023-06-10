/*Authors:
 * Gil Pasi 	- 206500936
 * Heba Abu-Kaf - 323980441
 * */
package driver;
import utils.Ram;
import utils.Validator;

public class Runner {
	
	
	public static void main(String [] args ) {
	
		int ramSize = Validator.getValidPowerOf2("Please enter a power of 2 ");
		Ram ram = new Ram(ramSize);
		int method = Validator.getValidInt(
				"Choose one method of the following:"
				+ "\n1)First Fit"
				+ "\n2)Next Fit"
				+ "\n3)Best Fit"
				,4);
		String methodName = "";
		
		switch(method) {
			case 1:
				methodName="first fit";
				break;
			case 2:
				methodName="next fit";
				break;
			case 3:
				methodName="best fit";
				break;
		}
		
		
		System.out.println("====Start a simulation with " + ramSize 
				+ " bytes for RAM using the " + methodName + " method====");
		
		int choice = 0;
		do {
			choice = Validator.getValidInt(
					"1. Enter process\r\n"
							+ "2. Exit process\r\n"
							+ "3. Print status\r\n"
							+ "4. Exit\r\n"
							+ "Enter your choice:\r\n" , 5
					);	
			
			switch(choice) {
			case 1:
				int processId = Validator.getValidInt("Enter the process id: ");
				if(processId == 0) {
					System.out.println("This id is not valid ");
					continue;
				}
				int processSize = Validator.getValidInt("Enter the process size: ");
				
				
				boolean sucessfulAllocation = false;
					
				if(method == 1)
					sucessfulAllocation = ram.allocateFirstFit(processId, processSize);
				if(method == 2)
					sucessfulAllocation = ram.allocateNextFit(processId, processSize);
				if(method == 3)
					sucessfulAllocation = ram.allocateBestFit(processId, processSize);

				
				//If the allocation was unsuccessful , print the fragmentation.
				if(!sucessfulAllocation)
					System.out.println("The fragmentation is :" + ram.calcFragmentation());
				
				break;
				
				
			case 2 :
				int process = Validator.getValidInt("Enter the process number which you want to free");
				ram.freeProcess(process);
				break;
			case 3: 
				ram.printStatus();
				break;
			default:
				System.out.println("No valid option was entered");
				continue;
				
			}
		System.out.println(ram);	
		}
		while(choice != 4);
		
	}
}




