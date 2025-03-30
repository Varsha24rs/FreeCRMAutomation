package util;

import base.TestBase;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

public class TestUtil extends TestBase {

    public static long PAGE_LOAD_TIMEOUT = 20;
    public static long IMPLICIT_WAIT = 10;

    static Workbook book;
    static Sheet sheet;

    public TestUtil(){
        PageFactory.initElements(driver,this);
    }

    public void mouseHover(WebElement ele){
        Actions action = new Actions(driver);
        action.moveToElement(ele);
    }

    public void waitTillElementDisplayed(WebElement ele, int time){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait.until(ExpectedConditions.visibilityOf(ele));
    }

    public void switchToFrame(String name){
        driver.switchTo().frame(name);
    }

    public static Object[][] excelReader(String excelSheetPath, String sheetName) {
        FileInputStream file = null;
        try{
            file=new FileInputStream(excelSheetPath);

        } catch (FileNotFoundException e) {
            e.getMessage();
        }
        try{
            book = WorkbookFactory.create(file);
        }catch (InvalidFormatException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();;
        }
        sheet = book.getSheet(sheetName);
        Object[][] data =  new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
                data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
                // System.out.println(data[i][k]);
            }
        }
        return data;
    }

    public static void takeScreenshotAtEndOfTest() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
    }
}

