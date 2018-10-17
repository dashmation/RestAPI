package restAPIAutomation.RestAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteOntoExcel {
	static XSSFSheet sheet;
	
	private static final String FILE_NAME = "./ExcelFile/Data_OutPut.xlsx";

	public static void writetorow(String sheetName,int rowIndex, String[] columndata) throws IOException {
		//File Exist
		File src = new File(FILE_NAME);
		
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		for (int m = 0; m < workbook.getNumberOfSheets(); m++) {
	           if (workbook.getSheetName(m).equals(sheetName)) {
	        	   sheet = workbook.getSheet(sheetName);
	    }      
		}
		//int index = workbook.getSheetIndex(sheetName);
		//XSSFSheet sheet = workbook.getSheetAt(index);
		Row row = sheet.createRow(rowIndex);
		int column = 0;
		for (int i=0;i<columndata.length;i++)
		{
			Cell cell = row.createCell(column++);
			cell.setCellValue(columndata[i]);
		}
		
		try {
			FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}
}
