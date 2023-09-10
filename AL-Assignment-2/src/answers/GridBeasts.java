package answers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GridBeasts {
	
	public static HashMap<String, ArrayList<String>> foundBeasts = new HashMap<>();
	/*The key is the actual structure of the beast, a 3D array.
	 * The value is an array of the the anchor's coordinations.*/
	
	public static void findAllBeasts(int size , int [][][] array) {
		ArrayList<ArrayList<Integer>> factorsPartitions = factorize(size);
		
		for(int i = 0 ; i < factorsPartitions.size() ; i++)
			findBeast(array,
					factorsPartitions.get(i).get(0),
					factorsPartitions.get(i).get(1), 
					factorsPartitions.get(i).get(2));
		
		
		System.out.println("The grid beasts in size " + size + " are:" );
			for (Map.Entry<String, ArrayList<String>> entry : foundBeasts.entrySet()) {
				if(entry.getValue().size() > 1)
					System.out.println("Beast: " + entry.getKey() + "Anchors: " + entry.getValue());
			}		
		
	}
	
	
	public static void findBeast (int [][][] array , final int DEP , final int HGT , final int WDT) {
		//3D scan

		int [][][] currentBeast = new int [DEP][HGT][WDT];
		
		for(int i = 0; i < array.length - DEP + 1; i++) {
			for(int j = 0; j < array[0].length - HGT + 1; j++) {
				for(int k = 0; k < array[0][0].length - WDT + 1; k++) {		

					int [][][] currentForm = slice(DEP,HGT,WDT,new int[]{i,j,k} ,array);
					putBeast(currentForm, "" + i + j + k);
		
				}
			}
		}
	}
	
	
	public static void putBeast (int [][][] beast , String anchor) {
		
		String rep = stringfyArray(beast);
		ArrayList<String> beastType = foundBeasts.get(rep);
		//An array of anchors that share the same grid beast
		
		//If not found, allocate a new array list
		if(beastType == null) {
			ArrayList <String> newBeastType = new ArrayList<>();
			newBeastType.add(anchor);
			foundBeasts.put(rep , newBeastType);
		}
		else {
			for(String existingAnchor : beastType ) 
				//Avoid adding overlapping beasts
				if(areOverlapping(existingAnchor, anchor, beast))return;
						
			beastType.add(anchor);
		} 
				
	}
	public static boolean areOverlapping (String a1 , String a2 , int [][][] beast) {
		/*This function checks if there are any overlapping parts between two different anchors
		 * with the same beast type.*/
		final int DEP = beast.length; //Depth
		final int HGT = beast[0].length;//Height
		final int WDT = beast[0][0].length;//Width
		
		//Anchors convention : " <depth><height><width> "

		
		return (a1.charAt(0) + DEP > a2.charAt(0) 
							&&
				a1.charAt(1) + HGT > a2.charAt(1)
							&&
				a1.charAt(2) + WDT > a2.charAt(2)); 
	}
	
	public static int [][][] slice(final int D , final int H , final int W , int [] a , int [][][] grid){
		int [][][] ret = new int [D][H][W];
		
		
		for(int i = 0; i < D ; i++) {
			for(int j = 0; j < H ; j++) {
				for(int k = 0; k < W; k++) 
					ret [i][j][k] = grid [a[0] + i ][a[1] + j ][a[2] + k]; 
			}
		}
		return ret;
	}
	

	
	
	public static String stringfyArray (int [][][] arr) {
		String ret = "";
		
		
		for(int i = 0; i < arr.length; i++) {
			ret += "{";
			for(int j = 0; j < arr[0].length; j++) {
				ret += "{";

				for(int k = 0; k < arr[0][0].length; k++) {
					ret += arr[i][j][k] ;
					if( k < arr[0][0].length - 1 )ret += ",";

					}
				ret += "}";
				if( j < arr[0].length - 1 )ret += ",";

				}
			ret += "}";
			if( i < arr.length - 1 )ret += ",";

			}
		return ret;
	}
	
	
	public static void print3D (int [][][] array) {
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[0].length; j++) {
				for(int k = 0; k < array[0][0].length; k++) {
					
					System.out.print(array[i][j][k] + "\t");	
				}
				System.out.println();
			}
			System.out.println("----------------------------------");
		}
	}
	
	
	public static ArrayList<ArrayList<Integer>> factorize(int n) {
	    ArrayList<ArrayList<Integer>> result = new ArrayList<>();
	    
	    for (int i = 1; i <= n; i++) {
	        if (n % i == 0) {
	            for (int j = 1; j <= n / i; j++) {
	                if ((n / i) % j == 0) {
	                    int k = n / i / j;
	                    ArrayList<Integer> factors = new ArrayList<>();
	                    factors.add(i);
	                    factors.add(j);
	                    factors.add(k);
	                    result.add(factors);
	                }
	            }
	        }
	    }
	    return result;
	}

}
