package com.realPage.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReusableFunctions {

	// Explicit Wait - ExpectedConditions - elementToBeClickable for the web element
	public static String waitForElementClickable(WebDriver driver, WebElement element, int waitTime) {
		try {
			element = new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(element));
			return "pass";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	// Explicit Wait - ExpectedConditions - visibilityOf for the web element
	public static String waitForElementVisibility(WebDriver driver, WebElement element, int waitTime) {
		try {
			element = new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(element));
			return "pass";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	// Function Select value from the drop down
	public static String dropDownSelect(WebElement element, String testData) {
		try {
			Select select = new Select(element);
			select.selectByVisibleText(testData);
			return "pass";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	// Function to Input text
	public static String inputText(WebElement element, String testData) {
		try {
			if (!testData.equals("")) {
				element.clear();
				element.sendKeys(testData);
			}
			return "pass";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	// Function to Capture Screenshot
	
	  public static String captureScreenshot(WebDriver driver, Scenario scenario) { 
		  try { 
	  byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	  scenario.embed(screenshot, "image/png"); 
	  scenario.attach(screenshot, "image/png","name" ); 
	  return "pass"; 
	  }
	  catch (Exception e) { 
		  e.printStackTrace(); 
		  return "fail"; 
		  }
	  
	  }
	 
		// Function to read the data from the Test Data excel
	public static String readTestData(String scenarioName) {
		Excel e = new Excel();
		String DataFilePath = null;

		DataFilePath = GlobalData.ConfigData.get("testDataFilePath");

		String ColumnName = "";
		GlobalData.DataFilePath = DataFilePath;
		String DataSheetName = GlobalData.DataSheetName;

		int callCount = 1;

		String UserStoryID = scenarioName;
		int dataCounter = 0;
		try {
			e.excelData(DataFilePath, ColumnName, DataSheetName, callCount, UserStoryID, dataCounter);
		} catch (IOException e3) {
			e3.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return "";
	}
	
	
	public static String waitforElementList(WebDriver driver, String tag, String xpathExpression) {
		try {
	WebDriverWait dyWait = new WebDriverWait(driver,20);
	dyWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName(tag)));
	List<WebElement> articles = driver.findElements(By.xpath(xpathExpression));
	articles.get(0).click();
	return "pass";	
		}
		catch(Exception e) {
			e.printStackTrace();
			return "fail";
		}	
	}
		
	
}
