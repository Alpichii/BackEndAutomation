package utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelUtils {

    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private static XSSFRow row;
    private static XSSFCell cell;
    private static String excelFilePath;

    public static void openExcelFile(String excelFileName, String sheetName) {
        excelFilePath = "testData/" + excelFileName + ".xlsx";

        try {
            FileInputStream fileInputStream = new FileInputStream(excelFilePath);
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetName);
            System.out.println("The file and sheet exist!");
        } catch (Exception e) {
            System.out.println("The file is not exist!");
        }
    }

    public static String getValue(int rowNum, int cellNum) {
        row = sheet.getRow(rowNum);
        cell = row.getCell(cellNum);
        cell.setCellType(CellType.STRING);
        return cell.toString();
    }

    // This method will override the existing cell
    // Because in the cell we are getting the existing cell
    public static void setValue(String newValue, int rowNum, int cellNum) {
        row = sheet.getRow(rowNum);
        cell = row.getCell(cellNum);
        cell.setCellValue(newValue);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath);
            workbook.write(fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This method is writing the value anywhere in the Excel sheet
    // Because in the cell we are using create method for the row
    public static void insertValue(String insertValue, int rowNum, int cellNum) {
        row = sheet.createRow(rowNum);
        cell = row.createCell(cellNum);
        cell.setCellValue(insertValue);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath);
            workbook.write(fileOutputStream);
        } catch (Exception e) {
            System.out.println("File is not exist to update!");
        }
    }

    public static List<List<String>> getValues() {
        List<List<String>> allValues = new ArrayList<>();
        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            List<String> eachRow = new ArrayList<>();
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                eachRow.add(getValue(i, j));
            }
            allValues.add(eachRow);
        }
        return allValues;
    }

    public static List<List<String>> getValues(String testMethodName) {
        List<List<String>> allValues = new ArrayList<>();
        for (int i = sheet.getFirstRowNum()+1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            List<String> values = new ArrayList<>();
            for (int j = row.getFirstCellNum()+1; j < row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                cell.setCellType(CellType.STRING);
                if(row.getCell(row.getFirstCellNum()).toString().equals(testMethodName)){
                    values.add((cell.toString()));
                }
            }
            if(!values.isEmpty()) {
                allValues.add(values);
            }
        }
        return allValues;
    }

    public static String[][] getExcelData(List<List<String>> mergedList) {
        String[][] result = new String[mergedList.size()][];
        for (int i = 0; i < mergedList.size(); i++) {
            List<String> currentList = mergedList.get(i);
            result[i] = currentList.toArray(new String[currentList.size()]);
        }
        return result;
    }

    public static void closeExcelFile() {
        try {
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        openExcelFile("excelSample", "Sheet1");
//        System.out.println(getValue(2, 3));
//        setValue("Polo", 1, 0);
//        insertValue("Polo", 7, 8);
//        System.out.println(getValues());
        System.out.println(Arrays.deepToString(getExcelData(getValues())));

    }

}
