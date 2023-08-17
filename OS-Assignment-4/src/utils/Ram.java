/*Authors:
 * Gil Pasi 	- 206500936
 * Heba Abu-Kaf - 323980441
 * */

package utils;

public class Ram {
	
	final static int FREE = 0;
	private int [] memoryTree; //The leaves layer is the actual memory
	private int ramSize;
	private int treeSize;
	private int ptr ;
	
	/**The RAM is based on a tree data structure.
	 *  It is implemented with a vector where all the leaves are the actual RAM bytes
	 *  and the rest of the nodes are representing the meta-data about them. 
	 *  The meta-data helps to determine if an allocation is possible.
	 *  According to the buddy-system algorithm allocation is only 
	 *  possible when there is one root.
	 *  There finding two roots for the same process after allocating means that
	 *  the allocation was unsuccessful.
	 *  */
	
	
	//---Constructors--
	public Ram (int ramSize) {
		if (!Validator.isPowerOf2(ramSize))
			throw new IllegalArgumentException("RAM size must be a power of 2");
		this.ramSize = ramSize;
		ptr =  ramSize - 1;
		treeSize = 2*ramSize - 1;
		memoryTree = new int [treeSize];
	}
	
	

	//---Algorithms---
	public boolean allocateFirstFit (int id , int size) {
		//First fit is essentially just a next fit when the pointer
		//goes back to the first index on each iteration.
		ptr = ramSize ;
		return allocateNextFit(id, size);
	}
	
	
	public boolean allocateNextFit (int id , int size) {
		final int ORIGINAL_PTR = ptr;
		final int BLOCK_SIZE = calcBlockSize(size);
		int allocationStart = ptr; 
		
		//Try to allocate 
		do {

			//a block was found
			if(ptr == allocationStart + BLOCK_SIZE) {
				allocate(allocationStart, ptr, id);
				eleminateNodesWithOneChild();
				
				if(rootsCount(id) == 1 ) //All allocations must have only one root
					return true;
				else {
					deallocate(allocationStart, ptr);
					ptr = ++allocationStart;
				}	
			}
			
			//Restart the circle only after the allocation stage
			if(ptr == treeSize)
				ptr = treeSize - ramSize;
					
				
			if (memoryTree[ptr] != FREE || ptr == FREE) 
				allocationStart = calcNextCell(ptr);
			
			ptr++;
		}while(ptr != ORIGINAL_PTR);

		return false;		
	}
	
	public boolean allocateBestFit(int id , int size ) {
		final int BLOCK_SIZE = calcBlockSize(size);		
		eleminateNodesWithOneChild();
		
		for(int i = treeSize - 1 ; i > 0 ; i--) 
			if(memoryTree[i] != FREE)
				memoryTree[parentPosition(i)] = memoryTree[i];
					

		//Calculate the maximal index where this block might be
		int degree =  (int )(log2(treeSize) - log2(BLOCK_SIZE));
		int maxIndex = (int)( Math.pow(2, degree + 1) - 2);

		int start ;
		
	

		//Look for a free place from the minimal to maximal
		for(int i = maxIndex; i > 0 ; i--) 
			if(memoryTree[i] == FREE && memoryTree[siblingPosition(i)] != FREE) {
				start = indexToRange(i)[0];
				allocate(start, start + BLOCK_SIZE, id);
				return true;
			}
		
		/*If we reached this stage, there are tree possible scenarios
		 * 1. All nodes are taken , meaning there is no free place - return null.
		 * 2. Some of the nodes have no brothers and the rest are not available.
		 * 	  In this case just allocate the smallest node.
		 * 3.All nodes have no siblings , the solution is same as the one for case 2.
		 * */
		
		for(int i = maxIndex ; i > 0 ; i-- )
			if(memoryTree[i] == FREE) {
				start = indexToRange(i)[0];
				allocate(start, start + BLOCK_SIZE, id);
				return true;
			}

		return false;

	}
	

	
	//---RAM actions---
	private void deallocate (int start , int end) {
		allocate(start, end, FREE);
	}
	
	public void allocate(int start , int end, int id ) {
		
		//First allocate the memory

		
		for(int i = start ; i < end; i++ ) 
			memoryTree[i] = id;
		
		//Second mark all parents as well
		for(int i = treeSize - 1; i > 0 ; i--) {
			memoryTree[parentPosition(i)] = memoryTree[i]; // Any parent is in the Arr[(i-1)/2] position
			
		}//Important to scan in reverse
		
	}
	
	public int calcFragmentation () {
		int fragmantationSize = 0;
		for(int i = ramSize - 1 ; i < treeSize ; i++)
			if(memoryTree [i] == FREE )
				fragmantationSize++;
		return fragmantationSize;
	}
	
	public void freeProcess(int id) {
		for(int i = ramSize - 1 ; i < treeSize ; i++ )
			if(memoryTree[i] == id)
				memoryTree [i] = FREE;
	}
	
	public void printStatus () {
		
		System.out.println(this);
		
		int currentProcess = memoryTree[ramSize - 1] , currentStart = ramSize - 1 , currentEnd = ramSize ;
		
		for(int i = ramSize; i < treeSize ; i++) {
			currentEnd ++;
			
			if(memoryTree[i] != currentProcess) {
				String prefix = currentProcess == FREE ? "Free space" : "Process " + currentProcess;
					System.out.println(prefix  + ": "
										+ (currentStart - ramSize + 1) + "-" + (currentEnd - ramSize));
				
				currentProcess = memoryTree[i];
				currentStart = i;
				currentEnd = i + 1;
			}
			
			if(i == treeSize-1 && currentProcess != FREE)
				System.out.println("Process " + currentProcess + ": " + currentStart + "-" + currentEnd );
		}
		
		
		System.out.println("\nFragmantation is: " + calcFragmentation());
	}
	
	
	//---Assisting methods----
	
	private int [] indexToRange(int index) {
		index ++ ; 
		int DEGREE =  (int) log2(ramSize)-(int )(log2(index));
		final int BLOCK_SIZE = (int)Math.pow(2, DEGREE);
		final int START = index * BLOCK_SIZE - 1 , END = START + BLOCK_SIZE;
		return new int [] {START, END};
		
	}
	
	private int calcNextCell (int current) {
		int ret = current + 1;
		if(ret >= treeSize)
			ret = treeSize - ramSize;
		return ret;
		}
	
	private int calcBlockSize(int proccessSize ) {
		
		//Both 1 and 2 are valid sizes
		if(proccessSize < 3 )return proccessSize;
		
		int low = 2 , high = 4;
		final int BUDDY_FACTOR = 2 ; 
		
		while (!(low <= proccessSize && proccessSize <= high ) ) {
			low  *= BUDDY_FACTOR ;
			high *= BUDDY_FACTOR ;
		}
		
		return high;	
	}
	
	
	private void eleminateNodesWithOneChild () {
		for (int i = 0 ; i < treeSize / 2 ; i++) {//Do not scan the leaves
			int child1 = memoryTree[2*i + 1];
			int child2 = memoryTree[2*i + 2];
			

			if(child2 >= treeSize || child1 >= treeSize)return;

			if(child1 - child2 != 0) // Meaning: has two children or no children at all
				memoryTree[i] = FREE;//== Delete this node
		}
	}
	
	
	private int rootsCount  (int id) {
		int counter = 0;
		for (int i = 0 ; i < treeSize / 2 ; i++) {//Do not scan the leaves
			int parent = memoryTree[parentPosition(i)];
			if(memoryTree[i]==id && parent != id)//A root is found on the i location
				counter++;
		}
		
		//Also count the orphan leaves
		for(int i = treeSize / 2; i < treeSize ; i++)
			if(memoryTree[i] == id && memoryTree[parentPosition(i)] != id )
				counter++;
		
		return counter;
	}
	
	private double log2(double base) {return Math.log(base) / Math.log(2);}

	
	
	
	//---Debugging methods----
	public void printTree () {	
		int rowCount = 1;
		int current = 0;

		while (current < treeSize) {
		    int rowElements = (int) Math.pow(2, rowCount - 1);
		    for (int i = 0; i < rowElements && current < treeSize; i++) {
		        System.out.print(memoryTree[current++]);
		        if (current < treeSize) {
		            System.out.print("-");
		        }
		    }
		    System.out.println();
		    rowCount++;
		}
		
	}
	
	private int parentPosition(int i) {return ((i - 1) / 2);}
	
	private int siblingPosition(int i) {
		if(i == 0)
			return -1;
		
		if(i % 2 == 0)return i -1;
		
		return i + 1;

	}
	
	public String toString () {
		String ret = "[";
		for(int i = treeSize - ramSize ; i < treeSize ; i++)
			ret += memoryTree[i] + " ";
		ret += "]\n";
		
		final int MEASURE_UNIT = 4;
		for(int i = 1 ; i < ramSize / MEASURE_UNIT + 1 ; i++)
			ret +="\t" +( i * MEASURE_UNIT );
		return ret;
	}

}
