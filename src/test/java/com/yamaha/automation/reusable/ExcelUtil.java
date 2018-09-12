package com.yamaha.automation.reusable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	public static Object[][] DataTable(String Workbook, String Sheet) throws FileNotFoundException, IOException{
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(Workbook));
		XSSFSheet sh = wb.getSheet(Sheet);
		Object[][] x = new Object[sh.getPhysicalNumberOfRows()-1][sh.getRow(0).getPhysicalNumberOfCells()];
		for(int i=1;i<sh.getPhysicalNumberOfRows();i++)
		{
			for(int j=0;j<sh.getRow(0).getPhysicalNumberOfCells();j++)
			{
				x[i-1][j] = sh.getRow(i).getCell(j).getStringCellValue();
			}
		}
		return x;
	}
	
	public static Object[][] DataTable(String Workbook, String Sheet, int rows) throws FileNotFoundException, IOException{
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(Workbook));
		XSSFSheet sh = wb.getSheet(Sheet);
		Object[][] x = new Object[rows][sh.getRow(0).getPhysicalNumberOfCells()];
		for(int i=1;i<rows+1;i++)
		{
			for(int j=0;j<sh.getRow(0).getPhysicalNumberOfCells();j++)
			{
				x[i-1][j] = sh.getRow(i).getCell(j).getStringCellValue();
			}
		}
		return x;
	}
	
	public static Object[][] DataTable(String Workbook, String Sheet, int rows, int col) throws FileNotFoundException, IOException{
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(Workbook));
		XSSFSheet sh = wb.getSheet(Sheet);
		Object[][] x = new Object[rows][col];
		for(int i=1;i<rows+1;i++)
		{
			for(int j=0;j<col;j++)
			{
				x[i-1][j] = sh.getRow(i).getCell(j).getStringCellValue();
			}
		}
		return x;
	}
	
	
}
