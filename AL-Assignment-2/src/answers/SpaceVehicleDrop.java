package answers;
import java.lang.reflect.Array;
import java.util.*;

public class SpaceVehicleDrop {
	
	
	public static void heightSequence (final int H , final int X , final int G) {/* H = building's height
	 																			    X = eggs count
	 																			    G = maximal survival height
	 																			*/
		final int DEF = 1 ; //1 is the first element in all sequences
		ArrayList<ArrayList<Integer>> heightsTable = new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0 ; i < X; i++) {
			ArrayList<Integer> cur = new ArrayList<Integer>();
			cur.add(DEF);
			heightsTable.add(cur);
		}
		
		ArrayList<Integer> baseRow = heightsTable.get(0);
		int rows = baseRow.size();

		int sum = 0 ;
		
		
		while(sum < H) {
			baseRow.add(DEF);
			
			for(int i = 1; i < heightsTable.size() ; i++) {
				ArrayList<Integer> cur = heightsTable.get(i);
				ArrayList<Integer> prev = heightsTable.get(i - 1);

				cur.add(cur.get(rows - 1) + prev.get(rows));
			}
			sum += heightsTable.get(heightsTable.size() - 1).get(rows);
			rows ++;
		}
		
		System.out.println("Pascal Triangle " + heightsTable + "\n");
		
		//Now backtrack
		
		int lowBound = 0;
		int r = heightsTable.size() - 1 , c = heightsTable.get(0).size() - 1;
		
		System.out.println("The heights sequence is :");

		while (r >= 0) {
			
			int testHeight = lowBound + heightsTable.get(r).get(c);
			
			System.out.println(testHeight);
			
			
			if(testHeight == G)return;
			
			if(testHeight < G) {
				lowBound = Math.min(testHeight , H);//Egg didn't break , lower bound increased
				--c;
			}
			else {
				System.out.println("Egg broke! there are " + r + " eggs left" );
				--r;
				
				c = -1; //Avoid bound problems
				sum = testHeight - lowBound;
				int subSum = 0;
				
				//Find new c 
				while(subSum < sum) {
					c++;
					subSum += heightsTable.get(r).get(c);
				}
			}
			
		}
		
	}
	
	
	
	//Bonus: Find a dynamic programming algorithm that finds the min trials for each eggs quantity.
	public static int[][] leastTries(int x , int h) {//h = height , x = tries
		int [][] table = new int[x][h + 1];
		
		//In java a table is initialized with all cells contain 0 
		//Therefore there is only a need to reset the first line (one try line)
		for(int i = 0 ; i < table[0].length; i++)table[0][i]=i; 

		
		for(int r = 1 ; r < x ; r++) { //r for row 
			for(int c = 1; c < table[0].length; c++) {// c for column
				ArrayList<Integer> options = new ArrayList<>();
				
				for(int i = 0 ; i < c; i ++) 
					options.add(Math.max(table[r][i] , table[r - 1][c - i - 1]));				
				
				table[r][c] = min(options) + 1;
			}
		}
		
		printArray(table);
		return table;
	}
	
	private static void printArray (int [][] array) {
		String delimiterUnit = "______________________________________________________________________________________________________________________________";
		String delimiter = delimiterUnit;
		for(int i = 0 ; i < array.length; i++) {
			delimiter += delimiterUnit;
			for(int j = 0; j < array[0].length ; j ++)
				System.out.print(array [i][j] + "\t");
			
			System.out.println();
		}
		System.out.println(delimiter);
	}
	
	
	public static int min(ArrayList<Integer> list) {return Collections.min(list);}
}
