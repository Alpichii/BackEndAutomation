package utils;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

public class DataProviderUtils {

    @DataProvider(name="DataFromExcel")
    public static Object[][] getDataFromExcelWithDataProvider(Method method){
        // Opening the exel file
        ExcelUtils.openExcelFile("petStoreData", "Sheet1");
        // Converting the list of list into multidimensional Object array
            // Following code will run the test with the method name in the test and Excel sheet
//        Object[][] arrayObject = ExcelUtils.getExcelData(ExcelUtils.getValues(method.getName()));
        // Following code will run the test with regardless of the method name in the test class
        Object[][] arrayObject = ExcelUtils.getExcelData(ExcelUtils.getValues());
        // Closing the Excel file
        ExcelUtils.closeExcelFile();

        return arrayObject;
    }
}
