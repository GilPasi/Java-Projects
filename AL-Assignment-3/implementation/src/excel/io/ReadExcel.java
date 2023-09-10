/*Authors : Yulia Moshan 319565610
 * 			Gil Pasi 	 206500936
 * Algorithm planning - assignment 3
 * */
package excel.io;
import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {

    private String inputFile;

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void demonstrateReading() throws IOException  {
        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet = w.getSheet(0);
            // Loop over first 10 column and lines

            for (int j = 0; j < sheet.getColumns(); j++) {
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell cell = sheet.getCell(j, i);
                    CellType type = cell.getType();
                    if (type == CellType.LABEL) {
                        System.out.println("I got a label "
                                + cell.getContents());
                    }

                    if (type == CellType.NUMBER) {
                        System.out.println("I got a number "
                                + cell.getContents());
                    }

                }
            }
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }
    public int getSheetRows() throws IndexOutOfBoundsException, BiffException, IOException {
    	return Workbook.getWorkbook(new File(inputFile)).getSheet(0).getRows();
    }
    
    public Object [][] extractEssentials(Object [][] table) throws IOException  {

    	File inputWorkbook = new File(inputFile);
        Workbook w;
        
        
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet = w.getSheet(0);
            
            for (int j = 0; j < sheet.getRows(); j++) {
            	
                for (int i = 0; i < sheet.getColumns(); i++) {
                	String headerName = sheet.getCell(i,0).getContents();

                    Cell cell = sheet.getCell(i ,j);
                    if(headerName.equals("City")) {
                    	table[j][0] = cell.getContents();
                    }
                    
                    else if(headerName.equals("X_Coordinate")) {
                    	table[j][1] = cell.getContents();
                    }
                    
                    else if(headerName.equals("Y_Coordinate")) {
                    	table[j][2] = cell.getContents();
                    }
                    else continue;
                }
            }
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return table;
    }
    
    
}