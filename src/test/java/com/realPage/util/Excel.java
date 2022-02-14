package com.realPage.util;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

	public static int excelData(String DataFilePath, String ColumnName, String DataSheetName, int callCount, String TestScenarioName, int dataCounter) throws IOException, InvalidFormatException {
		
		String key= "";
		String value = "";
		String tcid="";
		int reqRow=0;
		int matchCount=0;
		
		String projectDir = new File(".").getCanonicalPath();
//		String projectDir = System.getProperty("user.dir");
		System.out.println("Proj Dir: "+ projectDir);	
		File inputWorkBook = new File(projectDir + DataFilePath);
		XSSFWorkbook dataWorkBook = null;
		System.out.println("Path: "+ projectDir+DataFilePath);
		TestScenarioName = GlobalData.TestScenarioName;
		dataWorkBook = new XSSFWorkbook(inputWorkBook);
		XSSFFormulaEvaluator.evaluateAllFormulaCells(dataWorkBook);
		XSSFSheet dataSheet = dataWorkBook.getSheet(DataSheetName);
		int dataRowCount = dataSheet.getLastRowNum();
		System.out.println("No of Rows: "+ dataRowCount);
		XSSFRow row = dataSheet.getRow(1);
		int dataColCount = row.getLastCellNum();
//		if(dataCounter == 0) {
//			for(int h = 0; h < dataRowCount; h++) {
//				tcid = dataSheet.getRow(h).getCell(1).getStringCellValue();
//				if(tcid.equalsIgnoreCase(TestCaseName)) {
//					dataCounter++;
//				}
//			}
//		}
		System.out.println("Call Count: "+callCount);
		for(int y=0; y <= dataRowCount; y++) {
			tcid = dataSheet.getRow(y).getCell(0).getStringCellValue();
			
			if(tcid.equalsIgnoreCase(TestScenarioName)) 
			matchCount++;
			if(tcid.equalsIgnoreCase(TestScenarioName) && matchCount == callCount) {
				reqRow = y;
				break;
				
			}
			
		}
		for(int u =0; u< dataColCount; u++) {
			key = dataSheet.getRow(0).getCell(u).getStringCellValue();
			value = dataSheet.getRow(reqRow).getCell(u).getStringCellValue();
			GlobalData.DataElements.put("TCRowNo",Integer.toString(reqRow));
			GlobalData.DataElements.put(key,value);	
		}
		return dataCounter;
		

	}
	/*finally {
		if(dataWorkBook != null)
			dataWorkBook
	}*/
//	return dataCounter;
	
}

	
	
	
	

