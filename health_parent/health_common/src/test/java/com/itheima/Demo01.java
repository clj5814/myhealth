package com.itheima;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Demo01 {
    @Test
    public void fun() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("G:\\hello.xlsx");
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                String value = cell.getStringCellValue();
                System.out.println(value);
            }
        }
        workbook.close();
    }
    @Test
    public void fun01() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("G:\\hello.xlsx");
        XSSFSheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            short lastCellNum = row.getLastCellNum();
            System.out.println(lastCellNum);
            for (int j = 0; j <lastCellNum; j++) {
                XSSFCell cell = row.getCell(j);
                String value = cell.getStringCellValue();
                System.out.println(value);
            }
        }
       workbook.close();
    }
    @Test
    public void fun02() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("哈哈");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("年龄");
        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("小明");
        row1.createCell(2).setCellValue("10");
        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("2");
        row2.createCell(1).setCellValue("小王");
        row2.createCell(2).setCellValue("20");
        FileOutputStream os = new FileOutputStream("G:\\ahah.xlsx");
        workbook.write(os);
        os.flush();
        os.close();
        workbook.close();
    }
}
