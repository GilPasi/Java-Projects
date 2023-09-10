/*Authors : Yulia Moshan 319565610
 * 			Gil Pasi 	 206500936
 * Algorithm planning - assignment 3 
 * */
package driver;

import java.io.IOException;
import java.util.ArrayList;
import excel.io.ReadExcel;
import jxl.read.biff.BiffException;
import utilities.Coordinate;
import utilities.Map;
import utilities.Path;


public class Runner {
    public static void main(String[] args) {
    	ReadExcel reader = new ReadExcel();
    	reader.setInputFile("atm.xls");
    	
    	final int MACHINES_COUNT = 6; // <= data.length()
    	Map map = new Map(MACHINES_COUNT);
    	map.setCoordinate(Coordinate.GEOGRAFIC_CENTER);
    	


    	try {
    		
    		//=================Extracting data =========================
    		final int DATA_TYPES_COUNT = 3;
    		final int RECORDS_COUNT = reader.getSheetRows();

    		Object [][] data = new Object [RECORDS_COUNT][DATA_TYPES_COUNT];//String and integers array
    		
    		reader.extractEssentials(data);
    		
    		int coordinateIndex = 1; //Skip the geographic center
    		for(int i = 1 ; coordinateIndex < MACHINES_COUNT ; i++) {//i = 1 skip the headers
    			Object [] curAtmData = data[i];
    			
    			//Ignore invalid data
    			if(curAtmData[0]=="" ||curAtmData[1]=="" ||curAtmData[2]=="")continue;
    			
    			String city = (String)curAtmData[0];
    			double x = Double.parseDouble((String)curAtmData[1]);
    			double y = Double.parseDouble((String)curAtmData[2]);
    			
    			map.setCoordinate(coordinateIndex++, x,y , city);


  
    			
    		}
    		
    		// ====================Algorithm============================

    		final int HOUR_SIZE = 60;

    		Map bestJourney = findBestJourney(map);
    		
    		System.out.println("lightest journey is:\n" + bestJourney  );
    		double time = bestJourney.totalTime() / HOUR_SIZE;
    		
            System.out.println("Minimum time: "+String.format("%.2f",time) + " hours");

    		


    		


		} catch (IOException | IndexOutOfBoundsException | BiffException e) {
			e.printStackTrace();
		}
    }
    
    

    //====================== Algorithm high view ============================
    public static Map findBestJourney(Map m) {
    	ArrayList<String> permutations = (ArrayList<String>) m.findPermutations();
    	double minWeight = Double.MAX_VALUE;
    	Map minGraph = null;
    	for(String currentPermutation : permutations) {
    		Map currentMap = m.stringToMap(currentPermutation);
    		
    		if(currentMap.totalWeight() < minWeight && currentMap.isDegenerated()) {
    			minGraph = currentMap;
    			minWeight = currentMap.totalWeight();
    		}
    	}
    	return minGraph;
    }
    
    //=====================================================================
}