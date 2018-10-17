package restAPIAutomation.RestAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Utility {
	XSSFWorkbook wb;
	XSSFSheet sheet;
	DataFormatter formatter;
	String superKeyCellValue;
	String valCellValue;
	String keyCellValue;
	JSONObject jsonObject;
	JSONObject MjsonObject;

	public Utility(String path, String sheetName) {
		try {
			File src = new File(path);
			FileInputStream fis = new FileInputStream(src);
			wb = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int index = 0;
		for (int m = 0; m < wb.getNumberOfSheets(); m++) {
			if (wb.getSheetName(m).equals(sheetName)) {
				index = wb.getSheetIndex(sheetName);
			}
		}
		sheet = wb.getSheetAt(index);
	}

	public String readExcel(int Row, int column) throws IOException {

		formatter = new DataFormatter();
		String data = formatter.formatCellValue(sheet.getRow(Row).getCell(column));
		wb.close();
		return data;
	}

	public int lastRowNumber() {
		return sheet.getLastRowNum();

	}
}
