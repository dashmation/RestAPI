package restAPIAutomation.RestAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

public class ExcelActions2 {
	XSSFWorkbook wb;
	XSSFSheet sheet;
	DataFormatter formatter;
	String superKeyCellValue;
	String valCellValue;
	String keyCellValue;
	JSONObject jsonObject;
	JSONObject MjsonObject;
	File src;
	FileInputStream fis;
	FileOutputStream fos;
	
	public ExcelActions2(String path, int sheetIndex) {
		try {
			src = new File(path);
			fis = new FileInputStream(src);
			wb = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(sheetIndex);
	}

	public String readExcel(int Row, int column) throws IOException {

		formatter = new DataFormatter();
		String data = formatter.formatCellValue(sheet.getRow(Row).getCell(column));
		wb.close();
		return data;
	}

	public String makingJSON(int MassterkeyColndex, int keyColIndex, int valColIndex) {
		String exSuperKeyCellValue = null;
		jsonObject = new JSONObject();
		MjsonObject = new JSONObject();
		formatter = new DataFormatter();
		
		try {
			for (int iRowIndex = 3; iRowIndex <= sheet.getLastRowNum(); iRowIndex++) {
				superKeyCellValue = formatter.formatCellValue(sheet.getRow(iRowIndex).getCell(MassterkeyColndex));
				if (!superKeyCellValue.isEmpty() || superKeyCellValue.length() > 0) {
					exSuperKeyCellValue = superKeyCellValue;
					jsonObject = new JSONObject();
					continue;
				}
				XSSFRow row = sheet.getRow(iRowIndex);
				if (row != null) {
					Cell keyCell = row.getCell(keyColIndex);
					Cell valCell = row.getCell(valColIndex);
					if (keyCell != null && valCell != null) {
						keyCellValue = formatter.formatCellValue(sheet.getRow(iRowIndex).getCell(keyColIndex));
						valCellValue = formatter.formatCellValue(sheet.getRow(iRowIndex).getCell(valColIndex));
						if (!keyCellValue.isEmpty() || keyCellValue.length() > 0) {
							jsonObject.put(keyCellValue, valCellValue);
						}
					}
				}
				MjsonObject.put(exSuperKeyCellValue, jsonObject);
			}
			wb.close();
			System.out.println("before returning JSON string value is : " + MjsonObject.toString());
		} catch (JSONException |

				IOException ex) {
			ex.printStackTrace();
			System.out.println("Eception while preparing JSON object : " + ex.getMessage());
			return "";
		}
		return MjsonObject.toString();
	}

	public int lastRowNumber() {
		return sheet.getLastRowNum();

	}

	public int lastColumnNumber(int RowNum) {
		Row r = sheet.getRow(RowNum);
		return r.getLastCellNum();

	}
}
