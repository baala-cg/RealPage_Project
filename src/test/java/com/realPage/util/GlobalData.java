package com.realPage.util;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.Scenario;

public class GlobalData {
//	public static String TestCaseName = null;
	public static String TestScenarioName = null;
	public static String DataSheetName = null;
	public static WebDriver driver;
	public static Scenario scenario;
	public static HashMap<String,String> ConfigData = new HashMap<>();
	public static String DataFilePath;
	public static HashMap<String,String> DataElements = new HashMap<>();
}
