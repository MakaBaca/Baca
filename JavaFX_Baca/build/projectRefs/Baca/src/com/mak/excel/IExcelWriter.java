package com.mak.excel;

public interface IExcelWriter {
	
	public void createWorkBook();
	
	public void createSheet(String SheetName);
	
	public void createRow(int index);
	
	public void createCell(int rowIndex, int cellIndex);
	
	public void createCell(int cellIndex);
	
	public void createExcel(String excelName);
	
	public void setCellStyle(String font, int fontSize, int fontColor);
	
	public void setCellValue(String value);
	
	public void setHeader(String cols[]);
	
	public String getCellValue(int rowIndex, int cellIndex);
	
	public static int BLACK = 0;
	
	public static int RED = 1;
	
	public static int BLUE = 2;
	
	public static int GREEN = 3;

}
