package com.mak.excel;

public class TestExcelWriter {
	
	public static void main(String[] args0){
		IExcelWriter excelWriter = new PoiExcelWriter();
		excelWriter.createWorkBook();
		excelWriter.createSheet("Sample");
		excelWriter.createCell(0, 0);
		excelWriter.setCellStyle("Arial", 12, IExcelWriter.BLUE);
		excelWriter.setCellValue("HelloWorld");
		excelWriter.createExcel("Test.xlsx");
	}
}
