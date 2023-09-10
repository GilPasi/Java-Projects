package excel.io;

import java.io.IOException;

import jxl.write.WriteException;

public class Demo {
        
    public static void main(String[] args) throws WriteException, IOException {
    	ReadExcel test1 = new ReadExcel();
    	test1.setInputFile("atm.xls");
    	test1.demonstrateReading();
    	
    	
        WriteExcel test2 = new WriteExcel();
        test2.setOutputFile("atm.xls");
        test2.write();
        System.out
                .println("Please check the result file under atm.xls ");
    }
    

}